package se.kth.iv1201.group4.recruitment.util;

/**
 * This class helps to find temporary data stored in a 
 * {@link se.kth.iv1201.group4.recruitment.domain.LegacyUser}
 *
 * @author Filip Garamvölgyi
 */
public class TemporaryDataMatcher {
    public static final String regexUsernameMatcher = "badUserFakeUser\\d{1,5}";
    public static final String regexEmailMatcher = "fake\\d{1,5}@fakemegafake.email";

    public static boolean isTemporaryEmail(String email){return email.matches(regexEmailMatcher);}
    public static boolean isTemporaryUsername(String username){return username.matches(regexUsernameMatcher);}
    public static boolean isTemporarySSN(String ssn){return ssn.substring(0,1).equals("0");}
}
