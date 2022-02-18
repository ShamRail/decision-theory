import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DmkComponent} from "./dmk/dmk.component";
import {LvmComponent} from "./lvm/lvm.component";
import {StartPageComponent} from "./start-page/start-page.component";

const routes: Routes = [
  { path: 'dmk', component: DmkComponent },
  { path: 'lvm', component: LvmComponent },
  { path: '', pathMatch: 'full', component: StartPageComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
