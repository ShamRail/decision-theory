import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LvmRoutingModule } from './lvm-routing.module';
import {LvmComponent} from "./lvm.component";
import { LvmTreeComponent } from './lvm-tree/lvm-tree.component';
import {VisModule} from "ngx-vis";
import {FormsModule} from "@angular/forms";
import { LvmResultFunctionComponent } from './lvm-result-function/lvm-result-function.component';


@NgModule({
  declarations: [
    LvmComponent,
    LvmTreeComponent,
    LvmResultFunctionComponent,
  ],
  imports: [
    CommonModule,
    LvmRoutingModule,
    VisModule,
    FormsModule
  ]
})
export class LvmModule { }
