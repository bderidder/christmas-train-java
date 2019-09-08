package io.coderspotting.train.christmas.services;

import com.pi4j.io.gpio.*;

import javax.annotation.PreDestroy;

public class TrainServiceGpio implements TrainService
{
    private int speed;
    private boolean brake;
    private boolean direction;

    private GpioController gpio;

    private GpioPinDigitalOutput brakePin;
    private GpioPinDigitalOutput forwardPin;
    private GpioPinDigitalOutput reversePin;

    public TrainServiceGpio()
    {
        speed = 0;
        brake = true;
        direction = true;

        initGpio();
    }

    private void initGpio()
    {
        gpio = GpioFactory.getInstance();

        brakePin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "BrakePin", PinState.HIGH);
        forwardPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "ForwardPin", PinState.HIGH);
        reversePin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "ReversePin", PinState.LOW);

        brakePin.setShutdownOptions(true, PinState.LOW);
        forwardPin.setShutdownOptions(true, PinState.LOW);
        reversePin.setShutdownOptions(true, PinState.LOW);
    }

    @PreDestroy
    private void preDestroy()
    {
        gpio.shutdown();
    }

    @Override
    public void applyBrake() 
    {
        brake = true;

        brakePin.high();
    }

    @Override
    public void releaseBrake() 
    {
        brake = false;

        brakePin.low();
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
        direction = true;

        forwardPin.high();
        reversePin.low();
    }

    @Override
    public void moveReverse() 
    {
        direction = false;

        forwardPin.low();
        reversePin.high();
    }

    @Override
    public boolean isMovingForward()
    {
        return direction;
    }
}