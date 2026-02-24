package com.sailorham.lock.domain.enrollment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sailorham.lock.domain.course.entity.Course;
import com.sailorham.lock.domain.course.repository.CourseRepository;
import com.sailorham.lock.domain.enrollment.entity.Enrollment;
import com.sailorham.lock.domain.enrollment.repository.EnrollmentRepository;
import com.sailorham.lock.domain.student.entity.Student;
import com.sailorham.lock.domain.student.repository.StudentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentService {

	private final CourseRepository courseRepository;
	private final StudentRepository studentRepository;
	private final EnrollmentRepository enrollmentRepository;

	// V1: 동시성 제어가 적용되지 않은 기본 수강신청 로직
	@Transactional
	public void enrollCourse(Long studentId, Long courseId) {

		// 학생과 강좌 조회
		Student student = studentRepository.findById(studentId)
			.orElseThrow(() -> new IllegalArgumentException("학생이 존재하지 않습니다."));

		Course course = courseRepository.findById(courseId)
			.orElseThrow(() -> new IllegalArgumentException("강좌가 존재하지 않습니다."));

		// 수강 정원 검증 및 증가
		course.enroll();

		// 수강신청 정보 저장
		Enrollment enrollment = Enrollment.builder()
			.student(student)
			.course(course)
			.build();
		enrollmentRepository.save(enrollment);

		log.info("[EnrollmentService] 수강신청 완료: Student ID={}, Course ID={}", studentId, courseId);
	}
}
