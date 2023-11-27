package dev.tarasshablii.opora.microservices.initiatives.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotFoundException extends RuntimeException {

	private final String errorMessage;
}