package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.Person;

@Transactional(propagation = Propagation.MANDATORY)
@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    /**
     * Returns the applicant associated to the specified person.
     * 
     * @param person the specified person.
     * @return the applicant associated to the specified person.
     */
    Applicant findApplicantByPerson(Person person);

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    Applicant save(Applicant applicant);

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    void delete(Applicant applicant);
}
