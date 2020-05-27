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
class UsuarioHuellaController {
    getOne(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const { id } = req.params;
            const huellas = yield database_1.default.query('SELECT * FROM usuario_huella WHERE usuario_huella.id_huella= ?', [id]);
            if (huellas.length > 0) {
                var buffer = new Buffer(huellas[0].archivo_huella);
                var bufferBase64 = buffer.toString('ascii');
                huellas[0].archivo_huella = bufferBase64;
                return res.json(huellas[0]);
            }
            res.status(404).json({ text: "La huella no existe!" });
        });
    }
    getAll(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const huellas = yield database_1.default.query('SELECT * FROM usuario_huella ORDER BY usuario_huella.id_huella');
            for (let huella of huellas) {
                var buffer = new Buffer(huella.archivo_huella);
                var bufferBase64 = buffer.toString('ascii');
                huella.archivo_huella = bufferBase64;
            }
            res.json(huellas);
        });
    }
    update(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const { id } = req.params;
            yield database_1.default.query('UPDATE usuario_huella set ? WHERE id_huella = ?', [req.body, id]);
            res.json({ message: 'La huella ' + req.body.id + ' se ha actualizado a con exito!' });
        });
    }
    delete(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const { id } = req.params;
            yield database_1.default.query('DELETE FROM usuario_huella WHERE id_huella = ?', [id]);
            res.json({ message: 'La huella ' + [id] + ' ha sido eliminada con exito!' });
        });
    }
    create(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                yield database_1.default.query('INSERT INTO usuario_huella set ?', [req.body]);
                res.json({ message: 'Huella creada con exito!' });
            }
            catch (err) {
                res.status(404).json(err.message);
            }
        });
    }
}
exports.usuarioHuellaController = new UsuarioHuellaController();
