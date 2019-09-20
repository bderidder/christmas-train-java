import {Component, OnInit} from '@angular/core';
import {MatSliderChange} from "@angular/material/slider";
import {MatSlideToggleChange} from "@angular/material/slide-toggle";
import {HttpErrorResponse} from "@angular/common/http";
import {throwError} from "rxjs";
import {BackendStatus} from "../models/backend.status";
import {ControllerStatus, TrainDirection, TrainService} from "../services/train.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit
{
  forward = true;
  brake = true;
  sliderSpeed = 0;
  speed = 0;

  backendStatus: BackendStatus;

  constructor(private trainService: TrainService)
  {
    this.backendStatus = new BackendStatus();

    this.backendStatus.error = false;
    this.backendStatus.message = "status is not known";
  }

  ngOnInit()
  {
    this.initTrainController();
  }

  onReverseClicked()
  {
    this.forward = false;

    this.handleControllerStatusPromise(this.trainService.setDirection(TrainDirection.REVERSE));
  }

  onForwardClicked()
  {
    this.forward = true;

    this.handleControllerStatusPromise(this.trainService.setDirection(TrainDirection.FORWARD));
  }

  onSliderChange(event: MatSliderChange)
  {
    this.handleControllerStatusPromise(this.trainService.setSpeed(this.sliderSpeed));
  }

  onSliderInput(event: MatSliderChange)
  {
    this.sliderSpeed = event.value;
  }

  onBrakeChange(event: MatSlideToggleChange)
  {
    this.brake = event.checked;

    this.handleControllerStatusPromise(this.trainService.setBrake(this.brake));
  }

  initTrainController()
  {
    this.backendStatus.error = false;
    this.backendStatus.message = "sending changes to train";

    this.handleControllerStatusPromise(this.trainService.getControllerStatus());
  }

  handleControllerStatusPromise(promise: Promise<ControllerStatus>)
  {
    promise.then(
      controllerStatus => this.updateLocalStateFromServerStatus(controllerStatus),
      error => this.handleCallBackendError(error)
    );
  }

  updateLocalStateFromServerStatus(controllerStatus: ControllerStatus)
  {
    this.backendStatus.error = false;
    this.backendStatus.message = "";

    this.speed = controllerStatus.speed;
    this.sliderSpeed = this.speed;
    this.forward = controllerStatus.direction == TrainDirection.FORWARD;
    this.brake = controllerStatus.brake;
  }

  handleCallBackendError(error: HttpErrorResponse)
  {
    this.backendStatus.error = true;
    this.backendStatus.message = "could not talk to backend";

    if (error.error instanceof ErrorEvent)
    {
      console.error('An error occurred:', error.error.message);
    }
    else
    {
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }

    return throwError(
      'Something bad happened; please try again later.');
  }
}
