"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = require("express");
const usuariosFotoController_1 = require("../controllers/usuariosFotoController");
class UsuariosFotoRoutes {
    constructor() {
        this.router = express_1.Router();
        this.config();
    }
    config() {
        this.router.get('/crud/:id', usuariosFotoController_1.usuariosFotoController.getOne);
        this.router.get('/crud/', usuariosFotoController_1.usuariosFotoController.getAll);
        this.router.put('/crud/:id', usuariosFotoController_1.usuariosFotoController.update);
        this.router.delete('/crud/:id', usuariosFotoController_1.usuariosFotoController.delete);
        this.router.post('/crud/', usuariosFotoController_1.usuariosFotoController.create);
    }
}
const usuariosFotoRoutes = new UsuariosFotoRoutes();
exports.default = usuariosFotoRoutes.router;
