import { Component, OnInit } from '@angular/core';
import {DmkService} from "../service/DmkService";

@Component({
  selector: 'app-dmk-panel',
  templateUrl: './dmk-panel.component.html',
  styleUrls: ['./dmk-panel.component.css']
})
export class DmkPanelComponent implements OnInit {

  currentState = '';
  currentStrategy = '';

  constructor(
    private dmkService: DmkService
  ) { }

  ngOnInit(): void {
  }

  addState() {
    this.dmkService.addState(this.currentState);
  }

  addStrategy() {
    this.dmkService.addStrategy(this.currentStrategy);
  }

  getStrategies(): String[] {
    return this.dmkService.strategies;
  }

  getStates(): String[] {
    return this.dmkService.states;
  }
}
