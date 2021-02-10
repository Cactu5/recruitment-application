package se.kth.iv1201.group4.recruitment.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import se.kth.iv1201.group4.recruitment.domain.Availability;

@DataJpaTest
public class AvailabilityRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Test
    public void testCreateAvailability() {
        Availability availability = new Availability(LocalDate.of(2021, 01, 01), LocalDate.of(2021, 01, 15));
        entityManager.persist(availability);
        entityManager.flush();

        List<Availability> found = availabilityRepository.findAll();
        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(availability));
        assertThat(availability.getFrom(), is(found.get(0).getFrom()));
        assertThat(availability.getTo(), is(found.get(0).getTo()));
    }
}
