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
}
