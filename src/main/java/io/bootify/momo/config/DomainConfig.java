package io.bootify.momo.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("io.bootify.momo.domain")
@EnableJpaRepositories("io.bootify.momo.repos")
@EnableTransactionManagement
public class DomainConfig {
}
