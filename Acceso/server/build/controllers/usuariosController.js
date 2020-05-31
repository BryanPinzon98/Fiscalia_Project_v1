"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const database_1 = __importDefault(require("../database"));
class UsuariosController {
    create(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                yield database_1.default.query('INSERT INTO usuarios set ?', [req.body]);
                res.json({ message: 'Usuario creado con exito!' });
            }
            catch (err) {
                res.status(404).json(err.message);
            }
        });
    }
    createAll(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const id_usuario_bd = yield database_1.default.query('SELECT `AUTO_INCREMENT` AS id FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = "usuarios"');
                const id_usuario_maximo = JSON.stringify(id_usuario_bd[0].id);
                var huella = {};
                huella.id_usuario = id_usuario_maximo;
                huella.archivo_huella = req.body.archivo_huella;
                //huella.imagen_huella = req.params.imagen_huella;
                var foto = {};
                foto.id_usuario = id_usuario_maximo;
                foto.archivo_foto = req.body.archivo_foto;
                var usuario = {};
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
                yield database_1.default.query('INSERT INTO usuarios set ?', [usuario]);
                yield database_1.default.query('INSERT INTO usuarios_foto set ?', [foto]);
                yield database_1.default.query('INSERT INTO usuario_huella set ?', [huella]);
                res.json({ message: 'Usuario creado con exito!' });
            }
            catch (err) {
                res.status(404).json(err.message);
            }
        });
    }
    getOne(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const { id } = req.params;
            const coincidencia = yield database_1.default.query('select u.id_usuario, u.rfc_usuario, u.nombres_usuario, u.apellidos_usuario, u.direccion_usuario, u.correo_usuario, u.clave_usuario, u.id_genero, u.id_estado_civil, u.id_tipo_usuario,tt.archivo_foto as archivo_foto, uh.archivo_huella as archivo_huella from usuarios u left join (select uf.* from usuarios_foto uf inner join (select max(id_foto) maximo from usuarios_foto group by id_usuario) muf on uf.id_foto=muf.maximo) tt on u.id_usuario=tt.id_usuario left join usuario_huella uh on uh.id_usuario=u.id_usuario WHERE u.id_usuario = ? GROUP by u.id_usuario', [id]);
            for (let usuario of coincidencia) {
                var buffer = new Buffer(usuario.archivo_foto);
                var bufferBase64 = buffer.toString('ascii');
                usuario.archivo_foto = bufferBase64;
                var buffer = new Buffer(usuario.archivo_huella);
                var bufferBase64 = buffer.toString('ascii');
                usuario.archivo_huella = bufferBase64;
            }
            if (coincidencia.length > 0) {
                return res.json(coincidencia[0]);
            }
            res.status(404).json({ text: "El usuario no existe!" });
        });
    }
    getAll(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const usuarios = yield database_1.default.query('SELECT * FROM usuarios ORDER BY usuarios.id_usuario');
            res.json(usuarios);
        });
    }
    update(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const { id } = req.params;
            yield database_1.default.query('UPDATE usuarios set ? WHERE id_usuario = ?', [req.body, id]);
            res.json({ message: 'Usuario ' + req.body.id + ' se ha actualizado a ' + [id] + ' con exito!' });
        });
    }
    delete(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const { id } = req.params;
            const usuarios = yield database_1.default.query('DELETE FROM usuarios WHERE id_usuario = ?', [id]);
            res.json({ message: 'El usuario ' + [id] + ' ha sido eliminado' });
        });
    }
    listarInvitados(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const usuarios = yield database_1.default.query('SELECT usuarios.id_usuario, usuarios.rfc_usuario, usuarios.nombres_usuario, usuarios.apellidos_usuario, generos.nombre_genero, tipos_usuario.nombre_tipos_usuario FROM usuarios,generos,tipos_usuario WHERE usuarios.id_tipo_usuario=5 AND usuarios.id_genero=generos.id_genero AND usuarios.id_tipo_usuario=tipos_usuario.id_tipos_usuario ORDER BY usuarios.id_usuario');
            res.json(usuarios);
        });
    }
    listarProveedores(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const usuarios = yield database_1.default.query('SELECT usuarios.id_usuario, usuarios.rfc_usuario, usuarios.nombres_usuario, usuarios.apellidos_usuario, generos.nombre_genero, tipos_usuario.nombre_tipos_usuario FROM usuarios,generos,tipos_usuario WHERE usuarios.id_tipo_usuario=6 AND usuarios.id_genero=generos.id_genero AND usuarios.id_tipo_usuario=tipos_usuario.id_tipos_usuario ORDER BY usuarios.id_usuario');
            res.json(usuarios);
        });
    }
    getCountNewPeople(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const cuenta = yield database_1.default.query('SELECT COUNT(*) AS cantidad FROM usuarios WHERE fecha_registro BETWEEN ? AND ?', [req.query.desde, req.query.hasta]);
            res.json(cuenta);
        });
    }
    getSearchByName(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            var nombreCodificado = decodeURIComponent(req.query.nombre);
            var nombre = ("%" + nombreCodificado + "%");
            var apellidoCodificado = decodeURIComponent(req.query.apellido);
            var apellido = ("%" + apellidoCodificado + "%");
            const coincidencias = yield database_1.default.query('select u.id_usuario, u.rfc_usuario, u.nombres_usuario, u.apellidos_usuario, u.direccion_usuario, u.correo_usuario, u.clave_usuario, u.id_genero, u.id_estado_civil, u.id_tipo_usuario,tt.archivo_foto as archivo_foto, uh.archivo_huella as archivo_huella from usuarios u left join (select uf.* from usuarios_foto uf inner join (select max(id_foto) maximo from usuarios_foto group by id_usuario) muf on uf.id_foto=muf.maximo) tt on u.id_usuario=tt.id_usuario left join usuario_huella uh on uh.id_usuario=u.id_usuario WHERE u.nombres_usuario LIKE ? AND u.apellidos_usuario LIKE ? GROUP by u.id_usuario', [nombre, apellido]);
            for (let usuario of coincidencias) {
                var buffer = new Buffer(usuario.archivo_foto);
                var bufferBase64 = buffer.toString('ascii');
                usuario.archivo_foto = bufferBase64;
                var buffer = new Buffer(usuario.archivo_huella);
                var bufferBase64 = buffer.toString('ascii');
                usuario.archivo_huella = bufferBase64;
            }
            res.json(coincidencias);
        });
    }
}
exports.usuariosController = new UsuariosController();
