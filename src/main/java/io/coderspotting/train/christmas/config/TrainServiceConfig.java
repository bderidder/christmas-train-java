package io.coderspotting.train.christmas.config;

import io.coderspotting.train.christmas.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrainServiceConfig
{
    @Bean
    public TrainService createTrainService()
    {
        try
        {
            return new TrainServiceFirmata(12, 10);
        }
        catch(UnsatisfiedLinkError e)
        {
            return new TrainServiceMock();
        }
        catch (TrainServiceException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}