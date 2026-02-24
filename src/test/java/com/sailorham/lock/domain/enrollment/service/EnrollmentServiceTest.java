package com.sailorham.lock.domain.enrollment.service;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sailorham.lock.domain.course.entity.Course;
import com.sailorham.lock.domain.course.repository.CourseRepository;
import com.sailorham.lock.domain.enrollment.repository.EnrollmentRepository;
import com.sailorham.lock.domain.student.entity.Student;
import com.sailorham.lock.domain.student.repository.StudentRepository;

@SpringBootTest
class EnrollmentServiceTest {

	@Autowired
	private EnrollmentService enrollmentService;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private EnrollmentRepository enrollmentRepository;

	private Long targetCourseId;

	@AfterEach
	void tearDown() {
		enrollmentRepository.deleteAllInBatch();
		studentRepository.deleteAllInBatch();
		courseRepository.deleteAllInBatch();
	}

	@BeforeEach
	void setUp() {

		// 테스트용 강의 1개 생성 - 정원 100명
		Course course = Course.builder()
			.title("데이터베이스 시스템")
			.maxCapacity(100L)
			.build();
		Course savedCourse = courseRepository.save(course);
		targetCourseId = savedCourse.getId();

		// 테스트용 학생 100명 생성
		List<Student> students = new ArrayList<>();
		for (int i = 1; i <= 100; i++) {
			Student student = Student.builder()
				.name("학생" + i)
				.build();

			students.add(student);
		}

		studentRepository.saveAll(students);
	}

	@Test
	@DisplayName("100명이 동시에 수강신청을 요청하면 락이 없기 때문에 갱신 유실이 발생합니다.")
	void enrollCourse_should_loseUpdates_when_100ConcurrentRequestsWithoutLock() throws InterruptedException {

		// given
		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);

		// when
		for (int i = 1; i <= threadCount; i++) {
			final Long studentId = (long)i;

			executorService.submit(() -> {
				try {
					enrollmentService.enrollCourse(studentId, targetCourseId);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		// then
		Course course = courseRepository.findById(targetCourseId).orElseThrow();
		System.out.println("최종 수강 인원: " + course.getEnrolledCount());

		assertThat(course.getEnrolledCount()).isNotEqualTo(100L);
	}
}
