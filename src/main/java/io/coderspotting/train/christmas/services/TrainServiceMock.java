package io.coderspotting.train.christmas.services;

public class TrainServiceMock implements TrainService
{
    private int speed;
    private boolean brake;
    private boolean direction;

    public TrainServiceMock()
    {
        speed = 0;
        brake = true;
        direction = true;
    }

    @Override
    public void applyBrake() 
    {
        brake = true;
    }

    @Override
    public void releaseBrake() 
    {
        brake = false;
    }

    @Override
    public boolean isBrakeApplied() 
    {
        return brake;
    }

    @Override
    public void setSpeed(int newSpeed) 
    {
        speed = newSpeed;
    }

    @Override
    public int getSpeed() 
    {
        return speed;
    }

    @Override
    public void moveForward() 
    {
        if (!direction)
        {
            setSpeed(0);
            direction = true;
        }
    }

    @Override
    public void moveReverse() 
    {
        if (direction)
        {
            setSpeed(0);
            direction = false;
        }
    }

    @Override
    public boolean isMovingForward()
    {
        return direction;
    }

}