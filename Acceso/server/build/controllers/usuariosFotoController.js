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
class UsuariosFotoController {
    getOne(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const { id } = req.params;
            const fotos = yield database_1.default.query('SELECT * FROM usuarios_foto WHERE usuarios_foto.id_foto= ?', [id]);
            if (fotos.length > 0) {
                var buffer = new Buffer(fotos[0].archivo_foto);
                var bufferBase64 = buffer.toString('ascii');
                fotos[0].archivo_foto = bufferBase64;
                return res.json(fotos[0]);
            }
            res.status(404).json({ text: "La foto no existe!" });
        });
    }
    getAll(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const fotos = yield database_1.default.query('SELECT * FROM usuarios_foto ORDER BY usuarios_foto.id_foto');
            for (let foto of fotos) {
                var buffer = new Buffer(foto.archivo_foto);
                var bufferBase64 = buffer.toString('ascii');
                foto.archivo_foto = bufferBase64;
            }
            res.json(fotos);
        });
    }
    update(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const { id } = req.params;
            yield database_1.default.query('UPDATE usuarios_foto set ? WHERE id_foto = ?', [req.body, id]);
            res.json({ message: 'La foto ' + req.body.id + ' se ha actualizado a con exito!' });
        });
    }
    delete(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const { id } = req.params;
            yield database_1.default.query('DELETE FROM usuarios_foto WHERE id_foto = ?', [id]);
            res.json({ message: 'La foto ' + [id] + ' ha sido eliminada con exito!' });
        });
    }
    create(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                yield database_1.default.query('INSERT INTO usuarios_foto set ?', [req.body]);
                res.json({ message: 'Foto creada con exito!' });
            }
            catch (err) {
                res.status(404).json(err.message);
            }
        });
    }
}
exports.usuariosFotoController = new UsuariosFotoController();
