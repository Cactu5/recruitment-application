package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import se.kth.iv1201.group4.recruitment.domain.Person;
import se.kth.iv1201.group4.recruitment.domain.Recruiter;

/**
 * Repository for {@link se.kth.iv1201.group4.recruitment.domain.Recruiter}.
 * Contains all methods for accessing data in the database concerning
 * {@link se.kth.iv1201.group4.recruitment.domain.Recruiter}.
 * 
 * @author Cactu5
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {

    /**
     * Returns the recruiter associated to the specified person.
     * 
     * @param person the specified person.
     * @return the recruiter associated to the specified person.
     */
    Recruiter findRecruiterByPerson(Person person);

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    Recruiter save(Recruiter recruiter);

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    void delete(Recruiter recruiter);
}
