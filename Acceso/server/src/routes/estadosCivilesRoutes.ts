import {Router} from 'express';
import {estadosCivilesController} from '../controllers/estadosCivilesContoller';

class EstadosCivilesRoutes {

    public router: Router = Router();

    constructor(){
        this.config();
    }

    config(): void {
        this.router.get('/', estadosCivilesController.getCountNewPeople);

    }
}

const estadosCivilesRoutes = new EstadosCivilesRoutes();
export default estadosCivilesRoutes.router;