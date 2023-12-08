package dev.tarasshablii.opora.monolith.media.endpoint.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.Resource;

import java.util.UUID;

@Data
@Builder
public class MediaDto {
	private Resource media;
	private UUID id;
	private String contentType;
}
