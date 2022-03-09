import { Component, OnInit } from '@angular/core';
import {DmkResult, DmkService} from "../service/DmkService";

@Component({
  selector: 'app-dmk-result',
  templateUrl: './dmk-result.component.html',
  styleUrls: ['./dmk-result.component.css']
})
export class DmkResultComponent implements OnInit {

  private data: DmkResult = new DmkResult(
    new Map<number, Map<number, number>>(),
    new Map<number, Map<number, number>>()
  );

  transformedData: string[][] = [];
  statesCount = 0;

  constructor(
    private dmkService: DmkService) { }

  ngOnInit(): void {
    this.dmkService.subscribeOnResultGot((data) => {
      this.data = data;
      this.transformData();
    });
  }

  private transformData() {
    this.transformedData = [];
    this.statesCount = 0;

    let valueEntries = Object.entries(this.data.valueResultPerStep);
    let strategyEntries = Object.entries(this.data.strategyResultStep);
    const strategies = this.dmkService.strategies;
    if (valueEntries.length > 0) {
      valueEntries.forEach((value) => {
        let row = Array<string>();
        value.forEach((val, index) => {
          if (index != 0) {
            Object.values(val).forEach(v => row.push(Number(v).toFixed(2)));
          }
        });
        this.transformedData.push(row);
        this.statesCount = row.length;
      })

      strategyEntries.forEach((value, key) => {
        let row = Array<string>();
        value.forEach((val, index) => {
          if (index != 0) {
            Object.values(val).forEach(v => {
              const vn = Number(v);
              row.push(
                vn === -1 ? "-" : strategies[vn].toString()
              )
            });
          }
        });
        row.forEach(v => this.transformedData[key].push(v));
      })

    }
  }

  getIndexes(stateCount: number): number[] {
    let array = [];
    for (let i = 0; i < stateCount; i++) {
      array.push(i);
    }
    return array;
  }

  getHeading(i: number): String {
    return this.dmkService.states[i];
  }

}
