import {Component, OnInit} from '@angular/core';
import {Data, DataSet, Edge, Node, Options, VisNetworkService} from "ngx-vis";
import {LvmService, LvmTreeEdge, LvmTreeNode} from "../service/lvm.service";

@Component({
  selector: 'app-lvm-tree',
  templateUrl: './lvm-tree.component.html',
  styleUrls: ['./lvm-tree.component.css']
})
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

  public allEdges: LvmTreeEdge[] = [];
  public lvmEdges: LvmTreeEdge[] = [];
  public lvmNodes: LvmTreeNode[] = [];

  constructor(
    private visNetworkService: VisNetworkService,
    private lvmService: LvmService) {
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
          const sourceNode = this.lvmNodes.find((n) => n.name == edgeData.from);
          const targetNode = this.lvmNodes.find((n) => n.name == edgeData.to);
          if (sourceNode && targetNode) {
            if (!sourceNode.isLogicOperator() && !targetNode.isLogicOperator()) {
              return;
            }
            if (sourceNode.isLogicOperator() && targetNode.isLogicOperator()) {
              return;
            }
            if (this.allEdges.find((n => n.to == targetNode.name && n.from == sourceNode.name))) {
              return;
            }
            const logicOperator = sourceNode.isLogicOperator() ? sourceNode : targetNode;
            const eventNode = sourceNode.isLogicOperator() ? targetNode : sourceNode;
            const operatorParent = this.allEdges.find((n) => n.to == logicOperator.name);
            if (targetNode.isLogicOperator() && operatorParent) {
              return;
            }
            if (operatorParent) {
              this.lvmEdges.push(
                new LvmTreeEdge(operatorParent.from, eventNode.name, logicOperator.type, eventNode.probability)
              );
            }
            this.allEdges.push(new LvmTreeEdge(sourceNode.name, targetNode.name, logicOperator.type, targetNode.probability));
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

    if (this.option === 'event' || this.option === 'initEvent') {
      this.addEventNode(new LvmTreeNode(
        this.eventName, this.eventDescription,
        this.option === 'initEvent' ? this.eventProbability : -1
      ));
    } else {
      this.addOperatorNode(new LvmTreeNode(
        `${this.operatorOption}${Math.random() * 1000000}`,
        this.operatorOption, -1, this.operatorOption
      ));
    }

    this.visNetworkService.enableEditMode(this.visNetwork);
  }

  private addEventNode(node: LvmTreeNode, x?: number, y?: number) {
    const isInit = node.probability != -1;
    let text = `${node.name}\n${node.description}`;
    text += isInit ? `\nP = ${node.probability}` : '';
    this.drawNode({
      id: node.name, label: text, shape: 'box', color: isInit ? 'red' : 'green',
      x: x, y: y
    }, node);
  }

  private addOperatorNode(node: LvmTreeNode, x?: number, y?: number) {
    this.drawNode({
      id: node.name, label: node.description, shape: 'box',
      x: x, y: y
    }, node)
  }

  private drawNode(node: Node, lvmNode: LvmTreeNode) {
    try {
      this.nodes.add(node);
      this.visNetworkService.redraw(this.visNetwork);
      this.lvmNodes.push(lvmNode);
    } catch (e: any) {
      console.log(e);
    }
  }

  readFile($event: any) {
    if ($event.target.files.length > 0) {
      this.lvmService.restoreFromFile($event.target.files[0]).subscribe((result) => {

        this.nodes.clear();
        this.edges.clear();
        this.lvmNodes = [];
        this.lvmEdges = [];
        this.allEdges = [];

        result.nodes.forEach((node) => {
          const type = node.type;
          if ("none" == type) {
            this.addEventNode(new LvmTreeNode(node.name, node.description, node.probability, node.type), node.x, node.y)
          } else {
            this.addOperatorNode(new LvmTreeNode(node.name, node.description, node.probability, node.type), node.x, node.y)
          }
        })

        result.allEdges.forEach((edge) => {
          this.edges.add({ from: edge.from, to: edge.to });
        })
        this.visNetworkService.redraw(this.visNetwork);
        this.allEdges = result.allEdges;
        this.lvmEdges = result.lvmEdges;

      })
    }
  }

  saveFile() {
    this.visNetworkService.storePositions(this.visNetwork);
    this.lvmService.saveTreeToFile(this.lvmNodes, this.nodes, this.allEdges, this.lvmEdges);
  }

}
