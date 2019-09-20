package io.coderspotting.train.christmas.api.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import io.coderspotting.train.christmas.api.graphql.types.ControllerStatus;
import io.coderspotting.train.christmas.api.graphql.types.TrainDirection;
import io.coderspotting.train.christmas.services.TrainService;
import io.coderspotting.train.christmas.services.TrainServiceException;
import org.springframework.stereotype.Component;

@Component
public class TrainControllerQueryResolver implements GraphQLQueryResolver
{
    private TrainService trainService;

    public TrainControllerQueryResolver(TrainService trainService)
    {
        this.trainService = trainService;
    }

    public ControllerStatus getControllerStatus() throws TrainServiceException
    {
        return new ControllerStatus(
                trainService.isMovingForward() ? TrainDirection.FORWARD : TrainDirection.REVERSE,
                trainService.isBrakeApplied(),
                trainService.getSpeed()
        );
    }
}
