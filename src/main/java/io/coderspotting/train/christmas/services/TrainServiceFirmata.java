package io.coderspotting.train.christmas.services;

import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;

import javax.annotation.PreDestroy;
import java.io.IOException;

/*
    Channel A on Arduino Motor Shield

    DIRECTION = 12 (DIGITAL)
    BRAKE     = 9  (DIGITAL)
    SPEED     = 3  (PWM)
 */

public class TrainServiceFirmata implements TrainService
{
    private int powerVoltage;
    private int maxTrainVoltage;

    private IODevice device;

    private Pin directionPin;
    private Pin brakePin;
    private Pin speedPin;

    public TrainServiceFirmata(int powerVoltage, int maxTrainVoltage) throws TrainServiceException
    {
        this.powerVoltage = powerVoltage;
        this.maxTrainVoltage = maxTrainVoltage;

        initFirmata();
    }

    private void initFirmata() throws TrainServiceException
    {
        device = new FirmataDevice("/dev/ttyAMA0");

        try
        {
            device.start();
            device.ensureInitializationIsDone();

            directionPin = device.getPin(12);
            directionPin.setMode(Pin.Mode.OUTPUT);
            moveForward();

            brakePin = device.getPin(9);
            brakePin.setMode(Pin.Mode.OUTPUT);
            applyBrake();

            speedPin = device.getPin(3);
            speedPin.setMode(Pin.Mode.PWM);
            setSpeed(0);
        }
        catch (Exception e)
        {
            throw new TrainServiceException(e);
        }
    }

    @PreDestroy
    private void preDestroy() throws TrainServiceException
    {
        try
        {
            device.stop();
        }
        catch (Exception e)
        {
            throw new TrainServiceException(e);
        }
    }

    @Override
    public void applyBrake() throws TrainServiceException
    {
        try
        {
            brakePin.setValue(1);
        }
        catch (IOException e)
        {
            throw new TrainServiceException(e);
        }
    }

    @Override
    public void releaseBrake() throws TrainServiceException
    {
        try
        {
            brakePin.setValue(0);
        }
        catch (IOException e)
        {
            throw new TrainServiceException(e);
        }
    }

    @Override
    public boolean isBrakeApplied() 
    {
        return brakePin.getValue() == 1;
    }

    @Override
    public void setSpeed(int newSpeed) throws TrainServiceException
    {
        if (newSpeed > 100) newSpeed = 100;
        if (newSpeed < 0) newSpeed = 0;

        int speedStep = speedPercentageToSpeedStep(newSpeed);

        try
        {
            setSpeedStep(speedStep);
        }
        catch (IOException e)
        {
            throw new TrainServiceException(e);
        }
    }

    @Override
    public int getSpeed() 
    {
        return speedStepToSpeedPercentage(speedPin.getValue());
    }

    @Override
    public void moveForward() throws TrainServiceException
    {
        try
        {
            if (directionPin.getValue() != 1)
            {
                setSpeed(0);
                directionPin.setValue(1);
            }
        }
        catch (IOException e)
        {
            throw new TrainServiceException(e);
        }
    }

    @Override
    public void moveReverse() throws TrainServiceException
    {
        try
        {
            if (directionPin.getValue() == 1)
            {
                setSpeed(0);
                directionPin.setValue(0);
            }
        }
        catch (IOException e)
        {
            throw new TrainServiceException(e);
        }
    }

    @Override
    public boolean isMovingForward()
    {
        return directionPin.getValue() == 1;
    }

    private void setSpeedStep(long speedStep) throws IOException
    {
        if (speedStep > 255) speedStep = 255;
        if (speedStep < 0) speedStep = 0;

        speedPin.setValue(speedStep);
    }

    private int speedPercentageToSpeedStep(int speedPercentage)
    {
        if (speedPercentage > 100) speedPercentage = 100;
        if (speedPercentage < 0) speedPercentage = 0;

        return (int) Math.floor(maxSpeedStep() * (double)speedPercentage / 100.0);
    }

    private int speedStepToSpeedPercentage(long speedStep)
    {
        if (speedStep > 255) speedStep = 255;
        if (speedStep < 0) speedStep = 0;

        return (int) Math.floor(100.0 * ((double)speedStep + 1) / maxSpeedStep());
    }

    private long maxSpeedStep()
    {
        return (long) Math.floor(255.0 / (double)this.powerVoltage * (double)this.maxTrainVoltage);
    }
}