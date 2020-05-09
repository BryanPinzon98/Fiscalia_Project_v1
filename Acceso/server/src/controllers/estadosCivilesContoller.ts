import {Request, Response} from 'express';

import pool from '../database';

class EstadosCivilesController{
    public async getCountNewPeople (req: Request, res: Response): Promise<any>{
        const cuenta = await pool.query('SELECT * FROM estados_civiles');
        res.json(cuenta); 
    }     
}
export const estadosCivilesController = new EstadosCivilesController();