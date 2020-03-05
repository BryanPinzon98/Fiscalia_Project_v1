"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = require("express");
const registrosController_1 = require("../controllers/registrosController");
class RegistrosRoutes {
    constructor() {
        this.router = express_1.Router();
        this.config();
    }
    config() {
        this.router.get('/porfecha', registrosController_1.registrosController.getCountPeople);
        this.router.get('/', registrosController_1.registrosController.list);
        this.router.get('/:id', registrosController_1.registrosController.getOne);
        this.router.put('/:id', registrosController_1.registrosController.update);
        this.router.post('/', registrosController_1.registrosController.create);
        this.router.delete('/:id', registrosController_1.registrosController.delete);
    }
}
const registrosRoutes = new RegistrosRoutes();
exports.default = registrosRoutes.router;
