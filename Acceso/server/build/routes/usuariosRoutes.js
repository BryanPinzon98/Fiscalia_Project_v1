"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = require("express");
const usuariosController_1 = require("../controllers/usuariosController");
class UsuariosRoutes {
    constructor() {
        this.router = express_1.Router();
        this.config();
    }
    config() {
        this.router.get('/crud/', usuariosController_1.usuariosController.getAll);
        this.router.post('/crud/', usuariosController_1.usuariosController.create);
        this.router.get('/crud/:id', usuariosController_1.usuariosController.getOne);
        this.router.put('/crud/:id', usuariosController_1.usuariosController.update);
        this.router.delete('/crud/:id', usuariosController_1.usuariosController.delete);
        this.router.post('/createall', usuariosController_1.usuariosController.createAll);
        this.router.get('/invitados', usuariosController_1.usuariosController.listarInvitados);
        this.router.get('/cantidadnuevos', usuariosController_1.usuariosController.getCountNewPeople);
        this.router.get('/proveedores', usuariosController_1.usuariosController.listarProveedores);
        this.router.get('/buscarpornombre', usuariosController_1.usuariosController.getSearchByName);
        this.router.get('/prueba/', usuariosController_1.usuariosController.prueba);
    }
}
const usuariosRoutes = new UsuariosRoutes();
exports.default = usuariosRoutes.router;
