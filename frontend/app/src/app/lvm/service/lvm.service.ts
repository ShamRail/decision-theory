import { Injectable } from '@angular/core';

export class LvmTreeNode {
  constructor(
    public id: number,
    public name: string,
    public description: string,
    public probability: number = -1,
    public type: string = 'none') {
  }

  public isLogicOperator(): boolean {
    return this.type !== 'none';
  }

}

export class LvmTreeEdge {
  constructor(
    public from: number,
    public to: number,
    public type: string,
    public probability: number = -1) {
  }
}

@Injectable({
  providedIn: 'root'
})
export class LvmService {

  constructor() { }
}
