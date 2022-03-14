import { Component, OnInit } from '@angular/core';
import {LvmNodeResult, LvmService} from "../service/lvm.service";

@Component({
  selector: 'app-lvm-result-function',
  templateUrl: './lvm-result-function.component.html',
  styleUrls: ['./lvm-result-function.component.css']
})
export class LvmResultFunctionComponent implements OnInit {

  public mainResult?: LvmNodeResult;
  public probability?: number;

  constructor(private lvmService: LvmService) { }

  ngOnInit(): void {
    this.lvmService.subscribeOnResultGot((data) => {
      this.mainResult = data[0];
      this.probability = data[0].resultProbability;
    });
  }

  getRiskResult(): string {
    const loss = this.lvmService.getLoss();
    if (!loss) {
      return 'Неверно ввведены потери';
    }
    // @ts-ignore
    const risk = loss * this.probability;
    return `${loss} * ${ this.probability?.toFixed(3) } = ${ risk.toFixed(3) }`
  }
}
