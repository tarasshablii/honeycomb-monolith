package dev.tarasshablii.opora.monolith.media.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "media.database")
public record MediaProperties(
        String username,
        String password,
        String url,
        String bucket
) {
}
