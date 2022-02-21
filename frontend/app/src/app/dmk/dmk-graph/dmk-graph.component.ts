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

  // public addNode(): void {
  //   const lastId = this.nodes.length;
  //   const newId = this.nodes.length + 1;
  //   this.nodes.add({id: newId, label: 'New Node'});
  //   this.edges.add({from: lastId, to: newId});
  //   this.visNetworkService.fit(this.visNetwork);
  // }

  public ngOnInit(): void {
    // this.nodes = new DataSet<Node>([
    //   {id: '1', label: 'Node 1'},
    //   {id: '2', label: 'Node 2'},
    //   {id: '3', label: 'Node 3'},
    //   {id: '4', label: 'Node 4'},
    //   {id: '5', label: 'Node 5', title: 'Title of Node 5'}
    // ]);
    // this.edges = new DataSet<Edge>([
    //   {from: '1', to: '2', label: '123'},
    //   {from: '1', to: '3'},
    //   {from: '2', to: '4'},
    //   {from: '2', to: '5'}
    // ]);
    this.visNetworkData = {nodes: this.nodes, edges: this.edges};
    this.visNetworkOptions = {
      autoResize: true, edges: {
        arrows: {to: true},
      }
    };

    this.dmkService.subscribeOnSourceDataGenerated((data) => {
      this.nodes.clear();
      this.edges.clear();
      const matrix = data.probabilities[0];
      for (let i = 0; i < matrix.length; i++) {
        this.nodes.add({id: i.toString(), label: i.toString()})
      }
      for (let i = 0; i < matrix.length; i++) {
        for (let j = 0; j < matrix.length; j++) {
          const val = matrix[i][j];
          if (val > 0.01) {
            this.edges.add(
              {from: i.toString(), to: j.toString(), label: val.toPrecision(2), arrows: {
                to: true
              }},
            )
          }
        }
      }
      // this.visNetworkData = {nodes: newNodes, edges: newEdges};
      this.visNetworkService.redraw(this.visNetwork);
      // this.visNetworkService.fit(this.visNetwork);
      // this.visNetworkService.fit(this.visNetwork);
      // this.visNetworkService.fit(this.visNetwork);
      // this.visNetworkService.fit(this.visNetwork);
      // this.visNetworkService.fit(this.visNetwork);
      // this.visNetworkService.fit(this.visNetwork);
      // this.visNetworkService.fit(this.visNetwork);
      // this.visNetworkService.fit(this.visNetwork);
      // this.visNetworkService.fit(this.visNetwork);
    });

  }

}
