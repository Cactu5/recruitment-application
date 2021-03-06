package se.kth.iv1201.group4.recruitment.application;

import java.util.Collection;
import java.util.Hashtable;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.group4.recruitment.application.error.UpdatedPersonContainsTemporaryDataException;

import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.LegacyUser;
import se.kth.iv1201.group4.recruitment.domain.Person;
import se.kth.iv1201.group4.recruitment.domain.Recruiter;
import se.kth.iv1201.group4.recruitment.dto.PersonDTO;
import se.kth.iv1201.group4.recruitment.dto.ApplicantDTO;
import se.kth.iv1201.group4.recruitment.repository.ApplicantRepository;
import se.kth.iv1201.group4.recruitment.dto.LegacyUserDTO;
import se.kth.iv1201.group4.recruitment.repository.LegacyUserRepository;
import se.kth.iv1201.group4.recruitment.repository.PersonRepository;
import se.kth.iv1201.group4.recruitment.dto.RecruiterDTO;
import se.kth.iv1201.group4.recruitment.repository.RecruiterRepository;
import se.kth.iv1201.group4.recruitment.util.TemporaryDataMatcher;
import se.kth.iv1201.group4.recruitment.util.error.UsernameAlreadyExistsException;
import se.kth.iv1201.group4.recruitment.util.error.EmailAlreadyExistsException;

import se.kth.iv1201.group4.recruitment.util.error.SSNAlreadyExistsException;

