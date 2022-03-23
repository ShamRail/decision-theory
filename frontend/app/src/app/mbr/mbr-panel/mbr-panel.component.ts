import {Component, OnInit} from '@angular/core';

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

  constructor() {
  }

  ngOnInit(): void {
  }

  inverse() {
    this.withPrecision = !this.withPrecision;
  }

  updateRows($event: any) {
    this.rowCount = $event.target.value;
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

}
