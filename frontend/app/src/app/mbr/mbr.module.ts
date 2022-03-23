import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MbrRoutingModule } from './mbr-routing.module';
import { MbrComponent } from './mbr/mbr.component';
import { MbrPanelComponent } from './mbr-panel/mbr-panel.component';
import { MbrResultComponent } from './mbr-result/mbr-result.component';
import {FormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    MbrComponent,
    MbrPanelComponent,
    MbrResultComponent
  ],
    imports: [
        CommonModule,
        MbrRoutingModule,
        FormsModule
    ]
})
export class MbrModule { }
