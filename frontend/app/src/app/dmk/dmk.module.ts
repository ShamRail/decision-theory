import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DmkRoutingModule } from './dmk-routing.module';
import { DmkComponent } from './dmk.component';


@NgModule({
  declarations: [
    DmkComponent
  ],
  imports: [
    CommonModule,
    DmkRoutingModule
  ]
})
export class DmkModule { }
