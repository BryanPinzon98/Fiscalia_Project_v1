import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { UserViewComponent } from '../../pages/user-view/user-view.component';

@Component({
  selector: 'app-admin-layout',
  templateUrl: './admin-layout.component.html',
  styleUrls: ['./admin-layout.component.scss']
})
export class AdminLayoutComponent implements OnInit {

  constructor(public dialog:MatDialog){}

  ngOnInit() {
  }

  openDialog(): void {
    console.log('holiiii');
    const dialogRef = this.dialog.open(UserViewComponent, {
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}
