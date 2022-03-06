import {Component, OnInit} from '@angular/core';
import {Data, DataSet, Edge, Node, Options, VisNetworkService} from "ngx-vis";
import {LvmTreeEdge, LvmTreeNode} from "../service/lvm.service";

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

  public nodeId: number = 0;
  public allEdges: LvmTreeEdge[] = [];
  public lvmEdges: LvmTreeEdge[] = [];
  public lvmNodes: LvmTreeNode[] = [];

  constructor(private visNetworkService: VisNetworkService) {
  }

  ngOnInit(): void {
    this.visNetworkOptions = {
      autoResize: false,
      edges: {
        arrows: { to: true }
      },
      physics: {
        enabled: false
      },
      manipulation: {
        addEdge: (edgeData: any, callback: any) => {
          if (edgeData.from === edgeData.to) {
            return
          }
          const sourceNode = this.lvmNodes.find((n) => n.id == edgeData.from);
          const targetNode = this.lvmNodes.find((n) => n.id == edgeData.to);
          if (sourceNode && targetNode) {
            if (!sourceNode.isLogicOperator() && !targetNode.isLogicOperator()) {
              return;
            }
            if (sourceNode.isLogicOperator() && targetNode.isLogicOperator()) {
              return;
            }
            if (this.allEdges.find((n => n.to == edgeData.to && n.from == edgeData.from))) {
              return;
            }
            const logicOperator = sourceNode.isLogicOperator() ? sourceNode : targetNode;
            const eventNode = sourceNode.isLogicOperator() ? targetNode : sourceNode;
            const operatorParent = this.allEdges.find((n) => n.to == logicOperator.id);
            if (targetNode.isLogicOperator() && operatorParent) {
              return;
            }
            if (operatorParent) {
              this.lvmEdges.push(
                new LvmTreeEdge(Number(operatorParent.from), eventNode.id, logicOperator.type, eventNode.probability)
              );
            }
            this.allEdges.push(new LvmTreeEdge(edgeData.from, edgeData.to, sourceNode.type, targetNode.probability));
            callback(edgeData);
          }
        }
      }
    };
    this.visNetworkData = {nodes: this.nodes, edges: this.edges};
  }

  initialized() {
    this.visNetworkService.on(this.visNetwork, 'selectNode');
    this.visNetworkService.on(this.visNetwork, 'deselectNode');
    this.visNetworkService.selectNode.subscribe((_) =>
      this.visNetworkService.addEdgeMode(this.visNetwork)
    );
    this.visNetworkService.deselectNode.subscribe((_) =>
      this.visNetworkService.disableEditMode(this.visNetwork)
    );
  }

  addNode() {

    if (this.option === 'event') {
      this.lvmNodes.push(new LvmTreeNode(
        this.nodeId + 1, this.eventName, this.eventDescription)
      );
      this.addEventNode(false);
    } else if (this.option == 'initEvent') {
      this.lvmNodes.push(new LvmTreeNode(
        this.nodeId + 1, this.eventName, this.eventDescription, this.eventProbability)
      );
      this.addEventNode(true);
    } else {
      this.lvmNodes.push(new LvmTreeNode(
        this.nodeId + 1, this.eventName, this.eventDescription, this.eventProbability, this.operatorOption)
      );
      this.addOperatorEvent();
    }

    this.visNetworkService.enableEditMode(this.visNetwork);
  }

  private addEventNode(isInit: boolean) {
    let text = `${this.eventName}\n${this.eventDescription}`;
    text += isInit ? `\nP = ${this.eventProbability}` : '';
    this.drawNode({
      id: (++this.nodeId).toString(), label: text, shape: 'box', color: isInit ? 'red' : 'green'
    });
  }

  private addOperatorEvent() {
    this.drawNode({
      id: (++this.nodeId).toString(), label: this.operatorOption, shape: 'box'
    })
  }

  private drawNode(node: Node) {
    this.nodes.add(node);
    this.visNetworkService.redraw(this.visNetwork);
  }

}
