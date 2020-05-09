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
    listarInvitados(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const usuarios = yield database_1.default.query('SELECT * FROM usuarios WHERE usuarios.id_tipo_usuario=5 ORDER BY usuarios.id_usuario');
            res.json(usuarios);
        });
    }
    listarProveedores(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const usuarios = yield database_1.default.query('SELECT * FROM usuarios WHERE usuarios.id_tipo_usuario=6 ORDER BY usuarios.id_usuario');
            res.json(usuarios);
        });
    }
    getOne(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const { id } = req.params;
            const usuarios = yield database_1.default.query('SELECT * FROM usuarios WHERE usuarios.id_usuario= ? AND usuarios.id_genero=generos.id_genero AND usuarios.id_estado_civil=estados_civiles.id_estado_civil AND usuarios.id_tipo_usuario=tipos_usuario.id_tipos_usuario', [id]);
            if (usuarios.length > 0) {
                return res.json(usuarios[0]);
            }
            res.status(404).json({ text: "El usuario no existe!" });
        });
    }
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
    createFull(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const id_usuario_bd = yield database_1.default.query('SELECT MAX(id_usuario) AS id FROM usuarios');
                const id_usuario_maximo = JSON.stringify(id_usuario_bd[0].id + 1);
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
                console.log(usuario);
                console.log(foto);
                console.log(huella);
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
    update(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const { id } = req.params;
            const usuarioViejo = req.body;
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
    getCountNewPeople(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const cuenta = yield database_1.default.query('SELECT COUNT(*) AS cantidad FROM usuarios WHERE fecha_registro BETWEEN ? AND ?', [req.query.desde, req.query.hasta]);
            res.json(cuenta);
        });
    }
}
exports.usuariosController = new UsuariosController();