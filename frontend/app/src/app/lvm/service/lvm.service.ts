import { Injectable } from '@angular/core';
import {DataSet, Node} from "ngx-vis";
import {FileService} from "../../shared/FileService";
import {Observable} from "rxjs";

export class LvmTreeNode {

  public id: string = '';
  public name: string = '';
  public description: string = '';
  public probability: number = -1;
  public type: string = 'none';

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
    return this.type !== 'none';
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
              public allEdges: LvmTreeEdge[],
              public lvmEdges: LvmTreeEdge[]) {
  }
}

@Injectable({
  providedIn: 'root'
})
export class LvmService {

  constructor(private fileService: FileService) { }

  saveTreeToFile(lvmNodes: LvmTreeNode[], nodes: DataSet<Node>, allEdges: LvmTreeEdge[], lvmEdges: LvmTreeEdge[]) {
    let nodesToSave = lvmNodes.map(lvmNode => {
      let node = nodes.get().find((item) => item.id == lvmNode.id)
      if (!node) {
        throw new Error("Invalid id");
      }
      return new DetailedLvmTreeNode(lvmNode, node.x, node.y, node.color);
    });
    this.fileService.saveToFile(new FileDTO(nodesToSave, allEdges, lvmEdges))
  }

  restoreFromFile(file: File): Observable<FileDTO> {
    return this.fileService.restoreObject<FileDTO>(file);
  }

}
