"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = require("express");
const tiposUsuariosController_1 = require("../controllers/tiposUsuariosController");
class TiposUsuariosRoutes {
    constructor() {
        this.router = express_1.Router();
        this.config();
    }
    config() {
        this.router.get('/', tiposUsuariosController_1.tiposUsuariosController.getTiposUsuarios);
    }
}
const tiposUsuariosRoutes = new TiposUsuariosRoutes();
exports.default = tiposUsuariosRoutes.router;
