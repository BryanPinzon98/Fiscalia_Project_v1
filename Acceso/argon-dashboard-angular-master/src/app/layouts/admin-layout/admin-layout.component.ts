import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { RegistrosService } from '../../services/registros.service';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-admin-layout',
  templateUrl: './admin-layout.component.html',
  styleUrls: ['./admin-layout.component.scss']
})
export class AdminLayoutComponent implements OnInit {

  datoNuevos: any;
  datoSemana: any;
  datoDia: any;
  datoMes: any;
  myDate: Date = new Date();
  fechaActual: string;
  fechaSemana: string;
  fechaMes: string;

  constructor(private registrosService: RegistrosService,public datepipe: DatePipe, private usersService: UsersService) {
    this.fechaActual=this.datepipe.transform(this.myDate.getTime()-this.calcularDias(0), 'yyyy-MM-dd');
    this.fechaSemana=this.datepipe.transform(this.myDate.getTime()-this.calcularDias(7), 'yyyy-MM-dd');
    this.fechaMes=this.datepipe.transform(this.myDate.getTime()-this.calcularDias(30), 'yyyy-MM-dd');
  }

  ngOnInit() {
    this.registrosService.getCount(this.fechaActual, this.fechaActual).subscribe(
      res => {
        this.datoDia = res;
      },
      err => console.error(err)
    );

    this.registrosService.getCount(this.fechaSemana, this.fechaActual).subscribe(
      res => {
        this.datoSemana = res;
      },
      err => console.error(err)
    );

    this.registrosService.getCount(this.fechaMes, this.fechaActual).subscribe(
      res => {
        this.datoMes = res;
      },
      err => console.error(err)
    );

    this.usersService.getCountNewPeople(this.fechaMes, this.fechaActual).subscribe(
      res => {
        this.datoNuevos = res;
      },
      err => console.error(err)
    );
  }

  calcularDias(dias: number): any{
    return 1000 * 60 * 60 * 24 * dias;
  }
}