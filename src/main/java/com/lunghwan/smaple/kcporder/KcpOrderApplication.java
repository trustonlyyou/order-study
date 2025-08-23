package com.lunghwan.smaple.kcporder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KcpOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(KcpOrderApplication.class, args);
    }

}
