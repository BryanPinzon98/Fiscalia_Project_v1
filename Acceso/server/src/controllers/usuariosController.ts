import {Request, Response} from 'express';

import pool from '../database';
import fs from 'fs';

class UsuariosController{

    private decodificarFoto(usuarios:any): any {
            for(let usuario of usuarios){
                var buffer = new Buffer(usuario.archivo_foto);
                var bufferBase64 = buffer.toString('ascii');
                usuario.archivo_foto = bufferBase64;
             }
            return usuarios;
    }

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

            var foto: any = {};
            foto.id_usuario = id_usuario_maximo;
            foto.archivo_foto = req.body.archivo_foto;

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
        const usuarios = await pool.query('SELECT usuarios.id_usuario, usuarios.rfc_usuario, usuarios.nombres_usuario, usuarios.apellidos_usuario, generos.nombre_genero, tipos_usuario.nombre_tipos_usuario FROM usuarios,generos,tipos_usuario WHERE usuarios.id_tipo_usuario=5 AND usuarios.id_genero=generos.id_genero AND usuarios.id_tipo_usuario=tipos_usuario.id_tipos_usuario ORDER BY usuarios.id_usuario');
        res.json(usuarios);
    }

    public async listarProveedores (req: Request, res: Response): Promise<void>{
        const usuarios = await pool.query('SELECT usuarios.id_usuario, usuarios.rfc_usuario, usuarios.nombres_usuario, usuarios.apellidos_usuario, generos.nombre_genero, tipos_usuario.nombre_tipos_usuario FROM usuarios,generos,tipos_usuario WHERE usuarios.id_tipo_usuario=6 AND usuarios.id_genero=generos.id_genero AND usuarios.id_tipo_usuario=tipos_usuario.id_tipos_usuario ORDER BY usuarios.id_usuario');
        res.json(usuarios);
    }
    
    public async getCountNewPeople (req: Request, res: Response): Promise<any>{
        const cuenta = await pool.query('SELECT COUNT(*) AS cantidad FROM usuarios WHERE fecha_registro BETWEEN ? AND ?', [req.query.desde,req.query.hasta]);
        res.json(cuenta); 
    }  
    
    public async getSearchByName (req: Request, res: Response): Promise<any>{
        var nombreCodificado = decodeURIComponent(req.query.nombre);
        var nombre = ("%" + nombreCodificado + "%");
        var apellidoCodificado = decodeURIComponent(req.query.apellido);
        var apellido = ("%" + apellidoCodificado + "%");
        const coincidencias = await pool.query('SELECT usuarios.id_usuario, usuarios.nombres_usuario, usuarios.apellidos_usuario, usuarios.id_tipo_usuario, usuarios.rfc_usuario, usuarios.direccion_usuario, usuarios_foto.archivo_foto FROM usuarios, usuarios_foto, tipos_usuario WHERE usuarios.nombres_usuario LIKE ? AND usuarios.apellidos_usuario LIKE ? AND usuarios.id_tipo_usuario = tipos_usuario.id_tipos_usuario AND usuarios.id_usuario = usuarios_foto.id_usuario', [nombre, apellido]);
        for(let usuario of coincidencias){
            var buffer = new Buffer(usuario.archivo_foto);
            var bufferBase64 = buffer.toString('ascii');
            usuario.archivo_foto = bufferBase64;
        }
        res.json(coincidencias);         
    }  
}
export const usuariosController = new UsuariosController();