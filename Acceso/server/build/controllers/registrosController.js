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
class RegistrosController {
    list(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const registros = yield database_1.default.query('SELECT * FROM registros');
            res.json(registros);
        });
    }
    getOne(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const { id } = req.params;
            const registros = yield database_1.default.query('SELECT * FROM registros WHERE registros.id_registro= ?', [id]);
            if (registros.length > 0) {
                return res.json(registros[0]);
            }
            res.status(404).json({ text: "El registro no existe!" });
        });
    }
    create(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                yield database_1.default.query('INSERT INTO registros set ?', [req.body]);
                res.json({ message: 'Regisro creado con exito!' });
            }
            catch (err) {
                res.status(404).json(err.message);
            }
        });
    }
    update(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const { id } = req.params;
            const regisroViejo = req.body;
            yield database_1.default.query('UPDATE registros set ? WHERE id_registro = ?', [req.body, id]);
            res.json({ message: 'Regisro ' + req.body.id_registro + ' se ha actualizado a ' + [id] + ' con exito!' });
        });
    }
    delete(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const { id } = req.params;
            const registros = yield database_1.default.query('DELETE FROM registros WHERE id_registro = ?', [id]);
            res.json({ message: 'El regisro ' + [id] + ' ha sido eliminado' });
        });
    }
    getCountPeople(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            const cuenta = yield database_1.default.query('SELECT COUNT(*) AS promedio FROM registros WHERE fecha_registro BETWEEN ? AND ?', [req.query.desde, req.query.hasta]);
            res.json(cuenta);
        });
    }
}
exports.registrosController = new RegistrosController();
