package se.kth.iv1201.group4.recruitment.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.Person;
import se.kth.iv1201.group4.recruitment.dto.ApplicantDTO;
import se.kth.iv1201.group4.recruitment.dto.PersonDTO;
import se.kth.iv1201.group4.recruitment.dto.RecruiterDTO;
import se.kth.iv1201.group4.recruitment.repository.ApplicantRepository;
import se.kth.iv1201.group4.recruitment.repository.PersonRepository;
import se.kth.iv1201.group4.recruitment.repository.RecruiterRepository;

/**
 * A service for accessing or adding persons from and to the preson
 * repositories. Rolls back on all exceptions and supports only transactional
 * methods.
 * 
 * @author William Stackenäs
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
@Service
public class PersonService implements UserDetailsService {
    @Autowired
    PersonRepository personRepo;
    @Autowired
    ApplicantRepository applicantRepo;
    @Autowired
    RecruiterRepository recruiterRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    static private final String ROLE_APPLICANT = "ROLE_APPLICANT";
    static private final String ROLE_RECRUITER = "ROLE_RECRUITER";

    /**
     * Adds an applicant to the applicant repository
     * 
     * @param a The applicant to add
     */
    public void addApplicant(Applicant a) {
        if (a != null) {
            applicantRepo.save(a);
        }
    }

    /**
     * Fetches a person from the database with the given username and password
     * 
     * @param username The username of the person to fetch
     * @param password The password of the person to fetch
     */
    public Person getPerson(String username, String password) {
        if (username == null || password == null)
            return null;

        Person p = personRepo.findPersonByUsername(username);
        if (p == null)
            return null;

        if (password.equals(p.getPassword())) {
            return p;
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null)
            throw new UsernameNotFoundException("Null argument");

        Person p;
        String role = null;
        try {
            p = personRepo.findPersonByUsername(username);

            if (applicantRepo.findApplicantByPerson(p) != null) {
                LOGGER.debug("Person logged in as an applicant.");
                role = ROLE_APPLICANT;
            } else if (recruiterRepo.findRecruiterByPerson(p) != null) {
                LOGGER.info("Person logged in as a recruiter.");
                role = ROLE_RECRUITER;
            } else {
                // Should never end up here as all Persons are
                // either applicants or recruiters
                LOGGER.error("Person logged in as neither an applicant nor recruiter.");
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("database error");
        }
        return new User(username, p.getPassword(), AuthorityUtils.createAuthorityList(role));
    }
}
