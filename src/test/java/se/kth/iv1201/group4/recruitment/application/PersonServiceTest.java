package se.kth.iv1201.group4.recruitment.application;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import se.kth.iv1201.group4.recruitment.application.error.UpdatedPersonContainsTemporaryDataException;
import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.LegacyUser;
import se.kth.iv1201.group4.recruitment.domain.Person;
import se.kth.iv1201.group4.recruitment.domain.Recruiter;
import se.kth.iv1201.group4.recruitment.dto.PersonDTO;
import se.kth.iv1201.group4.recruitment.repository.ApplicantRepository;
import se.kth.iv1201.group4.recruitment.repository.LegacyUserRepository;
import se.kth.iv1201.group4.recruitment.repository.PersonRepository;
import se.kth.iv1201.group4.recruitment.repository.RecruiterRepository;
import se.kth.iv1201.group4.recruitment.util.error.EmailAlreadyExistsException;
import se.kth.iv1201.group4.recruitment.util.error.UsernameAlreadyExistsException;

@SpringBootTest
public class PersonServiceTest {
    @Autowired
    private PersonService service;

    @MockBean
    private PersonRepository personRepo;

    @MockBean
    private ApplicantRepository applicantRepo;

    @MockBean
    private RecruiterRepository recruiterRepo;

    @MockBean
    private LegacyUserRepository legacyUserRepo;

    @Test
    void testToAddApplicant(){
        Person p = dummyPerson();
        Applicant a1 = new Applicant(p); 
        doReturn(a1).when(applicantRepo).saveAndFlush(a1);
        
        Applicant a2 = (Applicant) service.addApplicant(a1);

        assertFalse(a2 == null);
        assertSame(a1, a2);
    }

    @Test
    void testToAddRecruiter(){
        Person p = dummyPerson();
        Recruiter r1 = new Recruiter(p); 
        doReturn(r1).when(recruiterRepo).saveAndFlush(r1);
        
        Recruiter r2 = (Recruiter) service.addRecruiter(r1);

        assertFalse(r2 == null);
        assertSame(r1, r2);
    }

    @Test
    void testToAddLegacyUser(){
        Person p = dummyPerson();
        LegacyUser lu1 = new LegacyUser(p); 
        doReturn(lu1).when(legacyUserRepo).saveAndFlush(lu1);
        
        LegacyUser lu2 = (LegacyUser) service.addLegacyUser(lu1);

        assertFalse(lu2 == null);
        assertSame(lu1, lu2);
    }

    @Test
    void testToGetPersonByUsernameAndHashedPassword(){
        Person p1 = dummyPerson("user","qooqWan123!");

        doReturn(p1).when(personRepo).findPersonByUsername("user");
        
        Person p2 = service.getPerson("user", p1.getPassword());

        assertFalse(p2 == null);
        assertSame(p1, p2);
    }

    @Test
    void testToGetApplicantByPerson(){
        Person p = dummyPerson();
        Applicant a1 = new Applicant(p);

        doReturn(a1).when(applicantRepo).findApplicantByPerson(p);

        Applicant a2 = service.getApplicant(p);

        validationAssertions(a1, a2);
    }

    @Test
    void testThatTemporarySSNCantExistWhenUpdatingPerson(){
        Person p = dummyLegacyPerson(false, true);
        Exception e = assertThrows(UpdatedPersonContainsTemporaryDataException.class, () -> {
            service.updatePersonWithUsernameAndRemoveFromLegacyUsers(p, ""); 
        });
        assertTrue(e.getMessage().contains("SSN"));
    }

    @Test
    void testThatTemporaryEmailCantExistWhenUpdatingPerson(){
        Person p = dummyLegacyPerson(true, false);
        Exception e = assertThrows(UpdatedPersonContainsTemporaryDataException.class, () -> {
            service.updatePersonWithUsernameAndRemoveFromLegacyUsers(p, ""); 
        });
        assertTrue(e.getMessage().contains("email"));
    }  

    @Test
    void testThatExceptionIsThrownWhenTryingToUpdateLegacyUserThatHasAUsernameNotFoundInTheDatabase(){
        Person p = dummyPerson();
        Exception e = assertThrows(UsernameNotFoundException.class, () -> {
            service.updatePersonWithUsernameAndRemoveFromLegacyUsers(p, p.getUsername()); 
        });
        assertTrue(e.getMessage().contains(p.getUsername()), "Contained: " + e.getMessage() + 
                ", not " + p.getUsername());
    } 

    @Test
    void testThatUpdatedUserCantUseUsernameAlreadyInUse(){
        Person p1 = dummyPerson("iExist");
        doReturn(p1).when(personRepo).findPersonByUsername("iExist");
        Exception e = assertThrows(UsernameAlreadyExistsException.class, () -> {
            service.updatePersonWithUsernameAndRemoveFromLegacyUsers(p1, ""); 
        });
        assertTrue(e.getMessage().contains("Username"));
    }

    @Test
    void testThatUpdatedUserCantUseEmailAlreadyInUse(){
        Person p1 = dummyPerson();
        doReturn(p1).when(personRepo).findPersonByEmail(p1.getEmail());
        Exception e = assertThrows(EmailAlreadyExistsException.class, () -> {
            service.updatePersonWithUsernameAndRemoveFromLegacyUsers(p1, p1.getUsername()); 
        });
        assertTrue(e.getMessage().contains("Email"));
    }

    @Test
    void testThatUpdatedUserCanUseSameUsername(){
        Person p1 = dummyPerson();
        doReturn(p1).when(personRepo).findPersonByUsername(p1.getUsername());
        doReturn(p1).when(personRepo).save(any());
        try {            
            service.updatePersonWithUsernameAndRemoveFromLegacyUsers(p1, p1.getUsername()); 
        } catch (Exception e) {
            fail("got error: " + e.getClass()); 
        }
    }

    private void validationAssertions(Object expected, Object actual){
        assertFalse(actual == null);
        assertSame(expected,actual);
    }

    private Person dummyLegacyPerson(boolean tempEmail,  boolean tempSSN){
        Random rand = new Random();
        String email = String.format("fake%05d@fakemegafake.email", rand.nextInt(100000));
        String SSN = String.format("000000%06d",rand.nextInt(1000000));
        if(!tempEmail) email = String.format("email%06d@email.com",rand.nextInt(1000000));
        if(!tempSSN) SSN = String.format("111111%06d",rand.nextInt(1000000));
        return new Person("name", "surname", email, SSN, "user", "qooqWan123!");
    }

    /* ----------------------------------------------------------
     * Impressive if a @Unique value is generated more than once.
     * ----------------------------------------------------------
     */
    private Person dummyPerson(){
        Random rand = new Random();
        String username = String.format("username%06d", rand.nextInt(1000000));
        return dummyPerson(username, "qooqWan123!");
    }

    private Person dummyPerson(String username){
        return dummyPerson(username, "qooqWan123!");
    }

    private Person dummyPerson(String username, String password){
        Random rand = new Random();
        String ssn = String.format("111111%06d",rand.nextInt(1000000));
        String email = String.format("email%06d@email.com",rand.nextInt(1000000));
        return new Person("name", "surname", email, ssn, username, password); 
    }

}
