package io.coderspotting.train.christmas.services;

public class TrainServiceException extends Exception
{
    public TrainServiceException() {
        super();
    }

    public TrainServiceException(String message) {
        super(message);
    }

    public TrainServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrainServiceException(Throwable cause) {
        super(cause);
    }

    protected TrainServiceException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
