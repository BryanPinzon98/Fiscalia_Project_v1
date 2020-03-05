import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Record } from '../models/Record';
import { Observable } from 'rxjs';
import { Time } from '../models/Time';
import { toInteger } from '@ng-bootstrap/ng-bootstrap/util/util';

@Injectable({
  providedIn: 'root'
})
export class RegistrosService {

  API_URI = 'http://localhost:3000/api';

  constructor(private http: HttpClient) { }

getRecords(){
  return this.http.get(`${this.API_URI}/registros`);
}

getRecord(id: string){
  return this.http.get(`${this.API_URI}/registros/${id}`);
}

saveRecord(record: Record){
  return this.http.post(`${this.API_URI}/registros`, record);
}

deleteRecord(id: string){
  return this.http.delete(`${this.API_URI}/registros/${id}`);
}

updateRecord(id: string, record: Record): Observable<any> {
  return this.http.put(`${this.API_URI}/registros/${id}`, record);
}

getCount(ini: string, fin: string){
  return this.http.get(`${this.API_URI}/registros/porfecha?desde=`+ini+`&hasta=`+fin);
}
}
