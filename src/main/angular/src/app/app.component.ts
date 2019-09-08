import {Component, OnInit} from '@angular/core';
import {MatSliderChange} from "@angular/material/slider";
import {MatSlideToggleChange} from "@angular/material/slide-toggle";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError} from "rxjs/operators";
import {TrainControllerRequest} from "../models/train.controller.request";
import {throwError} from "rxjs";
import {TrainControllerStatus} from "../models/train.controller.status";
import {BackendStatus} from "../models/backend.status";

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

  constructor(private http: HttpClient)
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

    this.updateTrainController();
  }

  onForwardClicked()
  {
    this.forward = true;

    this.updateTrainController();
  }

  onSliderChange(event: MatSliderChange)
  {
    this.updateTrainController();
  }

  onSliderInput(event: MatSliderChange)
  {
    this.sliderSpeed = event.value;
  }

  onBrakeChange(event: MatSlideToggleChange)
  {
    this.brake = event.checked;

    this.updateTrainController();
  }

  updateTrainController()
  {
    this.updateBackendTrainController(
      this.speed,
      this.forward,
      this.brake);
  }

  initTrainController()
  {
    this.backendStatus.error = false;
    this.backendStatus.message = "sending changes to train";

    this.http.get<TrainControllerStatus>('/api/train/control')
      .pipe(
        catchError(error => this.handleCallBackendError(error))
      )
      .subscribe(status => this.updateLocalStateFromServerStatus(status));
  }

  updateBackendTrainController(speed, direction, brake)
  {
    let trainControllerRequest = new TrainControllerRequest();

    trainControllerRequest.speed = speed;
    trainControllerRequest.direction = direction;
    trainControllerRequest.brake = brake;

    this.http.post<TrainControllerStatus>('/api/train/control', trainControllerRequest)
      .pipe(
        catchError(error => this.handleCallBackendError(error))
      )
      .subscribe(status => this.updateLocalStateFromServerStatus(status));
  }

  updateLocalStateFromServerStatus(trainControllerStatus: TrainControllerStatus)
  {
    this.backendStatus.error = false;
    this.backendStatus.message = "";

    this.speed = trainControllerStatus.speed;
    this.sliderSpeed = this.speed;
    this.forward = trainControllerStatus.direction;
    this.brake = trainControllerStatus.brake;
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
