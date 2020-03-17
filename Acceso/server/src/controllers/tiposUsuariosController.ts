import {Request, Response} from 'express';

import pool from '../database';

class TiposUsuariosController{
    public async getTiposUsuarios(req: Request, res: Response): Promise<any>{
        const cuenta = await pool.query('SELECT * FROM tipos_usuario');
        res.json(cuenta); 
    }       
}
export const tiposUsuariosController = new TiposUsuariosController();