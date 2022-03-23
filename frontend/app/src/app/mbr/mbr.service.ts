import { Injectable } from '@angular/core';
import {FileService} from "../shared/FileService";
import {Observable} from "rxjs";

export class MbrState {
  constructor(
    public rowCount: number,
    public colCount: number,
    public firstRowStrategy: number,
    public firstColStrategy: number,
    public precision: number,
    public stepAmount: number,
    public withPrecision: boolean,
    public matrix: number[][]) {
  }
}

@Injectable({
  providedIn: 'root'
})
export class MbrService {

  constructor(private fileService: FileService) { }

  public export(state: MbrState) {
    this.fileService.saveToFile(state);
  }

  public import(file: File): Observable<MbrState> {
    return this.fileService.restoreObject<MbrState>(file);
  }

}
