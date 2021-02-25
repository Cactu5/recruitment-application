package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import se.kth.iv1201.group4.recruitment.domain.LegacyUser;
import se.kth.iv1201.group4.recruitment.domain.Person;

/**
 * Repository for {@link LegacyUser}.
 * Contains all methods for accessing data in the database concerning
 * {@link LegacyUser}.
 * 
 * @author Filip Garamvölgyi
 * @version %I%
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository
public interface LegacyUserRepository extends JpaRepository<LegacyUser, Long> {

    /**
     * find a legacy user by the given person.
     *
     * @param person  {@link Person} used to find {@link LegacyUser}.
     * @return      {@link LegacyUser} returned by the given person.
     */
    LegacyUser findLegacyUserByPerson(Person person);

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    LegacyUser save(LegacyUser legacyUser);

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    void delete(LegacyUser legacyUser);
}
