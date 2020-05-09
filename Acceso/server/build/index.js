"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const morgan_1 = __importDefault(require("morgan"));
const cors_1 = __importDefault(require("cors"));
const indexRoutes_1 = __importDefault(require("./routes/indexRoutes"));
const usuariosRoutes_1 = __importDefault(require("./routes/usuariosRoutes"));
const registrosRoutes_1 = __importDefault(require("./routes/registrosRoutes"));
const estadosCivilesRoutes_1 = __importDefault(require("./routes/estadosCivilesRoutes"));
const tiposUsuariosRoutes_1 = __importDefault(require("./routes/tiposUsuariosRoutes"));
const generosRoutes_1 = __importDefault(require("./routes/generosRoutes"));
class Server {
    constructor() {
        this.app = express_1.default();
        this.config();
        this.routes();
    }
    config() {
        this.app.set('port', process.env.PORT || 3000);
        this.app.use(morgan_1.default('dev'));
        this.app.use(cors_1.default());
        this.app.use(express_1.default.json());
        this.app.use(express_1.default.urlencoded({ extended: false }));
    }
    routes() {
        this.app.use('/api/', indexRoutes_1.default);
        this.app.use('/api/usuarios', usuariosRoutes_1.default);
        this.app.use('/api/registros', registrosRoutes_1.default);
        this.app.use('/api/estadosciviles', estadosCivilesRoutes_1.default);
        this.app.use('/api/tiposusuarios', tiposUsuariosRoutes_1.default);
        this.app.use('/api/generos', generosRoutes_1.default);
    }
    start() {
        this.app.listen(this.app.get('port'), () => {
            console.log('server on port ', this.app.get('port'));
        });
    }
}
const server = new Server();
server.start();