package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import se.kth.iv1201.group4.recruitment.domain.Availability;

/**
 * Repository for {@link se.kth.iv1201.group4.recruitment.domain.Availability}.
 * Contains all methods for accessing data in the database concerning
 * {@link se.kth.iv1201.group4.recruitment.domain.Availability}.
 * 
 * @author Cactu5
 * @version %I%
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    Availability save(Availability availability);

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    void delete(Availability availability);
}
