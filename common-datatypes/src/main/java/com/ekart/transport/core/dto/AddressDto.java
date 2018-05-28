package com.ekart.transport.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDto implements Serializable {

    @NotNull
    @Size(max = 250, message = "Address Line 1 can not be greater than 250")
    private String addressLine1;

    @Size(max = 250, message = "Address Line 2 can not be greater than 250")
    private String addressLine2;

    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "City can consists of characters, numbers, " +
            "hyphen and underscore.")
    private String city;

    @Size(max = 50)
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "State can consists of characters, numbers, " +
            "hyphen and underscore.")
    private String state;

    @NotNull
    @Size(min = 6, max = 6, message = "Pincode will be of size 6 only")
    @Pattern(regexp = "^[0-9]+$", message = "Pincode can consists of numbers only")
    private String pincode;

    private Double latitude;

    private Double longitude;
}
