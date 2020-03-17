import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User} from '../models/User'
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  API_URI = 'http://localhost:3000/api';

  constructor(private http: HttpClient) { }

getUsersInvitados(){
  return this.http.get(`${this.API_URI}/usuarios/invitados`);
}

getUsersProveedores(){
  return this.http.get(`${this.API_URI}/usuarios/proveedores`);
}

getUser(id: string){
  return this.http.get(`${this.API_URI}/usuarios/${id}`);
}

saveUser(user: User){
  return this.http.post(`${this.API_URI}/usuarios`, user);
}

deleteUser(id: string){
  return this.http.delete(`${this.API_URI}/usuarios/${id}`);
}

updateUser(id: string, user: User): Observable<any> {
  return this.http.put(`${this.API_URI}/usuarios/${id}`, user);
}

getCountNewPeople(ini: string, fin: string){
  return this.http.get(`${this.API_URI}/usuarios/porfecha?desde=`+ini+`&hasta=`+fin);
}

}
