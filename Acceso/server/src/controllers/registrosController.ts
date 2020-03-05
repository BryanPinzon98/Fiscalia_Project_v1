import {Request, Response, json} from 'express';
import pool from '../database';

class RegistrosController{

    public async list (req: Request, res: Response): Promise<void>{
        const registros = await pool.query('SELECT * FROM registros');
        res.json(registros);
    }

    public async getOne (req: Request, res: Response): Promise<any>{
        const {id} = req.params;
        const registros = await pool.query('SELECT * FROM registros WHERE registros.id_registro= ?', [id]);
        if(registros.length > 0){
            return  res.json(registros[0]);
        }
        res.status(404).json({text: "El registro no existe!"});
    }

    public async create (req: Request, res: Response): Promise<void>{
        try{
            await pool.query('INSERT INTO registros set ?', [req.body]);
            res.json({message: 'Regisro creado con exito!'});
        }catch(err){
            res.status(404).json(err.message);
        }
    }

    public  async update (req: Request, res: Response): Promise<void>{
        const {id} = req.params;      
        const regisroViejo = req.body;
        await pool.query('UPDATE registros set ? WHERE id_registro = ?', [req.body, id]);
        res.json({message: 'Regisro ' +req.body.id_registro+ ' se ha actualizado a '+[id]+' con exito!'});
    }

    public async delete (req: Request, res: Response): Promise<void>{
        const {id} = req.params;
        const registros = await pool.query('DELETE FROM registros WHERE id_registro = ?', [id]);
        res.json({message: 'El regisro ' + [id] +' ha sido eliminado'})
    }

    public async getCountPeople (req: Request, res: Response): Promise<any>{
        const cuenta = await pool.query('SELECT COUNT(*) AS promedio FROM registros WHERE fecha_registro BETWEEN ? AND ?', [req.query.desde,req.query.hasta]);
        res.json(cuenta); 
    }
}
export const registrosController = new RegistrosController(); 