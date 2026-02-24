package com.sailorham.lock.global.exception;

public record ErrorResponse(
	String message,
	String details
) {

	public static ErrorResponse of(String message, String details) {
		return new ErrorResponse(message, details);
	}
}
