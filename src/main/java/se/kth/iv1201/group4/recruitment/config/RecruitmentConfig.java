package se.kth.iv1201.group4.recruitment.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import nz.net.ultraq.thymeleaf.LayoutDialect;

/**
 * Contains configurations for SpringMVC settings.
 *
 * @author Filip Garamvölgyi
 */
@EnableWebMvc
@Configuration
@ComponentScan({"se.kth.iv1201.group4.recruitment"})
public class RecruitmentConfig implements WebMvcConfigurer, ApplicationContextAware {  
    public static final String TEMPLATE_FILE_LOCATION = "classpath:/templates/";
    public static final String STATIC_RESOURCES_LOCATION = "classpath:/web-root/";
    public static final int CHACHE_PERIOD_FOR_STATIC_FILES_SEC = 1;

    private ApplicationContext applicationContext;

    /**
     * @param applicationContext    applicationContext used for the backend
     *                              of the webserver.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    /** 
     * Makes sure that every view of the web server will be resolved by 
     * {@link ThymeleafViewResolver} also sets the meta data of the view resolver
     * 
     * @return {@link ThymeleafViewResolver}    contains the settins set
     */ 
    @Bean 
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setContentType("text/html; charset=UTF-8");
        viewResolver.setTemplateEngine(templateEngine());
        return viewResolver;
    }

    /**
     * Configuration for Thymeleaf's template engine integration with spring.
     *
     * @return {@link SpringTemplateEngine}    used for all template resolutions.
     */
    @Bean(name = "recruitmentTemplateEngine")
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.addDialect(new LayoutDialect());
        return templateEngine;
    }

    /**
     * Configurations to intergrate Thymeleaf with Spring
     *
     * @return {@link SpringResourceTemplateResolver}   Only template resolver to 
     *                                                  be used by the web server
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = 
            new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);        
        templateResolver.setPrefix(TEMPLATE_FILE_LOCATION);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);

        return templateResolver;
    }
    
    /**
     * Configure static resources to be accessed by web clients.
     *
     * @param registry    used to configure static resources
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
            .addResourceLocations(STATIC_RESOURCES_LOCATION)
            .setCachePeriod(CHACHE_PERIOD_FOR_STATIC_FILES_SEC)
            .resourceChain(true)
            .addResolver(new PathResourceResolver());
    }
}
