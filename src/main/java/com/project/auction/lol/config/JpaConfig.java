package com.project.auction.lol.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // Jpa Auditing 활성화 ( Jpa Auditing 어노테이션들을 모두 활성화 할 수 있도록 )
public class JpaConfig {
}
