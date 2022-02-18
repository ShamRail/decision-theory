import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LvmComponent} from "./lvm.component";

const routes: Routes = [
  { path: '', pathMatch: 'full', component: LvmComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LvmRoutingModule { }
