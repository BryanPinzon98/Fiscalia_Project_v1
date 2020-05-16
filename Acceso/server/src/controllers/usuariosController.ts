import {Request, Response} from 'express';

import pool from '../database';
import fs from 'fs';

class UsuariosController{

    public async create (req: Request, res: Response): Promise<void>{
        try{
            await pool.query('INSERT INTO usuarios set ?', [req.body]);
            res.json({message: 'Usuario creado con exito!'});
        }catch(err){
            res.status(404).json(err.message);
        }
    }

    public async createAll (req: Request, res: Response): Promise<void>{
        try{
            const id_usuario_bd = await pool.query('SELECT `AUTO_INCREMENT` AS id FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = "usuarios"');
            const id_usuario_maximo = JSON.stringify(id_usuario_bd[0].id);
            
            var huella: any = {};
            huella.id_usuario = id_usuario_maximo;
            huella.archivo_huella = req.body.archivo_huella;
            //huella.imagen_huella = req.params.imagen_huella;
            console.log(huella);
            var foto: any = {};
            foto.id_usuario = id_usuario_maximo;
            foto.archivo_foto = req.body.archivo_foto;
            console.log(foto);
            var usuario: any = {};
            usuario.id_usuario = id_usuario_maximo;
            usuario.rfc_usuario = req.body.rfc_usuario;
            usuario.nombres_usuario = req.body.nombres_usuario;
            usuario.apellidos_usuario = req.body.apellidos_usuario;
            usuario.direccion_usuario = req.body.direccion_usuario;
            usuario.correo_usuario = req.body.correo_usuario;
            usuario.clave_usuario = req.body.clave_usuario;
            usuario.id_genero = req.body.id_genero;
            usuario.id_estado_civil = req.body.id_estado_civil;
            usuario.id_tipo_usuario = req.body.id_tipo_usuario;

            console.log(usuario);

            await pool.query('INSERT INTO usuarios set ?', [usuario]);
            await pool.query('INSERT INTO usuarios_foto set ?', [foto]);
            await pool.query('INSERT INTO usuario_huella set ?', [huella]);
            
            res.json({message: 'Usuario creado con exito!'});
        }catch(err){
            res.status(404).json(err.message);
        }
    }

    public async getOne (req: Request, res: Response): Promise<any>{
        const {id} = req.params;
        const usuarios = await pool.query('SELECT * FROM usuarios WHERE usuarios.id_usuario= ?', [id]);
        if(usuarios.length > 0){
            return  res.json(usuarios[0]);
        }
        res.status(404).json({text: "El usuario no existe!"});
    }

    public async getAll (req: Request, res: Response): Promise<void>{
        const usuarios = await pool.query('SELECT * FROM usuarios ORDER BY usuarios.id_usuario');
        res.json(usuarios);
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
    
    public async listarInvitados (req: Request, res: Response): Promise<void>{
        const usuarios = await pool.query('SELECT * FROM usuarios WHERE usuarios.id_tipo_usuario=5 ORDER BY usuarios.id_usuario');
        res.json(usuarios);
    }

    public async listarProveedores (req: Request, res: Response): Promise<void>{
        const usuarios = await pool.query('SELECT * FROM usuarios WHERE usuarios.id_tipo_usuario=6 ORDER BY usuarios.id_usuario');
        res.json(usuarios);
    }
    
    public async getCountNewPeople (req: Request, res: Response): Promise<any>{
        const cuenta = await pool.query('SELECT COUNT(*) AS cantidad FROM usuarios WHERE fecha_registro BETWEEN ? AND ?', [req.query.desde,req.query.hasta]);
        res.json(cuenta); 
    }  
    
    public async prueba (req: Request, res: Response): Promise<void>{
        const usuarios = await pool.query('SELECT * FROM usuarios_foto');
        var buffer = new Buffer(usuarios[0].archivo_foto);
        var bufferBase64 = buffer.toString('ascii');

        console.log(bufferBase64);
        res.json(usuarios);
    }
}
export const usuariosController = new UsuariosController();