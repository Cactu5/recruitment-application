package se.kth.iv1201.group4.recruitment.application;

import org.springframework.beans.factory.annotation.Autowired;

import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.Person;
import se.kth.iv1201.group4.recruitment.repository.ApplicantRepository;
import se.kth.iv1201.group4.recruitment.repository.PersonRepository;
import se.kth.iv1201.group4.recruitment.repository.RecruiterRepository;

/**
 * A service for accessing or adding persons from and to
 * the preson repositories
 * 
 * @author William Stackenäs
 */
public class PersonService {
    @Autowired
    PersonRepository personRepo;
    @Autowired
    ApplicantRepository applicantRepo;
    @Autowired
    RecruiterRepository recruiterRepo;
    
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
     * Fetches a person from the database with the given username
     * and password
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

}
