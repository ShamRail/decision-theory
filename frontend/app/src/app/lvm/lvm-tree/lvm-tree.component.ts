import {Component, OnInit} from '@angular/core';
import {Data, DataSet, Edge, Node, Options, VisNetworkService} from "ngx-vis";

@Component({
  selector: 'app-lvm-tree',
  templateUrl: './lvm-tree.component.html',
  styleUrls: ['./lvm-tree.component.css']
})

// https://qna.habr.com/q/921453

export class LvmTreeComponent implements OnInit {

  public visNetwork: string = 'networkId1';
  public visNetworkData: Data = {};
  public nodes: DataSet<Node> = new DataSet();
  public edges: DataSet<Edge> = new DataSet();
  public visNetworkOptions: Options = {};

  public option: string = 'event';
  public operatorOption: string = 'OR';
  public eventName: string = '';
  public eventDescription: string = '';
  public eventProbability: number = 0;

  public id: number = 0;

  constructor(private visNetworkService: VisNetworkService) {
  }

  ngOnInit(): void {
    this.visNetworkOptions = {
      autoResize: false,
      physics: {
        enabled: false
      },
      manipulation: {
        addEdge: function(edgeData: any, callback: any) {
          if (edgeData.from === edgeData.to) {
            return
          }
          callback(edgeData);
        }
      }
    };
    this.visNetworkData = {nodes: this.nodes, edges: this.edges};
  }

  initialized() {
    this.visNetworkService.on(this.visNetwork, 'selectNode');
    this.visNetworkService.selectNode.subscribe((_) =>
      this.visNetworkService.addEdgeMode(this.visNetwork)
    );
  }

  addNode() {

    if (this.option === 'event') {
      this.addEventNode(false);
    } else if (this.option == 'initEvent') {
      this.addEventNode(true);
    } else {
      this.addOperatorEvent();
    }

    this.visNetworkService.enableEditMode(this.visNetwork);
  }

  private addEventNode(isInit: boolean) {
    let text = `${this.eventName}\n${this.eventDescription}`;
    text += isInit ? `\nP = ${this.eventProbability}` : '';
    this.drawNode({
      id: (this.id++).toString(), label: text, shape: 'box', color: isInit ? 'red' : 'green'
    });
  }

  private addOperatorEvent() {
    this.drawNode({
      id: (this.id++).toString(), label: this.operatorOption, shape: 'box'
    })
  }

  private drawNode(node: Node) {
    this.nodes.add(node);
    //this.visNetworkService.fit(this.visNetwork);
    this.visNetworkService.redraw(this.visNetwork);
  }

}
