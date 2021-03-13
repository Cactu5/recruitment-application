package se.kth.iv1201.group4.recruitment.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import se.kth.iv1201.group4.recruitment.application.PersonService;
import se.kth.iv1201.group4.recruitment.domain.Person;

/**
 * This class makes it possible to use custom security settings for Spring MVC
 * Security settings.
 *
 * @author Filip Garamvölgyi
 * @author Cactu5
 * @author William Stackenäs
 * @version %I%
 */
@Configuration
@EnableWebMvc
@EnableGlobalMethodSecurity(securedEnabled = false, prePostEnabled = true)
public class RecruitmentSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String CSS_FILES_LOCATION = "/resources/style/*";

    @Autowired
    private PersonService service;

    /**
     * Configure accounts that can be used to log in by specifying the
     * service class acting as the user details service
     *
     * @param auth The object used to build a manager object for authentication
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(Person.PASSWORD_ENCODER);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    /**
     * Usees default {@link WebSecurityConfigurerAdapter} settings for the
     * constructor.
     */
    public RecruitmentSecurityConfig() {
        super();
    }

    /**
     * Configure what content can be accessed based on authentication,
     * authorization as well as other HTTP security settings
     *
     * @param http used to set security settings for spring webserver
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/register*", "/login*", CSS_FILES_LOCATION).permitAll()
                .antMatchers("/success").authenticated().antMatchers("/success-legacy-user")
                .hasRole("LEGACY_USER").antMatchers("/success-applicant").hasRole("APPLICANT")
                .antMatchers("/success-recruiter").hasRole("RECRUITER").and().formLogin().loginPage("/login")
                .loginProcessingUrl("/login").defaultSuccessUrl("/success", true);
    }
}
