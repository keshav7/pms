package com.ekart.transport.core.health;

/**
 * Created by shivam.jaiswal on 17/10/16.
 */
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class RotationTaskHealthIndicator implements HealthIndicator {

    private final Path path;

    @Autowired
    public RotationTaskHealthIndicator(@Value("${health.rotationConfig.persistentFilePath}") String path)
            throws IOException {
        log.info("Rotation file path" + path);
        this.path = Paths.get(path);
    }

    @Override
    public Health health() {
        if (Files.exists(path)) {
            return Health.up().withDetail("Health", "Machine is in Rotation").build();
        } else {
            return Health.down().withDetail("Health", "Machine is Out of Rotation").build();
        }
    }
}
