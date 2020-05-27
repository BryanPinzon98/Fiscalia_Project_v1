"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = require("express");
const usuarioHuellaController_1 = require("../controllers/usuarioHuellaController");
class UsuarioHuellaRoutes {
    constructor() {
        this.router = express_1.Router();
        this.config();
    }
    config() {
        this.router.get('/crud/:id', usuarioHuellaController_1.usuarioHuellaController.getOne);
        this.router.get('/crud/', usuarioHuellaController_1.usuarioHuellaController.getAll);
        this.router.put('/crud/:id', usuarioHuellaController_1.usuarioHuellaController.update);
        this.router.delete('/crud/:id', usuarioHuellaController_1.usuarioHuellaController.delete);
        this.router.post('/crud/', usuarioHuellaController_1.usuarioHuellaController.create);
    }
}
const usuarioHuellaRoutes = new UsuarioHuellaRoutes();
exports.default = usuarioHuellaRoutes.router;
