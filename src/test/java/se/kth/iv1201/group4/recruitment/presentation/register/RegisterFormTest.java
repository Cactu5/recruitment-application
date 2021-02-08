package se.kth.iv1201.group4.recruitment.presentation.register;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.empty;

import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegisterFormTest {
    @Autowired
    private Validator validator;

    @Test
    public void testBlankEmail() {
        testInvalidEmail("", "{register.email.missing}");
    }

    @Test
    public void testInvalidEmail() {
        testInvalidEmail("@a.b.c", "{register.email.invalid}");
    }

    @Test
    public void testLongEmail() {
        testInvalidEmail("oifhjasdoifjasgdhrtmjrtnrdsdoifnmoimawoifmoepm@oisadfjgsdpofsdfsdfsdfsfsdfqefqfqefnfmwdoicmwepocsdofjs.se", "{register.email.length}");
    }

    @Test
    public void testShortPass() {
        testInvalidPassword("a1#", "{register.password.length}");
    }

    @Test
    public void testLongPass() {
        testInvalidPassword("ohasfhaoilfjaslokckj dshk)())((/)(/)736247326gyuhdsjhfgsfwueg2387gxybuweq2", "{register.password.length}");
    }

    @Test
    public void testNoLetterPass() {
        testInvalidPassword("189345(?+", "{register.password.missing-char}");
    }

    @Test
    public void testNoNumberPass() {
        testInvalidPassword("asjdkj%!=fjkd", "{register.password.missing-char}");
    }

    @Test
    public void testNoSymbolPass() {
        testInvalidPassword("dljlfaljdHYU27hd32", "{register.password.missing-char}");
    }

    @Test
    public void testBlankName() {
        testInvalidName("", "{register.name.missing}");
    }

    @Test
    public void testShortName() {
        testInvalidName("a", "{register.name.length}");
    }

    @Test
    public void testLongName() {
        testInvalidName("lasdjfdalfkkjhfkashfohfKUHIUHWdnfonfewopfqnflkanfqfacnfbwfbjkzbsdyhbfuiqhjnfckjncohqeohefhdjkfhiduhufiw", "{register.name.length}");
    }

    @Test
    public void testInvalidName() {
        testInvalidName("jlksdfh ojfp9", "{register.name.invalid-char}");
    }

    @Test
    public void testBlankUsername() {
        testInvalidUsername("", "{register.username.missing}");
    }

    @Test
    public void testShortUsername() {
        testInvalidUsername("a", "{register.username.length}");
    }

    @Test
    public void testLongUsername() {
        testInvalidUsername("lasdjfdalfkjdpfafpjdpasjdpiqjf03fj4239rty29r834d9823j47jd23984u3294dju32942d983y2948327x89", "{register.username.length}");
    }

    @Test
    public void testInvalidUsername() {
        testInvalidUsername("jlksdfh ojfp9", "{register.username.invalid-char}");
    }

    @Test
    public void testBlankSurname() {
        testInvalidSurname("", "{register.surname.missing}");
    }

    @Test
    public void testShortSurname() {
        testInvalidSurname("a", "{register.surname.length}");
    }

    @Test
    public void testLongSurame() {
        testInvalidSurname("lasdjfdalfkkjhfkashfohfKUHIUHWdnfonfewopfqnflkanfqfacnfbwfbjkzbsdyhbfuiqhjnfckjncohqeohefhdjkfhiduhufiw", "{register.surname.length}");
    }

    @Test
    public void testInvalidSurname() {
        testInvalidSurname("jlksdfh ojfp9", "{register.surname.invalid-char}");
    }

    @Test 
    public void testBlankSsn() {
        testInvalidSsn("", "{register.ssn.blank}");
    }

    @Test
    public void testInvalidSsn() {
        testInvalidSsn("11111111111a", "{register.ssn.invalid-char}");
    }

    @Test
    public void testShortSsn() {
        testInvalidSsn("11111111111", "{register.ssn.length}");
    }

    @Test
    public void testLongSsn() {
        testInvalidSsn("1111111111111", "{register.ssn.length}");
    }


    @Test
    public void testValidRegister() {
        RegisterForm form = new RegisterForm();
        form.setEmail("a@a.com");
        form.setUsername("aaaaaa");
        form.setPassword("abc123##");
        form.setName("name");
        form.setSurname("surname");
        form.setSsn("012345678910");
        Set<ConstraintViolation<RegisterForm>> result = validator.validate(form);
        assertThat(result, is(empty()));
    }

    private void testInvalidEmail(String invalidEmail, String expectedMsg) {
        RegisterForm form = new RegisterForm();
        form.setEmail(invalidEmail);
        form.setUsername("aaaaaa");
        form.setPassword("abc123##");
        form.setName("name");
        form.setSurname("surname");
        form.setSsn("012345678910");
        testInvalidForm(form, expectedMsg);
    }

    private void testInvalidUsername(String invalidUser, String expectedMsg) {
        RegisterForm form = new RegisterForm();
        form.setEmail("a@a.com");
        form.setUsername(invalidUser);
        form.setPassword("abc123##");
        form.setName("name");
        form.setSurname("surname");
        form.setSsn("012345678910");
        testInvalidForm(form, expectedMsg);
    }

    private void testInvalidPassword(String invalidPass, String expectedMsg) {
        RegisterForm form = new RegisterForm();
        form.setEmail("a@a.com");
        form.setUsername("aaaaaa");
        form.setPassword(invalidPass);
        form.setName("name");
        form.setSurname("surname");
        form.setSsn("012345678910");
        testInvalidForm(form, expectedMsg);
    }

    private void testInvalidName(String invalidName, String expectedMsg) {
        RegisterForm form = new RegisterForm();
        form.setEmail("a@a.com");
        form.setUsername("aaaaaa");
        form.setPassword("abc123##");
        form.setName(invalidName);
        form.setSurname("surname");
        form.setSsn("012345678910");
        testInvalidForm(form, expectedMsg);
    }

    private void testInvalidSurname(String invalidSurname, String expectedMsg) {
        RegisterForm form = new RegisterForm();
        form.setEmail("a@a.com");
        form.setUsername("aaaaaa");
        form.setPassword("abc123##");
        form.setName("name");
        form.setSurname(invalidSurname);
        form.setSsn("012345678910");
        testInvalidForm(form, expectedMsg);
    }

    private void testInvalidSsn(String invalidSsn, String expectedMsg) {
        RegisterForm form = new RegisterForm();
        form.setEmail("a@a.com");
        form.setUsername("aaaaaa");
        form.setPassword("abc123##");
        form.setName("name");
        form.setSurname("surname");
        form.setSsn(invalidSsn);
        testInvalidForm(form, expectedMsg);
    }

    private void testInvalidForm(RegisterForm form, String expectedMsg) {
        Set<ConstraintViolation<RegisterForm>> result = validator.validate(form);
        assertThat(result.size(), is(1));
        assertThat(result, hasItem(hasProperty("messageTemplate", equalTo(expectedMsg))));
    }
}