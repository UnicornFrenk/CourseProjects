package com.github.configuration;

import com.github.config.ServiceConfig;
import com.github.servlet.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.github")
        public class WebConfig {

//    @Bean
//    ViewResolver viewResolver(){
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setSuffix(".jsp");
//        return resolver;
//    }


    private ServiceConfig serviceConfig;

    public WebConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    @Bean
    public UserController authUserController() {
        return new UserController(serviceConfig.personService());
    }

    @Bean
    public ItemController forItemController(){
        return new ItemController(serviceConfig.itemService());
    }

    @Bean
    public LoginController loginController(){
        return new LoginController(serviceConfig.personService());
    }

    @Bean
    public UrlBasedViewResolver tilesViewResolver(){
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(TilesView.class);
        return resolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer(){
        final TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions("/WEB-INF/tiles*.xml");
        return tilesConfigurer;
    }
}
