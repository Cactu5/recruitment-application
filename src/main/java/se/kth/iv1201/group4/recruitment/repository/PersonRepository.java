package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import se.kth.iv1201.group4.recruitment.domain.Person;

/**
 * Repository for {@link se.kth.iv1201.group4.recruitment.domain.Person}.
 * Contains all methods for accessing data in the database concerning
 * {@link se.kth.iv1201.group4.recruitment.domain.Person}.
 * 
 * @author Cactu5
 * @author William Stackenäs
 * @version %I%
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    /**
     * Returns the person with the specified email.
     * 
     * @param email the email of the person searched for.
     * @return the person with the specified email.
     */
    Person findPersonByEmail(String email);

    /**
     * Returns the person with the specified username.
     * 
     * @param username the username of the person searched for.
     * @return the person with the specified username.
     */
    Person findPersonByUsername(String username);

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    Person save(Person person);

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    void delete(Person person);
}
