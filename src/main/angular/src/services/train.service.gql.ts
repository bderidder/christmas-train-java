import gql from "graphql-tag";

export const getControllerStatusQuery = gql`
    query {
        getControllerStatus {
            speed
            direction
            brake
        }
    }
`;

export const setDirectionMutation = gql`
    mutation setDirection($direction: TrainDirection!) {
        setDirection(direction: $direction) {
            speed
            direction
            brake
        }
    }
`;

export const setSpeedMutation = gql`
    mutation setSpeed($speed: Int!) {
        setSpeed(speed: $speed) {
            speed
            direction
            brake
        }
    }
`;

export const setBrakeMutation = gql`
    mutation setBrake($brakeApplied: Boolean!) {
        setBrake(brakeApplied: $brakeApplied) {
            speed
            direction
            brake
        }
    }
`;
