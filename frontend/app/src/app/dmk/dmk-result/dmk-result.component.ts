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

  transformedData: number[][] = [];
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
    console.log(this.data);
    if (valueEntries.length > 0) {
      valueEntries.forEach((value) => {
        let row = Array<number>();
        value.forEach((val, index) => {
          if (index != 0) {
            Object.values(val).forEach(v => row.push(Number(v)));
          }
        });
        this.transformedData.push(row);
        this.statesCount = row.length;
      })

      strategyEntries.forEach((value, key) => {
        let row = Array<number>();
        value.forEach((val, index) => {
          if (index != 0) {
            Object.values(val).forEach(v => row.push(Number(v)));
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
}
