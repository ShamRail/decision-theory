import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {environment} from "../../../environments/environment";
import {map} from "rxjs/operators";

export class DmkSourceData {

  constructor(
    private _probabilities: number[][][],
    private _values: number[][][]
  ) { }

  get probabilities(): number[][][] {
    return this._probabilities;
  }

  get values(): number[][][] {
    return this._values;
  }

}

export class DmkResult {

  constructor(
    private _valueResultPerStep: Map<number, Map<number, number>> = new Map<number, Map<number, number>>(),
    private _strategyResultStep: Map<number, Map<number, number>> = new Map<number, Map<number, number>>()) {
  }

  get valueResultPerStep(): Map<number, Map<number, number>> {
    return this._valueResultPerStep;
  }

  get strategyResultStep(): Map<number, Map<number, number>> {
    return this._strategyResultStep;
  }

}

@Injectable({
  providedIn: "root"
})
export class DmkService {

  private _states: String[] = [];
  private _strategies: String[] = [];
  private _minV: number = 0;
  private _maxV: number = 10;
  private _stepAmount: number = 2;
  private _lastGeneratedData: DmkSourceData = new DmkSourceData([], []);

  private api = `${environment.apiUrl}/dmk`;

  private onSourceDataGenerated: Subject<DmkSourceData> = new Subject<DmkSourceData>();
  private onResultGot: Subject<DmkResult> = new Subject<DmkResult>();

  constructor(private httpClient: HttpClient) {
  }

  renderSourceData(dmkSourceData: DmkSourceData) {
    this._lastGeneratedData = dmkSourceData;
    this.onSourceDataGenerated.next(dmkSourceData);
  }

  renderResult(dmkResult: DmkResult) {
    this.onResultGot.next(dmkResult);
  }

  subscribeOnSourceDataGenerated(callBack: (data: DmkSourceData) => void): void {
    this.onSourceDataGenerated.subscribe(callBack);
  }

  subscribeOnResultGot(callBack: (data: DmkResult) => void): void {
    this.onResultGot.subscribe(callBack);
  }

  addState(state: String) {
    this._states.push(state);
  }

  addStrategy(strategy: String) {
    this._strategies.push(strategy);
  }

  get states(): String[] {
    return this._states;
  }

  get strategies(): String[] {
    return this._strategies;
  }

  get lastGeneratedData(): DmkSourceData {
    return this._lastGeneratedData;
  }

  generateData(): Observable<DmkSourceData> {
    return this.httpClient.get<DmkSourceData>(`${this.api}/generate`,
      {
        params: new HttpParams()
          .append('stateCount', this.states.length)
          .append('strategyCount', this.strategies.length)
          .append('minV', this._minV)
          .append('maxV', this._maxV)
      }
    )
  }

  calculateMarkProcess(data: DmkSourceData): Observable<DmkResult> {
    return this.httpClient.post<any>(`${this.api}/calculate`, data, {
      params: new HttpParams()
        .append('stepAmount', this._stepAmount)
    });
  }

  updateRange(minV: number, maxV: number) {
    this._minV = minV;
    this._maxV = maxV;
  }

  updateSourceDataValue(
    k: number, i: number, j: number, value: number,
    supplier: (data: DmkSourceData) => number[][][]) {
    supplier(this._lastGeneratedData)[k][i][j] = value;
  }

  updateSteps(stepAmount: number) {
    this._stepAmount = stepAmount;
  }

}
