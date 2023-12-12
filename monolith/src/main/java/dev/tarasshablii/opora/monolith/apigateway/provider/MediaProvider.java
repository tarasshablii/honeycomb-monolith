package dev.tarasshablii.opora.monolith.apigateway.provider;

import dev.tarasshablii.opora.monolith.apigateway.provider.dto.MediaDto;
import dev.tarasshablii.opora.monolith.apigateway.provider.mapper.MediaDtoMapper;
import dev.tarasshablii.opora.monolith.media.endpoint.MediaFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MediaProvider {

    private final MediaFacade mediaFacade;
    private final MediaDtoMapper mapper;

    public UUID create(MediaDto mediaDto) {
        return mediaFacade.createNew(mapper.toInboundDto(mediaDto));
    }

    public void deleteById(UUID mediaId) {
        mediaFacade.deleteById(mediaId);
    }

    public MediaDto getById(UUID mediaId) {
        return mapper.toOutboundDto(mediaFacade.getById(mediaId));
    }

    public void update(MediaDto mediaDto) {
        mediaFacade.update(mapper.toInboundDto(mediaDto));
    }
}
