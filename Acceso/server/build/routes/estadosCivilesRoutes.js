"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = require("express");
const estadosCivilesContoller_1 = require("../controllers/estadosCivilesContoller");
class EstadosCivilesRoutes {
    constructor() {
        this.router = express_1.Router();
        this.config();
    }
    config() {
        this.router.get('/', estadosCivilesContoller_1.estadosCivilesController.getCountNewPeople);
    }
}
const estadosCivilesRoutes = new EstadosCivilesRoutes();
exports.default = estadosCivilesRoutes.router;
