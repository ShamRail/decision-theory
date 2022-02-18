import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DmkComponent} from "./dmk.component";

const routes: Routes = [
  { path: '', pathMatch: 'full', component: DmkComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DmkRoutingModule { }
