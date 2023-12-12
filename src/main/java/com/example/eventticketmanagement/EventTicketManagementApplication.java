package com.example.eventticketmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@SpringBootApplication
public class EventTicketManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventTicketManagementApplication.class, args);
    }

//    @Bean(name = "multipartResolver")
//    public MultipartResolver multipartResolver() {
//        StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
//        resolver.(5 * 1024 * 1024); // 5 MB
//        resolver.setMaxRequestSize(5 * 1024 * 1024); // 5 MB
//        return resolver;
//    }

}
