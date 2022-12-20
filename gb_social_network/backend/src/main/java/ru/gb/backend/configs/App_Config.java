package ru.gb.backend.configs;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("ru.gb.backend")
public class App_Config implements WebMvcConfigurer {
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("templates/web_app/images/**")) {
            registry.addResourceHandler("templates/web_app/images/**").addResourceLocations("file:/templates/web_app/images/");
        }
    }
}
