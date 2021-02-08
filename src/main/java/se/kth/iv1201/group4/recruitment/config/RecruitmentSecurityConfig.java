package se.kth.iv1201.group4.recruitment.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
        .withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("USER")
        .and()
        .withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("USER")
        .and()
        .withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
          .formLogin()
          .loginPage("/login")
          .loginProcessingUrl("/login");
    }
}
