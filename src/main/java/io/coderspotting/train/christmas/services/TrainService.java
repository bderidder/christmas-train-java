package io.coderspotting.train.christmas.services;

// TrainServiceGpio

public interface TrainService
{
    public void applyBrake();
    public void releaseBrake();
    public boolean isBrakeApplied();

    public void setSpeed(int newSpeed);
    public int getSpeed();

    public void moveForward();
    public void moveReverse();
    public boolean isMovingForward();
}
