package io.coderspotting.train.christmas.api.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import io.coderspotting.train.christmas.api.graphql.types.ControllerStatus;
import io.coderspotting.train.christmas.api.graphql.types.TrainDirection;
import io.coderspotting.train.christmas.services.TrainService;
import io.coderspotting.train.christmas.services.TrainServiceException;
import org.springframework.stereotype.Component;

@Component
public class TrainControllerMutationResolver implements GraphQLMutationResolver
{
    private TrainService trainService;

    public TrainControllerMutationResolver(TrainService trainService)
    {
        this.trainService = trainService;
    }

    public ControllerStatus setSpeed(int newSpeed) throws TrainServiceException
    {
        if (newSpeed > 100) newSpeed = 100;
        if (newSpeed < 0) newSpeed = 0;

        trainService.setSpeed(newSpeed);

        return getControllerStatus();
    }

    public ControllerStatus setDirection(TrainDirection newDirection) throws TrainServiceException
    {
        switch(newDirection)
        {
            case FORWARD:
                trainService.moveForward();
                break;
            case REVERSE:
                trainService.moveReverse();
                break;
        }

        return getControllerStatus();
    }

    public ControllerStatus setBrake(boolean brakeApplied) throws TrainServiceException
    {
        if (brakeApplied)
            trainService.applyBrake();
        else
            trainService.releaseBrake();

        return getControllerStatus();
    }

    private ControllerStatus getControllerStatus() throws TrainServiceException
    {
        return new ControllerStatus(
                trainService.isMovingForward() ? TrainDirection.FORWARD : TrainDirection.REVERSE,
                trainService.isBrakeApplied(),
                trainService.getSpeed()
        );
    }
}
