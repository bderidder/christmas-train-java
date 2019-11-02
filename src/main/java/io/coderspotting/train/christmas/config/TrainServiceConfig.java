package io.coderspotting.train.christmas.config;

import io.coderspotting.train.christmas.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrainServiceConfig
{
    private final Logger logger = LoggerFactory.getLogger(TrainServiceConfig.class);

    @Bean
    public TrainService createTrainService()
    {
        try
        {
            return new TrainServiceFirmata(12, 10);
        }
        catch(Exception e)
        {
            logger.warn("Could not load TrainService implementation", e);
            logger.warn("Could not load TrainService implementation, reverting to mock implementation");

            return new TrainServiceMock();
        }
    }
}