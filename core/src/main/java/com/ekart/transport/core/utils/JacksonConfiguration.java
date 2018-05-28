package com.ekart.transport.core.utils;

import com.ekart.transport.core.utils.datetime.serialization.CustomDateTimeDeserializer;
import com.ekart.transport.core.utils.datetime.serialization.CustomDateTimeSerializer;
import com.ekart.transport.core.utils.datetime.serialization.CustomLocalDateSerializer;
import com.ekart.transport.core.utils.datetime.serialization.ISO8601LocalDateDeserializer;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JacksonConfiguration {

    @Bean
    public JodaModule jacksonJodaModule() {
        JodaModule module = new JodaModule();
        module.addSerializer(DateTime.class, new CustomDateTimeSerializer());
        module.addDeserializer(DateTime.class, new CustomDateTimeDeserializer());
        module.addSerializer(LocalDate.class, new CustomLocalDateSerializer());
        module.addDeserializer(LocalDate.class, new ISO8601LocalDateDeserializer());
        return module;
    }
}

