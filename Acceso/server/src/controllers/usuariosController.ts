import {Request, Response} from 'express';

import pool from '../database';

class UsuariosController{

    public async list (req: Request, res: Response): Promise<void>{
        const usuarios = await pool.query('SELECT usuarios.id_usuario, usuarios.rfc_usuario, usuarios.nombres_usuario, usuarios.apellidos_usuario, generos.nombre_genero, tipos_usuario.nombre_tipos_usuario FROM usuarios,generos,tipos_usuario WHERE usuarios.id_genero=generos.id_genero AND usuarios.id_tipo_usuario=tipos_usuario.id_tipos_usuario ORDER BY usuarios.id_usuario');
        res.json(usuarios);
    }

    public async getOne (req: Request, res: Response): Promise<any>{
        const {id} = req.params;
        const usuarios = await pool.query('SELECT usuarios.id_usuario, usuarios.rfc_usuario, usuarios.nombres_usuario, usuarios.apellidos_usuario, usuarios.direccion_usuario, usuarios.correo_usuario, usuarios.clave_usuario, generos.nombre_genero, estados_civiles.nombre_estado_civil, tipos_usuario.nombre_tipos_usuario FROM usuarios,generos,estados_civiles,tipos_usuario WHERE usuarios.id_usuario= ? AND usuarios.id_genero=generos.id_genero AND usuarios.id_estado_civil=estados_civiles.id_estado_civil AND usuarios.id_tipo_usuario=tipos_usuario.id_tipos_usuario', [id]);
        if(usuarios.length > 0){
            return  res.json(usuarios[0]);
        }
        res.status(404).json({text: "El usuario no existe!"});
    }

    public async create (req: Request, res: Response): Promise<void>{
        try{
            await pool.query('INSERT INTO usuarios set ?', [req.body]);
            res.json({message: 'Usuario creado con exito!'});
        }catch(err){
            res.status(404).json(err.message);
        }
    }

    public  async update (req: Request, res: Response): Promise<void>{
        const {id} = req.params;
        const usuarioViejo = req.body;
        await pool.query('UPDATE usuarios set ? WHERE id_usuario = ?', [req.body, id]);
        res.json({message: 'Usuario ' +req.body.id+ ' se ha actualizado a '+[id]+' con exito!'});
    }

    public async delete (req: Request, res: Response): Promise<void>{
        const {id} = req.params;
        const usuarios = await pool.query('DELETE FROM usuarios WHERE id_usuario = ?', [id]);
        res.json({message: 'El usuario ' + [id] +' ha sido eliminado'})
    }

    public async getCountNewPeople (req: Request, res: Response): Promise<any>{
        const cuenta = await pool.query('SELECT COUNT(*) AS cantidad FROM usuarios WHERE fecha_registro BETWEEN ? AND ?', [req.query.desde,req.query.hasta]);
        res.json(cuenta); 
    }    
}
export const usuariosController = new UsuariosController();