import { Component, OnInit } from '@angular/core';
import {DmkResult, DmkService, DmkSourceData} from "./service/DmkService";

@Component({
  selector: 'app-dmk',
  templateUrl: './dmk.component.html',
  styleUrls: ['./dmk.component.css']
})
export class DmkComponent implements OnInit {

  constructor(
    private dmkService: DmkService
  ) { }

  ngOnInit(): void {
  }

  updateTableAndGraphView(dmkSourceData: DmkSourceData) {
    this.dmkService.renderSourceData(dmkSourceData);
  }

  handleResult($event: DmkResult) {
    this.dmkService.renderResult($event);
  }

}
