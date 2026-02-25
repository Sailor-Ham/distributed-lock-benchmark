package com.sailorham.lock.global.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class BaseTimeEntity {

	@CreatedDate
	@Column(nullable = false, updatable = false)
	LocalDateTime createdAt;

	@LastModifiedDate
	@Column(nullable = false)
	LocalDateTime updatedAt;
}
