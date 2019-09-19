package io.coderspotting.train.christmas.services;

public interface TrainService
{
    public void applyBrake() throws TrainServiceException;
    public void releaseBrake() throws TrainServiceException;
    public boolean isBrakeApplied() throws TrainServiceException;

    /*
        Speed is expressed in % (0 - 100) of maximum speed
     */
    public void setSpeed(int newSpeed) throws TrainServiceException;
    public int getSpeed() throws TrainServiceException;

    public void moveForward() throws TrainServiceException;
    public void moveReverse() throws TrainServiceException;
    public boolean isMovingForward() throws TrainServiceException;
}
