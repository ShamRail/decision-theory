import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LvmRoutingModule } from './lvm-routing.module';
import {LvmComponent} from "./lvm.component";


@NgModule({
  declarations: [
    LvmComponent,
  ],
  imports: [
    CommonModule,
    LvmRoutingModule
  ]
})
export class LvmModule { }
