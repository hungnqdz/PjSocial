package com.project.socialNetwork.database;

import com.project.socialNetwork.model.account.Account;
import com.project.socialNetwork.model.request.RegisterRequest;
import com.project.socialNetwork.model.user.User;
import com.project.socialNetwork.repository.AccountRepository;
import com.project.socialNetwork.service.authentication.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;
import java.time.LocalDate;


@Configuration
@Slf4j
@AllArgsConstructor
public class H2Database {
    private final AccountRepository accountRepository;
    private final AuthenticationService authenticationService;

    @Bean
    CommandLineRunner initAccount() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                log.info(accountRepository.findAll().toString());
            }
        };
    }


}
