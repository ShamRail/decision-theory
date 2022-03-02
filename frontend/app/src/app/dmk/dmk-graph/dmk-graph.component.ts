import {Component, OnInit} from '@angular/core';

import {Data, DataSet, Edge, Node, Options, VisNetworkService} from 'ngx-vis';
import {DmkResult, DmkService} from "../service/DmkService";

export class StrategyColor {
  constructor(
    public name: String,
    public color: String
  ) { }
}

@Component({
  selector: 'app-dmk-graph',
  styleUrls: ['./dmk-graph.component.css'],
  templateUrl: './dmk-graph.component.html'
})
export class DmkGraphComponent implements OnInit {

  public colors: string[] = [
    'black', 'red', 'green', 'blue', 'purple'
  ];

  public visNetwork: string = 'networkId1';
  public visNetworkData: Data = {};
  public nodes: DataSet<Node> = new DataSet();
  public edges: DataSet<Edge> = new DataSet();
  public visNetworkOptions: Options = {};


  resultStrategies: StrategyColor[] = [];

  public constructor(
    private dmkService: DmkService,
    private visNetworkService: VisNetworkService) {
  }

  public ngOnInit(): void {
    this.visNetworkData = {nodes: this.nodes, edges: this.edges};
    this.visNetworkOptions = {
      autoResize: false,
      edges: {
        arrows: {to: true},
      },
      physics: {
        solver: 'forceAtlas2Based'
      }
    };

    this.dmkService.subscribeOnResultGot((data) => {
      this.nodes.clear();
      this.edges.clear();
      this.drawGraph(this.prepareStrategies(data));
      this.visNetworkService.redraw(this.visNetwork);
    });

  }

  private prepareStrategies(data: DmkResult): number[][] {
    let result: number[][] = [];
    let strategyEntries = Object.entries(data.strategyResultStep);
    if (strategyEntries.length > 0) {
      strategyEntries.forEach((value) => {
        let row = Array<number>();
        value.forEach((val, index) => {
          if (index != 0) {
            Object.values(val).forEach(v => row.push(Number(v)));
          }
        });
        result.push(row);
      })

    }
    return result;
  }

  private drawGraph(strategies: number[][]) {
    this.resultStrategies = [];
    const states = this.dmkService.states;
    const strategyNames = this.dmkService.strategies;
    strategies.forEach((_, stage) => {
      states.forEach(state => this.nodes.add( { id: `${state}${stage + 1}`, label: `${state}(${stage + 1})` }));
    })
    strategies = strategies.slice(1, strategies.length); // remove zero stage
    const assignedColors = this.assignColors(strategies);
    for (let i = 0; i < strategies.length; i++) {
      strategies[i].forEach((val, stateI) => {
        for (let stateJ of states) {
          let color = assignedColors.get(val);
          if (color) {
            this.edges.add({
              from: `${states[stateI]}${i + 1}`, to: `${stateJ}${i + 2}`,
              color: color.toString(), arrows: { to: true }
            });
            if (!this.resultStrategies.find(s => s.name === strategyNames[val])) {
              this.resultStrategies.push(new StrategyColor(strategyNames[val], color.toString()));
            }
          }
        }
      });
    }

  }

  private assignColors(strategies: number[][]): Map<number, String> {
    const strategyPerColor = new Map<number, String>();
    let colorIndex = 0;
    for (let strategy of strategies) {
      for (let val of strategy) {
        if (!strategyPerColor.has(val)) {
          strategyPerColor.set(val, this.colors[colorIndex++]);
        }
      }
    }
    return strategyPerColor;
  }

}
