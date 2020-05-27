import {Router} from 'express';
import {usuarioHuellaController} from '../controllers/usuarioHuellaController';

class UsuarioHuellaRoutes {

    public router: Router = Router();

    constructor(){
        this.config();
    }

    config(): void {
        this.router.get('/crud/:id', usuarioHuellaController.getOne);
        this.router.get('/crud/', usuarioHuellaController.getAll);
        this.router.put('/crud/:id', usuarioHuellaController.update);
        this.router.delete('/crud/:id', usuarioHuellaController.delete);
        this.router.post('/crud/', usuarioHuellaController.create);
    }
}

const usuarioHuellaRoutes = new UsuarioHuellaRoutes();
export default usuarioHuellaRoutes.router;