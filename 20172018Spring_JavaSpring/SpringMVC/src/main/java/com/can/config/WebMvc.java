package com.can.config;

import com.can.model.Product;
import com.can.repository.*;
import com.can.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("com.can")
public class WebMvc extends WebMvcConfigurerAdapter {
    @Bean
    public OrderRepository orderRepositoryResolver() { return new OrderRepositoryImpl(); }
    @Bean
    public ProductRepository productRepositoryResolver(){ return new ProductRepositoryImpl();}
    @Bean
    public UserRepository userRepositoryResolver(){ return new UserRepositoryImpl();}
    @Bean
    public UserService userServiceResolver(){ return new UserServiceImpl();}
    @Bean
    public ProductService productServiceResolver(){return new ProductServiceImpl();}
    @Bean
    public OrderService orderServiceResolver(){ return new OrderServiceImpl();}

    @Bean
    public InternalResourceViewResolver resolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }
}
