import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {DmkResult, DmkService, DmkSourceData} from "../service/DmkService";

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
  stepAmount: any;
  file: File | null = null;

  @Output() onDataGenerated: EventEmitter<DmkSourceData> = new EventEmitter<DmkSourceData>();
  @Output() onCalculated: EventEmitter<DmkResult> = new EventEmitter<DmkResult>();

  constructor(
    private dmkService: DmkService
  ) { }

  ngOnInit(): void {
    this.minV = this.dmkService.minV;
    this.maxV = this.dmkService.maxV;
    this.stepAmount = this.dmkService.stepAmount;
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

  updateMin($event: any) {
    this.minV = $event.target.value;
    this.dmkService.updateRange(this.minV, this.maxV);
  }

  updateMax($event: any) {
    this.maxV = $event.target.value;
    this.dmkService.updateRange(this.minV, this.maxV);
  }

  generateTable() {
    this.dmkService.generateData().subscribe(data => {
      this.onDataGenerated.emit(data);
    });
  }

  calculate() {
    this.dmkService.calculateMarkProcess(this.dmkService.lastGeneratedData).subscribe((data) => {
      this.onCalculated.emit(data);
    })
  }

  updateSteps() {
    this.dmkService.updateSteps(this.stepAmount);
  }

  export() {
    const json = JSON.stringify(this.dmkService.getCurrentState(), null, '\t');
    const blob = new Blob([json], {type: 'application/json'});
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = 'export.json';
    link.click();
  }

  uploadFile($event: any) {
    this.file = $event.target.files[0];
    if (this.file !== null) {
      this.dmkService.uploadFile(this.file);
    }
  }

  getMinV() {
    return this.dmkService.minV;
  }

  getMaxV() {
    return this.dmkService.maxV;
  }

  getStepAmount() {
    return this.dmkService.stepAmount;
  }
}
