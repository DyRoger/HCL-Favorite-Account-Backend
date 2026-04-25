package org.bank.hcl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class FavAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(FavAccountApplication.class, args);
    }

}
