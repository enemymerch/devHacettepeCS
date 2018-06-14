package com.can.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Configuration
public class Init extends AbstractAnnotationConfigDispatcherServletInitializer{

    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{Root.class};}

    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebMvc.class};
    }

    protected String[] getServletMappings() {
        return new String[]{
                "/"
        };
    }
}
