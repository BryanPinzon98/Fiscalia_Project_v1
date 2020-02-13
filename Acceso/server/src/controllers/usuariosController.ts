import {Request, Response} from 'express';

import pool from '../database';

class UsuariosController{

    public async list (req: Request, res: Response): Promise<void>{
        const usuarios = await pool.query('SELECT * FROM usuarios');
        res.json(usuarios);
    }

    public async getOne (req: Request, res: Response): Promise<any>{
        const {id} = req.params;
        const usuarios = await pool.query('SELECT * FROM usuarios WHERE nombre = ?', [id]);
        if(usuarios.length > 0){
            return  res.json(usuarios[0]);
        }
        res.status(404).json({text: "El usuario no existe!"});
    }

    public async create (req: Request, res: Response): Promise<void>{
        await pool.query('INSERT INTO usuarios set ?', [req.body]);
        res.json({message: 'Usuario creado con exito!'});
    }

    public  async update (req: Request, res: Response): Promise<void>{
        const {id} = req.params;
        const usuarioViejo = req.body;
        await pool.query('UPDATE usuarios set ? WHERE nombre = ?', [req.body, id]);
        res.json({message: 'Usuario ' +req.body+ ' se ha actualizado a '+[id]+' con exito!'});
    }

    public async delete (req: Request, res: Response): Promise<void>{
        const {id} = req.params;
        const usuarios = await pool.query('DELETE FROM usuarios WHERE nombre = ?', [id]);
        res.json({message: 'El usuario ' + [id] +' ha sido eliminado'})
    }
}
export const usuariosController = new UsuariosController();