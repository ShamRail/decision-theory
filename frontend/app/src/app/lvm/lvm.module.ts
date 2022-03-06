import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LvmRoutingModule } from './lvm-routing.module';
import {LvmComponent} from "./lvm.component";
import { LvmTreeComponent } from './lvm-tree/lvm-tree.component';
import {VisModule} from "ngx-vis";
import {FormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    LvmComponent,
    LvmTreeComponent,
  ],
  imports: [
    CommonModule,
    LvmRoutingModule,
    VisModule,
    FormsModule
  ]
})
export class LvmModule { }
