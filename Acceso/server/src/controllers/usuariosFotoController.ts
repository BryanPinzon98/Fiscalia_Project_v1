import {Request, Response} from 'express';

import pool from '../database';

class UsuariosFotoController{
    public async getOne (req: Request, res: Response): Promise<any>{
        const {id} = req.params;
        const fotos = await pool.query('SELECT * FROM usuarios_foto WHERE usuarios_foto.id_foto= ?', [id]);
        if(fotos.length > 0){
            var buffer = new Buffer(fotos[0].archivo_foto);
            var bufferBase64 = buffer.toString('ascii');
            fotos[0].archivo_foto = bufferBase64;
            return  res.json(fotos[0]);
        }
        res.status(404).json({text: "La foto no existe!"});
    }

    public async getAll (req: Request, res: Response): Promise<void>{
        const fotos = await pool.query('SELECT * FROM usuarios_foto ORDER BY usuarios_foto.id_foto');
        for(let foto of fotos){
            var buffer = new Buffer(foto.archivo_foto);
            var bufferBase64 = buffer.toString('ascii');
            foto.archivo_foto = bufferBase64;
        }
        res.json(fotos);
    }

    public  async update (req: Request, res: Response): Promise<void>{
        const {id} = req.params;
        await pool.query('UPDATE usuarios_foto set ? WHERE id_foto = ?', [req.body, id]);
        res.json({message: 'La foto ' +req.body.id+ ' se ha actualizado a con exito!'});
    }

    public async delete (req: Request, res: Response): Promise<void>{
        const {id} = req.params;
        await pool.query('DELETE FROM usuarios_foto WHERE id_foto = ?', [id]);
        res.json({message: 'La foto ' + [id] +' ha sido eliminada con exito!'})
    }

    public async create (req: Request, res: Response): Promise<void>{
        try{
            await pool.query('INSERT INTO usuarios_foto set ?', [req.body]);
            res.json({message: 'Foto creada con exito!'});
        }catch(err){
            res.status(404).json(err.message);
        }
    }  
}
export const usuariosFotoController = new UsuariosFotoController();