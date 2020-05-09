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
        this.router.get('/porfecha', usuariosController_1.usuariosController.getCountNewPeople);
        this.router.get('/invitados', usuariosController_1.usuariosController.listarInvitados);
        this.router.get('/proveedores', usuariosController_1.usuariosController.listarProveedores);
        this.router.get('/:id', usuariosController_1.usuariosController.getOne);
        this.router.put('/:id', usuariosController_1.usuariosController.update);
        this.router.post('/', usuariosController_1.usuariosController.create);
        this.router.post('/full', usuariosController_1.usuariosController.createFull);
        this.router.delete('/:id', usuariosController_1.usuariosController.delete);
    }
}
const usuariosRoutes = new UsuariosRoutes();
exports.default = usuariosRoutes.router;
