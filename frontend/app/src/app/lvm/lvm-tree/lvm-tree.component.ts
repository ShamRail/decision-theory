import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Data, DataSet, Edge, Node, Options, VisNetworkService} from "ngx-vis";
import {LvmNodeResult, LvmService, LvmTreeEdge, LvmTreeNode} from "../service/lvm.service";

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
  public selectedNode?: Node;
  public selectedEdge?: Edge;

  public allEdges: LvmTreeEdge[] = [];
  public lvmNodes: LvmTreeNode[] = [];
  public selectedLvmNode?: LvmTreeNode;
  public selectedLvmEdge?: LvmTreeEdge;

  public nodeId: number = 1;
  public edgePerId: Map<String, {from: string, to: string}> = new Map<String, {from: string, to: string}>();

  constructor(
    private visNetworkService: VisNetworkService,
    private lvmService: LvmService) {
  }

  ngOnInit(): void {
    this.visNetworkOptions = {
      autoResize: false,
      interaction: {
        selectConnectedEdges: false
      },
      edges: {
        arrows: { to: true }
      },
      physics: {
        enabled: false
      },
      manipulation: {
        addEdge: (edgeData: any, callback: any) => {
          if (edgeData.from === edgeData.to) {
            return;
          }
          const sourceNode = this.lvmNodes.find((n) => n.id == edgeData.from);
          const targetNode = this.lvmNodes.find((n) => n.id == edgeData.to);
          console.log("Bind edges: ", sourceNode, targetNode);
          if (sourceNode && targetNode) {
            if (!sourceNode.isLogicOperator() && !targetNode.isLogicOperator()) {
              console.log("Can't bind edges. Reason: both are events");
              return;
            }
            if (sourceNode.isLogicOperator() && targetNode.isLogicOperator()) {
              console.log("Can't bind edges. Reason: both are logic operators");
              return;
            }
            if (this.allEdges.find((n => n.to == targetNode.id && n.from == sourceNode.id
                                      || n.from == targetNode.id && n.to == sourceNode.id))) {
              console.log("Can't bind edges. Reason: edge is already existed");
              return;
            }
            const logicOperator = sourceNode.isLogicOperator() ? sourceNode : targetNode;
            const eventNode = sourceNode.isLogicOperator() ? targetNode : sourceNode;
            const operatorParent = this.allEdges.find((n) => n.to == logicOperator.id);
            if (targetNode.isLogicOperator() && operatorParent) {
              return;
            }
            this.allEdges.push(new LvmTreeEdge(edgeData.id, sourceNode.id, targetNode.id, logicOperator.type, targetNode.probability));
            callback(edgeData);
            this.edgePerId.set(edgeData.id, { from: edgeData.from, to: edgeData.to })
          }
        }
      }
    };
    this.visNetworkData = {nodes: this.nodes, edges: this.edges};
  }

  initialized() {
    this.visNetworkService.on(this.visNetwork, 'selectNode');
    this.visNetworkService.on(this.visNetwork, 'deselectNode');
    this.visNetworkService.on(this.visNetwork, 'selectEdge');
    this.visNetworkService.on(this.visNetwork, 'deselectEdge');

    this.visNetworkService.selectNode.subscribe((node) => {
        let nodeId = node[1].nodes[0];
        this.selectedLvmNode = this.lvmNodes.find(n => n.id == nodeId);
        this.selectedNode = this.nodes.get().find(item => item.id == nodeId);
        console.log("Chosen node: ", this.selectedNode, this.selectedLvmNode);
        this.unSelectEdge();
        if (this.selectedLvmNode) {
          if (this.selectedLvmNode.isLogicOperator()) {
            this.option = 'operator';
            this.operatorOption = this.selectedLvmNode.name;
          } else {
            this.option = this.selectedLvmNode.probability > -1 ? 'initEvent' : 'event';
          }
          this.eventName = this.selectedLvmNode.name;
          this.eventDescription = this.selectedLvmNode.description;
          this.eventProbability = this.selectedLvmNode.probability;
        }
        this.visNetworkService.addEdgeMode(this.visNetwork);
      }
    );

    this.visNetworkService.selectEdge.subscribe((event) => {
      const edge = this.edgePerId.get(event[1].edges[0]);
      console.log("Chosen edge: ", edge);
      this.unSelectNode();
      if (edge) {
        this.selectedLvmEdge = this.allEdges.find(e =>
          e.from == edge.from && e.to == edge.to ||
          e.from == edge.to && e.to == edge.from
        );
        this.selectedEdge = this.edges.get().find(e =>
          e.from == edge.from && e.to == edge.to ||
          e.from == edge.to && e.to == edge.from
        );
        console.log("Found edges: ", this.selectedLvmEdge, this.selectedEdge);
      }
    });

    this.visNetworkService.deselectNode.subscribe((_) => {
      this.unSelectNode();
      this.clearValues();
      this.visNetworkService.disableEditMode(this.visNetwork);
    });

    this.visNetworkService.deselectEdge.subscribe((_) => {
      this.unSelectEdge();
    })

  }

  addNode() {
    const newId = 'n' + this.nodeId++;
    if (this.option === 'event' || this.option === 'initEvent') {
      this.addEventNode(new LvmTreeNode(
        newId, this.eventName, this.eventDescription,
        this.option === 'initEvent' ? this.eventProbability : -1
      ));
    } else {
      this.addOperatorNode(new LvmTreeNode(
        newId, this.operatorOption, '', -1, this.operatorOption
      ));
    }
    this.visNetworkService.enableEditMode(this.visNetwork);
    this.clearValues();
  }

  private addEventNode(node: LvmTreeNode, x?: number, y?: number) {
    const isInit = node.probability != -1;
    let text = `${node.name}\n${node.description}`;
    text += isInit ? `\nP = ${node.probability}` : '';
    this.drawNode({
      id: node.id, label: text, shape: 'box', color: isInit ? 'red' : 'green',
      x: x, y: y
    }, node);
  }

  private addOperatorNode(node: LvmTreeNode, x?: number, y?: number) {
    this.drawNode({
      id: node.id, label: node.name, shape: 'box',
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
        this.allEdges = [];
        this.edgePerId.clear();

        let maxId = 0;
        result.nodes.forEach((node) => {
          const type = node.type;
          const id = Number(node.id.slice(1));
          maxId = id > maxId ? id : maxId;
          if ("NONE" == type) {
            this.addEventNode(new LvmTreeNode(node.id, node.name, node.description, node.probability, node.type), node.x, node.y)
          } else {
            this.addOperatorNode(new LvmTreeNode(node.id, node.name, node.description, node.probability, node.type), node.x, node.y)
          }
        })
        this.nodeId = maxId + 1;

        result.allEdges.forEach((edge) => {
          this.edges.add({ from: edge.from, to: edge.to });
          this.edgePerId.set(edge.id, { from: edge.from, to: edge.to });
        })
        this.visNetworkService.redraw(this.visNetwork);
        this.allEdges = result.allEdges;

      })
    }
  }

  saveFile() {
    this.visNetworkService.storePositions(this.visNetwork);
    this.lvmService.saveTreeToFile(this.lvmNodes, this.nodes, this.allEdges);
  }

  updateSelected() {
    if (this.selectedLvmNode && this.selectedNode) {

      console.log("Update node: ", this.selectedLvmNode);
      this.selectedLvmNode.description = this.eventDescription;
      this.selectedLvmNode.probability = this.eventProbability;
      if (this.selectedLvmNode.isLogicOperator()) {
        this.selectedLvmNode.name = this.operatorOption;
        this.selectedLvmNode.type = this.operatorOption;
        const nodeId = this.selectedLvmNode.id;
        this.allEdges.filter(e => e.from == nodeId).forEach(e => e.type = this.operatorOption);
      } else {
        this.selectedLvmNode.name = this.eventName;
      }
      console.log("Update result: ", this.selectedLvmNode);

      const isInit = this.selectedLvmNode.probability != -1;
      let text = `${this.selectedLvmNode.name}\n${this.selectedLvmNode.description}`;
      text += isInit ? `\nP = ${this.selectedLvmNode.probability}` : ''.trim();
      this.selectedNode.label = text;

      // @ts-ignore
      this.nodes.updateOnly([{ id: this.selectedNode.id, label: text} ]);
      this.visNetworkService.redraw(this.visNetwork);
    }

  }

  unSelectNode() {
    this.selectedLvmNode = undefined;
    this.selectedNode = undefined;
  }

  clearValues() {
    this.eventName = '';
    this.eventDescription = '';
    this.eventProbability = 0;
  }

  delete() {
    console.log("Try to remove. Node: ", this.selectedNode, " Edge: ", this.selectedEdge);
    if (this.selectedNode) {
      console.log("Remove node: ", this.selectedNode);
      const id = this.selectedNode.id;
      const edgesToRemove = this.edges.get().filter(e => e.from == id || e.to == id);
      edgesToRemove.forEach(e => this.removeEdge(e.from, e.to, e.id));
      // @ts-ignore
      this.nodes.remove(id);
      this.lvmNodes = this.lvmNodes.filter(n => n.id != id);
      this.unSelectNode();
    } else if (this.selectedEdge) {
      console.log("Remove edge: ", this.selectedEdge);
      this.removeEdge(this.selectedEdge.from, this.selectedEdge.to, this.selectedEdge.id);
      this.unSelectEdge();
    } else {
      console.log("Nothing to remove");
    }
  }

  unSelectEdge() {
    this.selectedEdge = undefined;
    this.selectedLvmEdge = undefined;
  }

  removeEdge(from: any, to: any, id: any) {
    const ids = [from, to];
    // @ts-ignore
    this.edges.remove(id);
    this.allEdges = this.allEdges.filter(n => !ids.includes(n.from) && !ids.includes(n.to));
  }

  calculate() {
    this.lvmService.calculateTree(this.allEdges, this.lvmNodes);
  }

}
