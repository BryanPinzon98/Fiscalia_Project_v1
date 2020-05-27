import {Router} from 'express';
import {usuariosFotoController} from '../controllers/usuariosFotoController';

class UsuariosFotoRoutes {

    public router: Router = Router();

    constructor(){
        this.config();
    }

    config(): void {
        this.router.get('/crud/:id', usuariosFotoController.getOne);
        this.router.get('/crud/', usuariosFotoController.getAll);
        this.router.put('/crud/:id', usuariosFotoController.update);
        this.router.delete('/crud/:id', usuariosFotoController.delete);
        this.router.post('/crud/', usuariosFotoController.create);
    }
}

const usuariosFotoRoutes = new UsuariosFotoRoutes();
export default usuariosFotoRoutes.router;