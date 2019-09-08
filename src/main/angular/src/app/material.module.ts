import {NgModule} from "@angular/core";

import {MatButtonModule} from '@angular/material/button';
import {MatSliderModule} from "@angular/material/slider";
import {MatCardModule} from "@angular/material/card";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {MatIconModule} from "@angular/material/icon";
import {FormsModule} from "@angular/forms";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {HttpClientModule} from "@angular/common/http";

const ANGULAR_MODULES = [
  MatButtonModule,
  MatIconModule,
  MatButtonToggleModule,
  MatSliderModule,
  MatCardModule,
  MatSlideToggleModule,
  FormsModule,
  HttpClientModule
];

@NgModule({
  imports: ANGULAR_MODULES,
  exports: ANGULAR_MODULES,
})
export class MaterialModule { }
