package se.kth.iv1201.group4.recruitment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * This class makes it possible to use custom security settings
 * for Spring MVC Security settings.
 *
 * @author Filip Garamvölgyi
 */
@Configuration
@EnableWebMvc
@EnableGlobalMethodSecurity(securedEnabled = false, prePostEnabled = true)
public class RecruitmentSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String CSS_FILES_LOCATION = "/resources/style/*";

    /**
    * Usees default {@link WebSecurityConfigurerAdapter} settings for the
    * constructor.
    */
    public RecruitmentSecurityConfig(){ super(); }

    /**
    * Configure what content can be accessed based on authentication,
    * authoriazation as well as other HTTP security settings
    *
    * @param http  used to set scurity settings for spring webserver
    */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
          .csrf().disable()
          .authorizeRequests()
          .antMatchers("/register*","/login*", CSS_FILES_LOCATION).permitAll()
          .anyRequest().authenticated()
          .and()
          .formLogin().loginPage("/login");
    }
}
