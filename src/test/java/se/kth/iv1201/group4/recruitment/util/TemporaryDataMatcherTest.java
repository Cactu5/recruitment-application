package se.kth.iv1201.group4.recruitment.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TemporaryDataMatcherTest {
    private static final String TEMPORARY_EMAIL_EXAMPLE = "fake00000@fakemegafake.email";
    private static final String TEMPORARY_USERNAME_EXAMPLE = "badUserFakeUser00000";
    private static final String TEMPORARY_SSN_EXAMPLE = "000000000000";

    @Test
    void testIfTemporaryEmailIsValidatedAsTemporary(){
        assertTrue(TemporaryDataMatcher.isTemporaryEmail(TEMPORARY_EMAIL_EXAMPLE));
    }

    @Test
    void testIfTemporaryUsernameIsValidatedAsTemporary(){
        assertTrue(TemporaryDataMatcher.isTemporaryUsername(TEMPORARY_USERNAME_EXAMPLE));
    }

    @Test
    void testIfTemporarySSNIsValidatedAsTemporary(){
        assertTrue(TemporaryDataMatcher.isTemporarySSN(TEMPORARY_SSN_EXAMPLE));
    }

    @Test
    void testIfNotTemporaryEmailIsValidatedAsNotTemporary(){
        assertFalse(TemporaryDataMatcher.isTemporaryEmail("fake0@fakemegafake.email"));
        assertFalse(TemporaryDataMatcher.isTemporaryEmail("00000@fakemegafake.email"));
        assertFalse(TemporaryDataMatcher.isTemporaryEmail("00000@fakemegafake"));
        assertFalse(TemporaryDataMatcher.isTemporaryEmail("fake00000fakemegafake.email"));
    }

    @Test
    void testIfNotTemporaryUsernameIsValidatedAsNotTemporary(){
        assertFalse(TemporaryDataMatcher.isTemporaryUsername("badUserFakeUser0"));
        assertFalse(TemporaryDataMatcher.isTemporaryUsername("UserFakeUser00000"));
        assertFalse(TemporaryDataMatcher.isTemporaryUsername("badUserFake00000"));
        assertFalse(TemporaryDataMatcher.isTemporaryUsername("badUserFakeUser0000"));
    }

    @Test
    void testIfNotTemporarySSNIsValidatedAsNotTemporary(){
        assertFalse(TemporaryDataMatcher.isTemporarySSN("100000000000"));
    }

}
