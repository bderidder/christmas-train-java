package io.coderspotting.train.christmas.api.resources;

import io.coderspotting.train.christmas.api.models.TrainControllerRequest;
import io.coderspotting.train.christmas.api.models.TrainControllerStatus;
import io.coderspotting.train.christmas.services.TrainServiceException;
import org.springframework.web.bind.annotation.*;

import io.coderspotting.train.christmas.services.TrainService;

@RestController
@RequestMapping("/api/train/control")
public class TrainControllerResource
{
    private TrainService trainService;

    public TrainControllerResource(TrainService trainService)
    {
        this.trainService = trainService;
    }

    @GetMapping
    public TrainControllerStatus get() throws TrainServiceException
    {
        return createTrainControllerStatus();
    }

    @PostMapping
    public TrainControllerStatus post(@RequestBody TrainControllerRequest controlRequest) throws TrainServiceException
    {
        if (controlRequest.brake != null)
            if (controlRequest.brake)
                trainService.applyBrake();
            else
                trainService.releaseBrake();

        if (controlRequest.speed != null)
            trainService.setSpeed(controlRequest.speed);

        if (controlRequest.direction != null)
            if (controlRequest.direction)
                trainService.moveForward();
            else
                trainService.moveReverse();

        return createTrainControllerStatus();
    }

    private TrainControllerStatus createTrainControllerStatus() throws TrainServiceException
    {
        var response = new TrainControllerStatus();

        response.speed = trainService.getSpeed();
        response.direction = trainService.isMovingForward();
        response.brake = trainService.isBrakeApplied();

        return response;
    }
}