import { Component, OnInit } from '@angular/core';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})

export class UsersComponent implements OnInit {

  invitados: any = [];
  proveedores: any = [];

  constructor(private usersServices: UsersService){}

  ngOnInit() {
    this.usersServices.getUsersInvitados().subscribe(
      res => {
        this.invitados = res;
      },
      err => console.error(err)
    );

    this.usersServices.getUsersProveedores().subscribe(
      res => {
        this.proveedores = res;
      },
      err => console.error(err)
    );
  }
}