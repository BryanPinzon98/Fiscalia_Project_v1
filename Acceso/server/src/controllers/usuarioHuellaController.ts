import {Request, Response} from 'express';

import pool from '../database';

class UsuarioHuellaController{

    public async getOne (req: Request, res: Response): Promise<any>{
        const {id} = req.params;
        const huellas = await pool.query('SELECT * FROM usuario_huella WHERE usuario_huella.id_huella= ?', [id]);
        if(huellas.length > 0){
            var buffer = new Buffer(huellas[0].archivo_huella);
            var bufferBase64 = buffer.toString('ascii');
            huellas[0].archivo_huella = bufferBase64;
            return  res.json(huellas[0]);
        }
        res.status(404).json({text: "La huella no existe!"});
    }

    public async getAll (req: Request, res: Response): Promise<void>{
        const huellas = await pool.query('SELECT * FROM usuario_huella ORDER BY usuario_huella.id_huella');
        for(let huella of huellas){
            var buffer = new Buffer(huella.archivo_huella);
            var bufferBase64 = buffer.toString('ascii');
            huella.archivo_huella = bufferBase64;
        }
        res.json(huellas);
    }

    public  async update (req: Request, res: Response): Promise<void>{
        const {id} = req.params;
        await pool.query('UPDATE usuario_huella set ? WHERE id_huella = ?', [req.body, id]);
        res.json({message: 'La huella ' +req.body.id+ ' se ha actualizado a con exito!'});
    }

    public async delete (req: Request, res: Response): Promise<void>{
        const {id} = req.params;
        await pool.query('DELETE FROM usuario_huella WHERE id_huella = ?', [id]);
        res.json({message: 'La huella ' + [id] +' ha sido eliminada con exito!'})
    }

    public async create (req: Request, res: Response): Promise<void>{
        try{
            await pool.query('INSERT INTO usuario_huella set ?', [req.body]);
            res.json({message: 'Huella creada con exito!'});
        }catch(err){
            res.status(404).json(err.message);
        }
    }  
}
export const usuarioHuellaController = new UsuarioHuellaController();