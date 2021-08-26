package com.example.web.jpa.config;

import com.example.web.CrudAppApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Servlet initializer
 * @author Krzysztof Kubiś
 * @version 1.0
 * @since 11
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CrudAppApplication.class);
    }

}
