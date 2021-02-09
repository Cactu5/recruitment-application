package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.kth.iv1201.group4.recruitment.domain.Availability;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    @Override
    Availability save(Availability availability);

    @Override
    void delete(Availability availability);
}
