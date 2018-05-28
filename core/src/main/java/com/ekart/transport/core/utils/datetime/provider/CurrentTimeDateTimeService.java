package com.ekart.transport.core.utils.datetime.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

/**
 * This class returns the current time.
 *
 * @author Petri Kainulainen
 */
@Component("dateTimeService")
public class CurrentTimeDateTimeService implements DateTimeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentTimeDateTimeService.class);

    @Override
    public ZonedDateTime getCurrentDateAndTime() {
        return ZonedDateTime.now();
    }
}