import { Component, OnInit } from '@angular/core';
import { UsersService } from '../../services/users.service';
import { RegistrosService } from '../../services/registros.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  usuarios: any = [];
  myDate = new Date(); 

  public copy: string;
  constructor(private usersServices: UsersService, private registrosService: RegistrosService) { }

  ngOnInit() {
    this.usersServices.getUsers().subscribe(
      res => {
        this.usuarios = res;
      },
      err => console.error(err)
    ); 

    // AQUI VA LO DE ARRIBA!!!
  }

 
}


