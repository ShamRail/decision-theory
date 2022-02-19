import {Injectable} from "@angular/core";

@Injectable({
  providedIn: "root"
})
export class DmkService {

  private _states: String[] = [];
  private _strategies: String[] = [];

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

}
