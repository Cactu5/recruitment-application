package se.kth.iv1201.group4.recruitment.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Contains properties from application.properities, starting with 'se.kth
 * .iv1201.server'.
 */
/**
 * Contains properties from application.properties prefixed with
 * "se.kth.iv1201.group4.recruitment".
 *
 * @author William Stackenäs
 */
@ConfigurationProperties(prefix = "se.kth.iv1201.group4.recruitment")
public class ServerProperties {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerProperties.class);
    
    private String loginUrl;
    private String registerUrl;

    /**
     * Getter for the login URL
     * 
     * @return The login URL
     */
    public String getLoginUrl() {
        return loginUrl;
    }

    /**
     * Getter for the register URL
     * 
     * @return The register URL
     */
    public String getRegisterUrl() {
        return registerUrl;
    }
}
