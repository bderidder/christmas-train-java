import {Apollo} from "apollo-angular";
import gql from "graphql-tag";
import {Injectable} from "@angular/core";

const getControllerStatusQuery = gql`
    query {
        getControllerStatus {
            speed
            direction
            brake
        }
    }
`;

const setDirectionMutation = gql`
    mutation setDirection($direction: TrainDirection!) {
        setDirection(direction: $direction) {
            speed
            direction
            brake
        }
    }
`;

const setSpeedMutation = gql`
    mutation setSpeed($speed: Int!) {
        setSpeed(speed: $speed) {
            speed
            direction
            brake
        }
    }
`;

const setBrakeMutation = gql`
    mutation setBrake($brakeApplied: Boolean!) {
        setBrake(brakeApplied: $brakeApplied) {
            speed
            direction
            brake
        }
    }
`;

export enum TrainDirection
{
  FORWARD = "FORWARD",
  REVERSE = "REVERSE"
}

export interface ControllerStatus
{
  direction: TrainDirection;
  brake: boolean;
  speed: number;
}

@Injectable({
  providedIn: 'root'
})
export class TrainService
{
  constructor(private apollo: Apollo) {}

  getControllerStatus() : Promise<ControllerStatus>
  {
    return new Promise<ControllerStatus>(
      resolve =>
      {
        this.apollo.query({
          query: getControllerStatusQuery
        })
          .toPromise().then(result =>
        {
          resolve(result.data.getControllerStatus);
        });
      }
    );
  }

  setDirection(direction: TrainDirection) : Promise<ControllerStatus>
  {
    return new Promise<ControllerStatus>(
      resolve =>
      {
        this.apollo.mutate({
          mutation: setDirectionMutation,
          variables: {
            direction: direction == TrainDirection.FORWARD ? "FORWARD" : "REVERSE"
          }
        })
          .toPromise().then(result =>
        {
          resolve(result.data.setDirection);
        });
      }
    );
  }

  setSpeed(speed: Number) : Promise<ControllerStatus>
  {
    return new Promise<ControllerStatus>(
      resolve =>
      {
        this.apollo.mutate({
          mutation: setSpeedMutation,
          variables: {
            speed: speed
          }
        })
          .toPromise().then(result =>
          {
            resolve(result.data.setSpeed);
          });
      }
    );
  }

  setBrake(brakeApplied : boolean) : Promise<ControllerStatus>
  {
    return new Promise<ControllerStatus>(
      resolve =>
      {
        this.apollo.mutate({
          mutation: setBrakeMutation,
          variables: {
            brakeApplied: brakeApplied
          }
        })
          .toPromise().then(result =>
        {
          resolve(result.data.setBrake);
        });
      }
    );
  }
}
