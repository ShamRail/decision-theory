import { Injectable } from '@angular/core';
import {FileService} from "../shared/FileService";
import {Observable, Subject} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";

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

export class MbrDTO {
  constructor(
    public matrix: number[][],
    public iterationsAmount: number,
    public precision: number,
    public firstRow: number,
    public firstCol: number
  ) {
  }
}

export class MbrResultRow {
  constructor(
    public iteration: number,
    public colResult: number[],
    public rowResult: number[],
    public minIndex: number,
    public min: number,
    public maxIndex: number,
    public max: number) {
  }
}

export class MbrResultDto {
  constructor(
    public steps: MbrResultRow[],
    public totalIterations: number,
    public minMax: number,
    public maxMinIndex: number,
    public minMaxIndex: number,
    public maxMin: number,
    public gamePrice: number,
    public log: string,
    public rowMixStrategies: number[] = [],
    public colMixStrategies: number[] = []
  ) {
  }
}

@Injectable({
  providedIn: 'root'
})
export class MbrService {

  private apiHost = environment.apiUrl;

  private onResultGot: Subject<MbrResultDto> = new Subject<MbrResultDto>();

  constructor(
    private fileService: FileService,
    private httpClient: HttpClient) { }

  public export(state: MbrState) {
    this.fileService.saveToFile(state);
  }

  public import(file: File): Observable<MbrState> {
    return this.fileService.restoreObject<MbrState>(file);
  }

  public invokeCalculation(withPrecision: boolean, dto: MbrDTO): void {
    this.httpClient.post<MbrResultDto>(
      `${this.apiHost}/mbr/solve`, dto, {
        params: new HttpParams().append("byPrecision", withPrecision)
      }
    ).subscribe(result => {
      this.onResultGot.next(result);
    })
  }

  subscribeOnResult(callback: (dto: MbrResultDto) => void) {
   this.onResultGot.subscribe(callback);
  }

}
