import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DmkRoutingModule } from './dmk-routing.module';
import { DmkPanelComponent } from './dmk-panel/dmk-panel.component';
import { DmkComponent } from './dmk.component';
import {FormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    DmkPanelComponent,
    DmkComponent
  ],
  imports: [
    CommonModule,
    DmkRoutingModule,
    FormsModule
  ],
})
export class DmkModule { }
