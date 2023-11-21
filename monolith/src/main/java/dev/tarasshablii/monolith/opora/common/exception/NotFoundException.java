package dev.tarasshablii.monolith.opora.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotFoundException extends RuntimeException {

	private final String errorMessage;
}