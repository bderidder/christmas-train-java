import {Apollo} from "apollo-angular";
import {Injectable} from "@angular/core";
import {getControllerStatusQuery, setBrakeMutation, setDirectionMutation, setSpeedMutation} from "./train.service.gql";

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
        this.apollo
          .query({
            query: getControllerStatusQuery
          })
          .toPromise().then(result =>
          {
            // @ts-ignore
            resolve(result.data.getControllerStatus);
          });
      }
    );
  }

  setDirection(direction: TrainDirection) : Promise<ControllerStatus>
  {
    return this.genericMutation(
      setDirectionMutation,
      {
        direction: direction == TrainDirection.FORWARD ? "FORWARD" : "REVERSE"
      },
      result => result.data.setDirection
    );
  }

  setSpeed(speed: Number) : Promise<ControllerStatus>
  {
    return this.genericMutation(
      setSpeedMutation,
      {
        speed: speed
      },
      result => result.data.setSpeed
    );
  }

  setBrake(brakeApplied : boolean) : Promise<ControllerStatus>
  {
    return this.genericMutation(
      setBrakeMutation,
      {
        brakeApplied: brakeApplied
      },
      result => result.data.setBrake
    );
  }

  genericMutation(mutation, variables, resolver) : Promise<ControllerStatus>
  {
    return new Promise<ControllerStatus>(
      resolve =>
      {
        this.apollo
          .mutate({
            mutation: mutation,
            variables: variables
          })
          .toPromise().then(result =>
          {
            resolve(resolver(result));
          });
      }
    );
  }
}
