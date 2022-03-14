import {Injectable} from "@angular/core";
import {from, Observable} from "rxjs";
import {map} from "rxjs/operators";

Injectable({
  providedIn: 'root'
})
export class FileService {

  saveToFile(obj: any) {
    const json = JSON.stringify(obj, null, '\t');
    const blob = new Blob([json], {type: 'application/json'});
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = 'export.json';
    link.click();
    URL.revokeObjectURL(link.href);
  }

  restoreObject<T>(file: File): Observable<T> {
    return from(file.text())
      .pipe(map(text => JSON.parse(text)))
      .pipe(map(obj => (<T> obj)));
  }

}
