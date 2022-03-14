import { Injectable } from '@angular/core';
import {DataSet, Node} from "ngx-vis";
import {FileService} from "../../shared/FileService";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";

export class LvmTreeNode {

  public id: string = '';
  public name: string = '';
  public description: string = '';
  public probability: number = -1;
  public type: string = 'NONE';

  constructor(id: string, name: string, description: string, probability: number, type?: string) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.probability = probability;
    if (type) {
      this.type = type;
    }
  }

  public isLogicOperator(): boolean {
    return this.type !== 'NONE';
  }

}

export class LvmTreeEdge {
  constructor(
    public id: string,
    public from: string,
    public to: string,
    public type: string,
    public probability: number = -1) {
  }
}

export class DetailedLvmTreeNode extends LvmTreeNode {

  public x?: number = -1;
  public y?: number = -1;
  public color?: any = '';

  constructor(node: LvmTreeNode, x?: number, y?: number, color?: any) {
    super(
      node.id, node.name, node.description, node.probability
    );
    this.x = x;
    this.y = y;
    this.color = color;
    this.type = node.type;
  }
}

class FileDTO {
  constructor(public nodes: DetailedLvmTreeNode[],
              public allEdges: LvmTreeEdge[]) {
  }
}

export class LvmNodeResult {
  constructor(public nodeName: string,
              public logicalFunction: string,
              public notAndLogicalFunction: string,
              public probabilityFunction: string,
              public resultProbability: number) {
  }
}

@Injectable({
  providedIn: 'root'
})
export class LvmService {

  private readonly api: string = environment.apiUrl + '/lvm';

  constructor(private fileService: FileService,
              private httpClient: HttpClient) { }

  saveTreeToFile(lvmNodes: LvmTreeNode[], nodes: DataSet<Node>, allEdges: LvmTreeEdge[]) {
    let nodesToSave = lvmNodes.map(lvmNode => {
      let node = nodes.get().find((item) => item.id == lvmNode.id)
      if (!node) {
        throw new Error("Invalid id");
      }
      return new DetailedLvmTreeNode(lvmNode, node.x, node.y, node.color);
    });
    this.fileService.saveToFile(new FileDTO(nodesToSave, allEdges))
  }

  restoreFromFile(file: File): Observable<FileDTO> {
    return this.fileService.restoreObject<FileDTO>(file);
  }

  calculateTree(edges: LvmTreeEdge[], nodes: LvmTreeNode[]): Observable<LvmNodeResult[]> {
    const namePerId = new Map<string, string>();
    const probabilityPerNode: {[k: string]: any} = {};
    nodes.forEach(n => {
      namePerId.set(n.id, n.name);
      if (n.probability > -1) {
        probabilityPerNode[n.name] = n.probability;
      }
    });
    const transformedEdges = this.formAdjancentList(nodes, edges).map(e => {
      // @ts-ignore
      return new LvmTreeEdge(e.id, namePerId.get(e.from), namePerId.get(e.to), e.type, e.probability);
    })
    console.log("Calculate tree with ", transformedEdges, " ", probabilityPerNode);
    return this.httpClient.post<LvmNodeResult[]>(`${this.api}/calculate-tree`, {
      edges: transformedEdges,
      nodeProbabilities: probabilityPerNode
    })
  }

  formAdjancentList(nodes: LvmTreeNode[], edges: LvmTreeEdge[]): LvmTreeEdge[] {
    const result: LvmTreeEdge[] = [];
    for (let node of nodes) {
      if (node.isLogicOperator()) {
        const childrenEdges = edges.filter(e => e.from == node.id)
        const parentEdge = edges.find(e => e.to == node.id);
        if (parentEdge) {
          for (let edge of childrenEdges) {
            result.push(new LvmTreeEdge(
              edge.id, parentEdge.from, edge.to, edge.type, edge.probability
            ));
          }
        }
      }
    }
    return result;
  }

}
