import { Component, OnInit } from '@angular/core';
import {DmkService, DmkSourceData} from "../service/DmkService";

@Component({
  selector: 'app-dmk-table',
  templateUrl: './dmk-table.component.html',
  styleUrls: ['./dmk-table.component.css']
})
export class DmkTableComponent implements OnInit {

  private _data: DmkSourceData = new DmkSourceData([], []);

  constructor(
    private dmkService: DmkService
  ) { }

  ngOnInit(): void {
    this.dmkService.subscribeOnSourceDataGenerated((data) => {
      this._data = data;
    });
  }

  get data(): DmkSourceData {
    return this._data;
  }

  updateProbabilityCell(strategy: number, row: number, col: number, event: any) {
    this.dmkService.updateSourceDataValue(
      strategy, row, col, Number(event.target.value),
      (data) => data.probabilities
    )
  }

  updateValueCell(strategy: number, row: number, col: number, event: any) {
    this.dmkService.updateSourceDataValue(
      strategy, row, col, Number(event.target.value),
      (data) => data.values
    )
  }

  getStateHeading(k: number): String {
    return this.dmkService.states[k];
  }

  getStrategyHeading(k: number): String {
    return this.dmkService.strategies[k];
  }
}
