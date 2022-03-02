import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DmkRoutingModule } from './dmk-routing.module';
import { DmkPanelComponent } from './dmk-panel/dmk-panel.component';
import { DmkComponent } from './dmk.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { VisModule } from "ngx-vis";
import { DmkGraphComponent } from './dmk-graph/dmk-graph.component';
import { DmkTableComponent } from './dmk-table/dmk-table.component';
import { DmkResultComponent } from './dmk-result/dmk-result.component';
import { DmkLogComponent } from './dmk-log/dmk-log.component';


@NgModule({
  declarations: [
    DmkPanelComponent,
    DmkComponent,
    DmkGraphComponent,
    DmkTableComponent,
    DmkResultComponent,
    DmkLogComponent
  ],
  imports: [
    CommonModule,
    DmkRoutingModule,
    FormsModule,
    HttpClientModule,
    VisModule
  ],
})
export class DmkModule { }
