import { Component, OnInit } from '@angular/core';
import {MbrResultDto, MbrService} from "../mbr.service";

@Component({
  selector: 'app-mbr-result',
  templateUrl: './mbr-result.component.html',
  styleUrls: ['./mbr-result.component.css']
})
export class MbrResultComponent implements OnInit {

  mbrResult?: MbrResultDto;

  constructor(private mbrService: MbrService) {

  }

  ngOnInit(): void {
    this.mbrService.subscribeOnResult((result) => {
      console.log(result);
      this.mbrResult = result;
    })
  }

  formatArray(colMixStrategies: number[]): string {
    return colMixStrategies.map(n => n.toFixed(2)).join(", ");
  }
}
