package com.sailorham.lock.domain.enrollment.dto;

import jakarta.validation.constraints.NotNull;

public record EnrollmentRequest(
	@NotNull(message = "학생 ID는 필수입니다.")
	Long studentId,

	@NotNull(message = "강좌 ID는 필수입니다.")
	Long courseId
) {
}
