import {Request, Response} from 'express';

import pool from '../database';

class GenerosController{
    public async getGeneros (req: Request, res: Response): Promise<any>{
        const cuenta = await pool.query('SELECT * FROM generos');
        res.json(cuenta); 
    }  

     
}
export const generosController = new GenerosController();