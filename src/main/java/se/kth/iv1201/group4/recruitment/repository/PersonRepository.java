package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import se.kth.iv1201.group4.recruitment.domain.Person;

@Transactional(propagation = Propagation.MANDATORY)
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

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
