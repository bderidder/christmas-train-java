package io.coderspotting.train.christmas.graphql.types;

public class ControllerStatus
{
    private TrainDirection direction;
    private boolean brake;
    private int speed;

    public ControllerStatus(TrainDirection direction, boolean brake, int speed)
    {
        this.direction = direction;
        this.brake = brake;
        this.speed = speed;
    }

    public TrainDirection getDirection()
    {
        return direction;
    }

    public void setDirection(TrainDirection direction)
    {
        this.direction = direction;
    }

    public boolean isBrake()
    {
        return brake;
    }

    public void setBrake(boolean brake)
    {
        this.brake = brake;
    }

    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }
}
