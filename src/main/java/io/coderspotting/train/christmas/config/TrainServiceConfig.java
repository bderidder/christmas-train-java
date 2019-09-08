package io.coderspotting.train.christmas.config;

import io.coderspotting.train.christmas.services.TrainService;
import io.coderspotting.train.christmas.services.TrainServiceGpio;
import io.coderspotting.train.christmas.services.TrainServiceMock;
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
            return new TrainServiceGpio();
        }
        catch(UnsatisfiedLinkError e)
        {
            return new TrainServiceMock();
        }
    }
}