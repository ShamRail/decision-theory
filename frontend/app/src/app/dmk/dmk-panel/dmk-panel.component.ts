import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {DmkService, DmkSourceData} from "../service/DmkService";

@Component({
  selector: 'app-dmk-panel',
  templateUrl: './dmk-panel.component.html',
  styleUrls: ['./dmk-panel.component.css']
})
export class DmkPanelComponent implements OnInit {

  currentState = '';
  currentStrategy = '';
  minV : any;
  maxV : any;

  @Output() onDataGenerated: EventEmitter<DmkSourceData> = new EventEmitter<DmkSourceData>();

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

  updateRange() {
    this.dmkService.updateRange(this.minV, this.maxV);
  }

  generateTable() {
    this.dmkService.generateData().subscribe(data => {
      this.onDataGenerated.emit(data);
    });
  }

}