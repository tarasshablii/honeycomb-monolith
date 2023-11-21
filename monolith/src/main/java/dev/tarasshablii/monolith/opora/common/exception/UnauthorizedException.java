package dev.tarasshablii.monolith.opora.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UnauthorizedException extends RuntimeException {

	private final String errorMessage;
}