package com.sailorham.lock.domain.course.entity;

import com.sailorham.lock.global.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@Table(name = "course")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course extends BaseTimeEntity {

	@Id
	@Column(name = "course_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(nullable = false)
	String title;

	@Column(nullable = false)
	Long maxCapacity;

	@Column(nullable = false)
	Long enrolledCount;

	@Builder
	public Course(String title, Long maxCapacity) {
		this.title = title;
		this.maxCapacity = maxCapacity;
		this.enrolledCount = 0L;
	}

	public void enroll() {
		if (enrolledCount >= maxCapacity) {
			throw new IllegalStateException("수강 정원이 초과되었습니다.");
		}

		enrolledCount++;
	}
}
