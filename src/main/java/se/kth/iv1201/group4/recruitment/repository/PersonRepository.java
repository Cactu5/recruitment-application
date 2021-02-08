package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.kth.iv1201.group4.recruitment.domain.Person;
import se.kth.iv1201.group4.recruitment.domain.Recruiter;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    /**
     * Returns the person with the specified username.
     * 
     * @param username the username of the person searched for.
     * @return the person with the specified username.
     */
    Person findPersonByUsername(String username);

    @Override
    Person save(Person person);

    @Override
    void delete(Person person);
}