/**
 * A service for accessing or adding persons from and to the person
 * repositories. Rolls back on all exceptions and supports current transactions,
 * or creates a new if none exist.
 * 
 * @author William Stackenäs
 * @author Filip Garamvölgyi
 * @author Cactu5
 * @version %I%
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
@Service
public class PersonService implements UserDetailsService {
    @Autowired
    @Lazy
    @Qualifier("customAuthenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private PersonRepository personRepo;

    @Autowired
    private ApplicantRepository applicantRepo;

    @Autowired
    private RecruiterRepository recruiterRepo;

    @Autowired
    private LegacyUserRepository legacyUserRepo;

    private Hashtable<UUID, String> personsToBeReset;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    static public final String ROLE_APPLICANT = "ROLE_APPLICANT";
    static public final String ROLE_RECRUITER = "ROLE_RECRUITER";
    static public final String ROLE_LEGACY_USER = "ROLE_LEGACY_USER";

    /**
     * Adds a person to the list of persons that has requested a reset of their account
     * 
     * @param email The email of the person that requested an account reset
     * 
     * @return The UUID that was generated for the requested reset
     */
    public UUID addPersonToResetAccountList(String email) throws IllegalArgumentException {
        UUID uuid;
        Person p;
        if (personsToBeReset == null)
            personsToBeReset = new Hashtable<UUID, String>();
        if (email == null || TemporaryDataMatcher.isTemporaryEmail(email))
            throw new IllegalArgumentException("Email must not be null or temporary");
        
        p = personRepo.findPersonByEmail(email);
        if (p == null)
            throw new IllegalArgumentException("No such email");
        uuid = UUID.randomUUID();
        personsToBeReset.put(uuid, email);

        LOGGER.warn("Person with email " + email + " requested a reset available at /reset/" + uuid.toString());

        // TODO: Send email with link here

        return uuid;
    }

    /**
     * Checks whether or not a person exists in the list of persons that has requested
     * a reset of their account for the given UUID and returns their email
     * 
     * @param uuid The UUID to look for in the list
     * @return The email of the given UUID or null if no such UUID existed
     */
    public String getEmailFromAccountList(UUID uuid) {
        if (personsToBeReset == null)
            personsToBeReset = new Hashtable<UUID, String>();
        if (uuid == null)
            return null;

        return personsToBeReset.get(uuid);
    }

    /**
     * Resets a person from the list of persons that has requested
     * a reset of their account for the given UUID using the given
     * information contained in the PersonDTO. They will then be removed
     * from the list and also from their status as a legacy user, if applicable.
     * 
     * @param uuid The UUID in the list corresponding to the email of the person
     *             whose account should be reset.
     * @param p The person containing the updated data with which to reset
     */
    public void resetPersonFromResetAccountList(UUID uuid, PersonDTO p) {
        String email = getEmailFromAccountList(uuid);
        if (email == null || p == null)
            throw new IllegalArgumentException("No such UUID or Person");
        
        updatePersonWithEmail(p, email);
        personsToBeReset.remove(uuid);
    }

    /**
     * Adds an applicant to the applicant repository
     * 
     * @param a The applicant to add
     * @return returns the added {@link ApplicantDTO} if successful otherwise null is returned.
     */
    public ApplicantDTO addApplicant(Applicant a) {
        if (a != null) {
            ApplicantDTO  dto = applicantRepo.saveAndFlush(a);
            return dto;
        }
        return null;
    }

    /**
     * Adds a recruiter to the recruiter repository
     * 
     * @param r The recruiter to add
     * @return returns the added {@link RecruiterDTO} if successful otherwise null is returned.
     */
    public RecruiterDTO addRecruiter(Recruiter r) {
        if (r != null) {
            RecruiterDTO dto = 
            recruiterRepo.saveAndFlush(r);
            return dto;
        }
        return null;
    }
    
    /**
     * Updates a {@link Person} migrated from the old database. It's required for the 
     * dto to not contain any temporary data. If successful the {@link Person} is updated
     * and removed as a {@link LegacyUser}.
     *
     * @param   dto         contains the updated data to make sure the {@link Person} follows
     *                      the rules of the database schema.
     * @param   username    the username of the {@link LegacyUser} to update.
     * @throws  UpdatedPersonContainsTemporaryDataException if dto still contains temporary
     *                                                      data this exception is thrown.
     */
    public void updatePersonWithUsernameAndRemoveFromLegacyUsers(PersonDTO dto, String username)
        throws UpdatedPersonContainsTemporaryDataException {
        Person p = personRepo.findPersonByUsername(username);
        if(p == null){
            LOGGER.error(String.format("Legacy user %s not found in person table", username));
            throw new UsernameNotFoundException(String.format("Legacy user %s not found in person table",
                    username));
        }
        if (TemporaryDataMatcher.isTemporaryEmail(dto.getEmail())) {
            throw new UpdatedPersonContainsTemporaryDataException("Still contains the temporary email");
        }
        if(TemporaryDataMatcher.isTemporarySSN(dto.getSSN())){
            throw new UpdatedPersonContainsTemporaryDataException("Still contains the temporary SSN");
        }
        if(!dto.getUsername().equals(username) &&
                personRepo.findPersonByUsername(dto.getUsername()) != null){
            LOGGER.info(String.format("Legacy user %s tried to use username %s", username,
                    dto.getUsername()));
            throw new UsernameAlreadyExistsException("Username is already in use.");
        }
        if(!p.getEmail().equals(dto.getEmail()) &&
                personRepo.findPersonByEmail(dto.getEmail()) != null){
            LOGGER.info(String.format("Legacy user %s tried to use email %s", username,
                    dto.getEmail()));
            throw new EmailAlreadyExistsException("Email is already in use.");
        }
        if(!p.getSSN().equals(dto.getSSN()) &&
                personRepo.findPersonBySsn(dto.getSSN()) != null){
            LOGGER.info(String.format("Legacy user %s tried to use ssn %s", username,
                    dto.getSSN()));
            throw new SSNAlreadyExistsException("SSN is already in use.");
        }       
        dto = updatePersonWithContentsOfDTO(dto, username);
        removeLegacyUserByPersonDTO(dto);
        legacyUserRepo.flush();
    }

    private void removeLegacyUserByPersonDTO(PersonDTO person){
        LegacyUser lu = legacyUserRepo.findLegacyUserByPerson((Person)person);
        if (lu != null)
            legacyUserRepo.delete(lu);
    }

    private PersonDTO updatePersonWithContentsOfDTO(PersonDTO dto, String username){
        Person p = personRepo.findPersonByUsername(username);
        if(p == null) {
            LOGGER.error("Legacy user " + username + " was not found.");
            throw new UsernameNotFoundException("Username " + username + " was not found in the database");
        }
        p.updateWithContentsOfDTO(dto);
        return personRepo.save(p);
    }

    /**
     * Updates a {@link Person} migrated from the old database. If successful the {@link Person}
     * is updated and removed as a {@link LegacyUser} if it used to be one.
     *
     * @param   dto         contains the updated data to make sure the {@link Person} follows
     *                      the rules of the database schema.
     * @param   email       the email of the {@link Person} to update.
     */
    public void updatePersonWithEmail(PersonDTO dto, String email) {
        Person p = personRepo.findPersonByEmail(email);
        if (p != null) {
            p.updateWithContentsOfDTO(dto);
            dto = personRepo.save(p);
            removeLegacyUserByPersonDTO(dto);
            legacyUserRepo.flush();
        }
    }

    /**
     * Adds a legacy user to the legacy user repository
     * 
     * @param lu The legacy user to add
     * @return returns the added {@link LegacyUserDTO} if successful otherwise null is returned.
     */
    public LegacyUserDTO addLegacyUser(LegacyUser lu) {
        if (lu != null) {
            LegacyUserDTO dto = legacyUserRepo.saveAndFlush(lu);
            LOGGER.info("Added legacy user");
            return dto;
        }
        return null;
    }

    /**
     * Fetches a person from the database with the given username and password
     * 
     * @param username The username of the person to fetch
     * @param password The hashed password of the person to fetch
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

    /**
     * Fetches an applicant from the database with the given person.
     *
     * @param person person used to identify applicant
     * @return {@link Applicant} identified by the person
     */
    public Applicant getApplicant(Person person) {
        if (person == null)
            return null;

        return applicantRepo.findApplicantByPerson(person);
    }

    /**
     * Retrieves the Person object corresponding to the given username, and if it exists,
     * returns it as an object implementing the UserDetails interface
     * 
     * @param username The username of the person to retrieve
     * @return The details of the person, including its roles
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null)
            throw new UsernameNotFoundException("Null argument");

        Person p;
        String role = null;
        try {
            p = personRepo.findPersonByUsername(username);

            if (p != null) {
                if (legacyUserRepo.findLegacyUserByPerson(p) != null){
                    LOGGER.info("Person logged in as a legacy user");
                    role = ROLE_LEGACY_USER;
                } else if (applicantRepo.findApplicantByPerson(p) != null) {
                    LOGGER.debug("Person logged in as an applicant.");
                    role = ROLE_APPLICANT;
                } else if (recruiterRepo.findRecruiterByPerson(p) != null) {
                    LOGGER.info("Person logged in as a recruiter.");
                    role = ROLE_RECRUITER; 
                } else {
                    // Should never end up here as all Persons are
                    // either applicants or recruiters
                    LOGGER.warn("Person logged in as neither an applicant nor recruiter.");
                    throw new UsernameNotFoundException("User has no role");
                }
            } else {
                LOGGER.debug("No person with the username " + username + " and password combination could be found.");
                throw new UsernameNotFoundException("Username " + username + " not found.");
            }
        } catch (Exception e) {
            LOGGER.error("Database transaction failed.");
            throw new UsernameNotFoundException("Database error.");
        }

        return new User(username, p.getPassword(), AuthorityUtils.createAuthorityList(role));
    }

    /**
     * If the user credentails are correct, the user will get logged in.
     * 
     * @param username the username
     * @param password the password
     */
    public void autoLogin(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            LOGGER.debug("Auto login was successfull for user: " + username);
        }
    }

    /**
     * Returns the logged in user (<code>UserDetails</code>).
     * 
     * @return a <code>UserDetails</code> if the user is logged in and
     *         <code>null</code> otherwise
     */
    public UserDetails getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()
                && !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return (UserDetails) auth.getPrincipal();
        } else {
            LOGGER.debug("Tried getting a logged in user, but none was available.");
            return null;
        }
    }

    /**
     * Returns whether the <code>Collection</code> contains the authority.
     * 
     * @param collection the collection
     * @param authority  the authority
     * @return <code>true</code> if the authority exists in the list, else
     *         <code>false</code>
     */
    public static boolean authoritiesContains(Collection<? extends GrantedAuthority> collection, String authority) {
        for (GrantedAuthority grantedAuthority : collection) {
            if (authority.equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }

        return false;
    }
}
