package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.kth.iv1201.group4.recruitment.domain.Person;
import se.kth.iv1201.group4.recruitment.domain.Recruiter;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {

    /**
     * Returns the recruiter associated to the specified person.
     * 
     * @param person the specified person.
     * @return the recruiter associated to the specified person.
     */
    Recruiter findRecruiterByPerson(Person person);

    @Override
    Recruiter save(Recruiter recruiter);

    @Override
    void delete(Recruiter recruiter);
}
