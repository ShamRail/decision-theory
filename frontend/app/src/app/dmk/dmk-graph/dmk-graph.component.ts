import {Component, OnInit} from '@angular/core';

import {Data, DataSet, Edge, Node, Options, VisNetworkService} from 'ngx-vis';
import {DmkService} from "../service/DmkService";

@Component({
  selector: 'app-dmk-graph',
  styleUrls: ['./dmk-graph.component.css'],
  styles: [
    `
      .network-canvas {
        width: 100%;
        height: 400px;
        border: 1px solid lightgray;
      }
    `
  ],
  template: `
    <div class="container">
      <div *ngIf="visNetworkData">
        <div
          class="network-canvas"
          [visNetwork]="visNetwork"
          [visNetworkData]="visNetworkData"
          [visNetworkOptions]="visNetworkOptions"
        ></div>
      </div>
    </div>
  `

})
export class DmkGraphComponent implements OnInit {

  public visNetwork: string = 'networkId1';
  public visNetworkData: Data = {};
  public nodes: DataSet<Node> = new DataSet();
  public edges: DataSet<Edge> = new DataSet();
  public visNetworkOptions: Options = {};

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

    this.dmkService.subscribeOnSourceDataGenerated((data) => {
      this.nodes.clear();
      this.edges.clear();
      const matrix = data.probabilities[0];
      const states = this.dmkService.states;
      for (let i = 0; i < matrix.length; i++) {
        this.nodes.add({id: states[i].toString(), label: states[i].toString()})
      }
      for (let i = 0; i < matrix.length; i++) {
        for (let j = 0; j < matrix.length; j++) {
          const val = matrix[i][j];
          if (val > 0.01) {
            this.edges.add(
              {from: states[i].toString(), to: states[j].toString(), label: val.toPrecision(2), color: 'red', arrows: {
                to: true
              }},
            )
          }
        }
      }
      this.visNetworkService.redraw(this.visNetwork);
    });

  }

}
