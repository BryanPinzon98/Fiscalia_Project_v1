"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = require("express");
const generosConroller_1 = require("../controllers/generosConroller");
class GenerosRoutes {
    constructor() {
        this.router = express_1.Router();
        this.config();
    }
    config() {
        this.router.get('/', generosConroller_1.generosController.getGeneros);
    }
}
const generosRoutes = new GenerosRoutes();
exports.default = generosRoutes.router;
