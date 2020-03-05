import { Component, OnInit } from '@angular/core';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})

export class UsersComponent implements OnInit {

  usuarios: any = [];

  constructor(private usersServices: UsersService){}

  ngOnInit() {
    this.usersServices.getUsers().subscribe(
      res => {
        this.usuarios = res;
      },
      err => console.error(err)
    );
  }
}