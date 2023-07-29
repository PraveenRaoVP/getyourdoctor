package com.getyourdoc.getyourdoctors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin
public class GetyourdoctorsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetyourdoctorsApplication.class, args);
    }

}
