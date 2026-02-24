package com.sailorham.lock.domain.enrollment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sailorham.lock.domain.enrollment.dto.EnrollmentRequest;
import com.sailorham.lock.domain.enrollment.service.EnrollmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EnrollmentController {

	private final EnrollmentService enrollmentService;

	@PostMapping("/v1/enrollment")
	public ResponseEntity<String> enrollV1(
		@Valid @RequestBody EnrollmentRequest request
	) {

		log.info("수강신청 요청 수신: Student ID={}, Course ID={}", request.studentId(), request.courseId());

		enrollmentService.enrollCourse(request.studentId(), request.courseId());

		return ResponseEntity.ok("V1: 수강신청 완료 (락 없음)");
	}
}
