import {Router} from 'express';
import {usuariosController} from '../controllers/usuariosController';

class UsuariosRoutes {

    public router: Router = Router();

    constructor(){
        this.config();
    }

    config(): void {
        this.router.get('/porfecha', usuariosController.getCountNewPeople);
        this.router.get('/invitados', usuariosController.listarInvitados);
        this.router.get('/proveedores', usuariosController.listarProveedores);
        this.router.get('/:id', usuariosController.getOne);
        this.router.put('/:id', usuariosController.update);
        this.router.post('/', usuariosController.create);
        this.router.post('/full', usuariosController.createFull);
        this.router.delete('/:id', usuariosController.delete);
    }
}

const usuariosRoutes = new UsuariosRoutes();
export default usuariosRoutes.router;