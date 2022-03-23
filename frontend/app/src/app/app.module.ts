import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { StartPageComponent } from './start-page/start-page.component';
import {DmkModule} from "./dmk/dmk.module";
import {LvmModule} from "./lvm/lvm.module";
import {FileService} from "./shared/FileService";
import {MbrModule} from "./mbr/mbr.module";

@NgModule({
  declarations: [
    AppComponent,
    StartPageComponent
  ],
  imports: [
    BrowserModule,
    DmkModule,
    LvmModule,
    MbrModule,
    AppRoutingModule
  ],
  providers: [FileService],
  bootstrap: [AppComponent]
})
export class AppModule { }
