import {Router} from 'express';
import {generosController} from '../controllers/generosConroller';

class GenerosRoutes {

    public router: Router = Router();

    constructor(){
        this.config();
    }

    config(): void {
        this.router.get('/', generosController.getGeneros);

    }
}

const generosRoutes = new GenerosRoutes();
export default generosRoutes.router;