import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { StartPageComponent } from './start-page/start-page.component';
import {DmkModule} from "./dmk/dmk.module";

@NgModule({
  declarations: [
    AppComponent,
    StartPageComponent
  ],
  imports: [
    BrowserModule,
    DmkModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
