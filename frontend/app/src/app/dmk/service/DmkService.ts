import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {environment} from "../../../environments/environment";

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

@Injectable({
  providedIn: "root"
})
export class DmkService {

  private _states: String[] = [];
  private _strategies: String[] = [];
  private _minV: number = 0;
  private _maxV: number = 10;

  private api = `${environment.apiUrl}/dmk`;

  private onSourceDataGenerated: Subject<DmkSourceData> = new Subject<DmkSourceData>();

  constructor(private httpClient: HttpClient) {
  }

  renderSourceData(dmkSourceData: DmkSourceData) {
    this.onSourceDataGenerated.next(dmkSourceData);
  }

  subscribeOnSourceDataGenerated(callBack: (data: DmkSourceData) => void): void {
    this.onSourceDataGenerated.subscribe(callBack);
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

  updateRange(minV: number, maxV: number) {
    this._minV = minV;
    this._maxV = maxV;
  }
}
