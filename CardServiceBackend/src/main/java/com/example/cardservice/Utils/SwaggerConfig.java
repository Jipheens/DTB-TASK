package com.example.cardservice.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${myswagger.openapi.dev-url}")
    private String devUrl;

    @Value("${myswagger.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("wahomejipheens@gmail.com");
        contact.setName("JIPHEENS");
        contact.setUrl("https://www.sic-edgetechnologies.co.ke/");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Cards Management APIs")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage Cards.").termsOfService("https://www.sic-edgetechnologies.co.ke/")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}







//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import io.swagger.v3.oas.models.servers.Server;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//public class SwaggerConfig {
//
//    @Value("${myswagger.openapi.dev-url}")
//    private String devUrl;
//
//    @Value("${myswagger.openapi.prod-url}")
//    private String prodUrl;
//
//    @Bean
//    public OpenAPI myOpenAPI() {
//        Server devServer = new Server();
//        devServer.setUrl(devUrl);
//        devServer.setDescription("Server URL in Development environment");
//
//        Server prodServer = new Server();
//        prodServer.setUrl(prodUrl);
//        prodServer.setDescription("Server URL in Production environment");
//
//        Contact contact = new Contact();
//        contact.setEmail("wahomejipheens@gmail.com");
//        contact.setName("JIPHEENS");
//        contact.setUrl("https://www.emtechhouse.co.ke/");
//
//        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
//
//        Info info = new Info()
//                .title("Supplier Management APIs")
//                .version("1.0")
//                .contact(contact)
//                .description("This API exposes endpoints to manage suppliers.").termsOfService("https://www.emtechhouse.co.ke/")
//                .license(mitLicense);
//
//        return new OpenAPI().info(info).servers(Arrays.asList(devServer, prodServer));
//
//        //return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
//    }
//}




























//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//
//import java.util.Collections;
//
//@Configuration
//public class SwaggerConfig {
//    public static final String AUTHORIZATION_HEADER = "Authorization";
//
//    private ApiInfo apiInfo() {
//        return new ApiInfo("EMT Recon Master Server from E&M Technology House LTD",
//                "Comprehensive Auto Recon utility.",
//                "1.0",
//                "Terms of service",
//                new Contact("E&M", "www.emtechhouse.co.ke", "developer@emtechhouse.co.ke"),
//                "License of API",
//                "https.emtechhouse.co.ke",
//                Collections.emptyList());
//    }
//}

//import org.springdoc.core.GroupedOpenApi;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class SwaggerConfig implements WebMvcConfigurer {
//
//    @Bean
//    public GroupedOpenApi publicApi() {
//        String[] packagesToScan = {"com.example.controllers.publicapi"};
//        return GroupedOpenApi.builder()
//                .group("public-api")
//                .packagesToScan(packagesToScan)
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi privateApi() {
//        String[] packagesToScan = {"com.example.controllers.privateapi"};
//        return GroupedOpenApi.builder()
//                .group("private-api")
//                .packagesToScan(packagesToScan)
//                .build();
//    }
//}
//
//
//
