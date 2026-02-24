package com.sailorham.lock.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
	// WebMvcTest에서 AuditingEntityListener가 동작하지 않는 문제 해결을 위해 추가한 클래스입니다.
}
