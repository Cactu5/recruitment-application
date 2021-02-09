package se.kth.iv1201.group4.recruitment.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@SpringJUnitWebConfig(initializers = ConfigDataApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"se.kth.iv1201.group4.recruitment"})
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, RecruitmentConfigTest.class  })
class RecruitmentConfigTest implements TestExecutionListener {
    static final String STATIC_RESOURCES_TEST_LOCATION
        = "/resource/TO_BE_ADDED";
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Disabled
    @Test
    void testStaticResourcesCanBeAccessed() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders.get(STATIC_RESOURCES_TEST_LOCATION))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentType()
            .equalsIgnoreCase("CONTENT TYPE ENTERED HERE");
    }
    @Test
    void testCorrectTemplateEngineIsUsed(@Autowired @Qualifier("recruitmentTemplateEngine") 
            SpringTemplateEngine templateEngine,
            @Autowired ThymeleafViewResolver viewResolver){
        assertEquals(
            templateEngine, viewResolver.getTemplateEngine(), 
            "configured template engine is not used..."
        );
    }

    @Test
    void testCorrectTemplateResolverIsUsed(@Autowired ThymeleafViewResolver viewResolver,
            @Autowired SpringResourceTemplateResolver templateResolver){
        assertTrue(
                ((SpringTemplateEngine) viewResolver.getTemplateEngine())
                .getTemplateResolvers()
                .contains(templateResolver), "configured template resolver is not used...");
    }

    @Test
    void testCorrectLocaleChangeInterceptorCreated(
            @Autowired LocaleChangeInterceptor LCI){
        assertThat ("Correct LocaleChangeInterceptor was not configured", LCI,
                allOf(
                    hasProperty("paramName",equalTo("lang")),
                    hasProperty("httpMethods", arrayContainingInAnyOrder("GET", "POST")),
                    hasProperty("ignoreInvalidLocale", equalTo(true))));
    }
    
    @Test
    void testCorrectReloadableResourceBundleMsgSrcCreate(
        @Autowired ReloadableResourceBundleMessageSource msgSrc
    ){
        assertThat("Correct ReloadableResourceBundleMessageSource was not configured...",
                msgSrc,
                hasProperty("basenameSet", containsInAnyOrder(
                    RecruitmentConfig.I18N_VALIDATION_MESSAGES_LOCATION,
                    RecruitmentConfig.I18N_MESSAGES_LOCATION
                )));
    }
}
