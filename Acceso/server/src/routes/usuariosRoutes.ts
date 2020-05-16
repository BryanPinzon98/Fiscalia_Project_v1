import {Router} from 'express';
import {usuariosController} from '../controllers/usuariosController';

class UsuariosRoutes {

    public router: Router = Router();

    constructor(){
        this.config();
    }

    config(): void {       
        this.router.get('/crud/', usuariosController.getAll);
        this.router.get('/prueba/', usuariosController.prueba);
        this.router.post('/crud/', usuariosController.create);
        this.router.get('/crud/:id', usuariosController.getOne);
        this.router.put('/crud/:id', usuariosController.update);
        this.router.delete('/crud/:id', usuariosController.delete);
        this.router.post('/createall', usuariosController.createAll);
        this.router.get('/invitados', usuariosController.listarInvitados);
        this.router.get('/cantidadnuevos', usuariosController.getCountNewPeople);
        this.router.get('/proveedores', usuariosController.listarProveedores);
 
    }
}

const usuariosRoutes = new UsuariosRoutes();
export default usuariosRoutes.router;