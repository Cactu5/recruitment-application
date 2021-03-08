package se.kth.iv1201.group4.recruitment.domain;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.fail;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;

/**
 * This is class with helper functions for validating constraints.
 * 
 * @author Leif Lindbäck
 * @author Cactu5
 * @version %I%
 */
public class ConstrainValidationHelper {
    /**
     * This is a helper function that helps validate
     * <code>ConstraintViolationException</code>.
     * 
     * Original from Leif Lindbäck
     * https://github.com/KTH-IV1201/bank/blob/15c1e687c76e7ccc203ef998f7e2584f28877e0b/src/test/java/se/kth/iv1201/appserv/bank/domain/AccountTest.java#L143
     * 
     * @param e                  the exception
     * @param expectedContraints the expected exceptions
     */
    public static void validateConstraints(ConstraintViolationException e, String... expectedContraints) {
        Set<ConstraintViolation<?>> result = e.getConstraintViolations();
        assertThat(result.size(), is(expectedContraints.length));
        for (String expectedContraint : expectedContraints) {
            assertThat(result, hasItem(hasProperty("messageTemplate", equalTo(expectedContraint))));
        }
    }

    /**
     * This function is used to test <code>ConstraintViolationException</code>. It
     * compares the exception messages against the expected messages.
     * 
     * @param <T>                the type of the entity
     * @param em                 the test entity manager
     * @param entity             the entity that's being tested
     * @param expectedContraints the expected constraint violation messages
     */
    public static <T> void testConstraintViolation(TestEntityManager em, T entity, String... expectedContraints) {
        try {
            em.persistAndFlush(entity);
            fail("ConstraintViolationException was not thrown.");
        } catch (ConstraintViolationException e) {
            validateConstraints(e, expectedContraints);
        }
    }
}
