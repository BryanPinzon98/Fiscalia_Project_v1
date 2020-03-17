import {Router} from 'express';
import {tiposUsuariosController} from '../controllers/tiposUsuariosController';

class TiposUsuariosRoutes {

    public router: Router = Router();

    constructor(){
        this.config();
    }

    config(): void {
        this.router.get('/', tiposUsuariosController.getTiposUsuarios);

    }
}

const tiposUsuariosRoutes = new TiposUsuariosRoutes();
export default tiposUsuariosRoutes.router;