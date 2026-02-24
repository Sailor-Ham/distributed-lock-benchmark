package com.sailorham.lock.domain.enrollment.entity;

import com.sailorham.lock.domain.course.entity.Course;
import com.sailorham.lock.domain.student.entity.Student;
import com.sailorham.lock.global.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@Table(
	name = "enrollment",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "uk_student_course",
			columnNames = {"student_id", "course_id"}
		)
	}
)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Enrollment extends BaseTimeEntity {

	@Id
	@Column(name = "enrollment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", nullable = false)
	Student student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", nullable = false)
	Course course;

	@Builder
	public Enrollment(Student student, Course course) {
		this.student = student;
		this.course = course;
	}
}
