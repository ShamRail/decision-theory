import { Component, OnInit } from '@angular/core';
import {DmkService} from "../service/DmkService";

@Component({
  selector: 'app-dmk-log',
  templateUrl: './dmk-log.component.html',
  styleUrls: ['./dmk-log.component.css']
})
export class DmkLogComponent implements OnInit {

  log: string[] = [];

  constructor(private dmkService: DmkService) { }

  ngOnInit(): void {
    this.dmkService.subscribeOnResultGot((result) => {
      this.log = result.log;
    });
  }

}
