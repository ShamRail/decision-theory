import {Component, OnInit} from '@angular/core';
import {MbrService} from "../mbr.service";

@Component({
  selector: 'app-mbr-panel',
  templateUrl: './mbr-panel.component.html',
  styleUrls: ['./mbr-panel.component.css']
})
export class MbrPanelComponent implements OnInit {

  withPrecision = false;
  rowCount = 2;
  colCount = 2;
  matrix: number[][] = [[0, 0], [0, 0]];
  stepsCount: number = 5;
  precision: number = 0.001;
  colStart: number = 1;
  rowStart: number = 1;

  constructor(
    private mbrService: MbrService
  ) {
  }

  ngOnInit(): void {
  }

  inverse() {
    this.withPrecision = !this.withPrecision;
  }

  updateRows($event: any) {
    this.rowCount = $event.target.value;
    console.log(this.rowCount);
    this.updateMatrix();
  }

  updateCols($event: any) {
    this.colCount = $event.target.value;
    this.updateMatrix();
  }

  private updateMatrix(): void {
    const newMatrix: number[][] = [];
    for (let i = 0; i < this.rowCount; i++) {
      const row: number[] = [];
      for (let j = 0; j < this.colCount; j++) {
        row.push(0);
      }
      newMatrix.push(row);
    }
    this.matrix = newMatrix;
  }

  importFile($event: any) {
    const file = $event.target.files[0];
    this.mbrService.import(file).subscribe(result => {
      this.colCount = result.colCount;
      this.rowCount = result.rowCount;
      this.matrix = result.matrix;
      this.stepsCount = result.stepAmount;
      this.precision = result.precision;
      this.withPrecision = result.withPrecision;
      this.rowStart = result.firstRowStrategy;
      this.colStart = result.firstColStrategy;
    });
  }

  exportState() {
    this.mbrService.export({
      colCount: this.colCount,
      rowCount: this.rowCount,
      matrix: this.matrix,
      stepAmount: this.stepsCount,
      precision: this.precision,
      withPrecision: this.withPrecision,
      firstRowStrategy: this.rowStart,
      firstColStrategy: this.colStart
    });
  }

  updateCell(i: number, j: number, $event: any) {
    this.matrix[i][j] = $event.target.value;
  }
}
