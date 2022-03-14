import { Component, OnInit } from '@angular/core';
import {LvmNodeResult, LvmService} from "../service/lvm.service";

@Component({
  selector: 'app-lvm-result-function',
  templateUrl: './lvm-result-function.component.html',
  styleUrls: ['./lvm-result-function.component.css']
})
export class LvmResultFunctionComponent implements OnInit {

  public mainResult?: LvmNodeResult;

  constructor(private lvmService: LvmService) { }

  ngOnInit(): void {
    this.lvmService.subscribeOnResultGot((data) => {
      this.mainResult = data[0];
    });
  }

}
