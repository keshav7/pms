package com.ekart.transport.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Immutable
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationDto implements Serializable {

    @NotNull
    @Size(min = 2, max = 20, message = "Location Type can be of size 2 to 20 chars")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Location Type can consists of characters, numbers, " +
            "hyphen and underscore.")
    private String type;

    @NotNull
    @Size(min = 2, max = 50, message = "Location External Id can be of size 2 to 50 chars")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Location External Id can consists of characters, numbers, " +
            "hyphen and underscore.")
    private String externalId;

    private String locationId;

    private AddressDto address;
}
