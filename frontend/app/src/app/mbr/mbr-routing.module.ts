import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MbrComponent} from "./mbr/mbr.component";

const routes: Routes = [
  { path: '', pathMatch: 'full', component: MbrComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MbrRoutingModule { }
