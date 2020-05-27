import express, {Application} from 'express';
import morgan from 'morgan';
import cors from 'cors';

import indexRoutes from './routes/indexRoutes';
import usuariosRoutes from './routes/usuariosRoutes';
import registrosRoutes from './routes/registrosRoutes';
import estadosCivilesRoutes from './routes/estadosCivilesRoutes';
import tiposUsuariosRoutes from './routes/tiposUsuariosRoutes';
import usuariosFotoRoutes from './routes/usuariosFotoRoutes';
import usuarioHuellaRoutes from './routes/usuarioHuellaRoutes';
import generosRoutes from './routes/generosRoutes';

class Server {

    public app: Application;

    constructor(){
        this.app = express();
        this.config();
        this.routes();
    }

    config(): void{
        this.app.set('port',process.env.PORT || 3000);
        this.app.use(morgan('dev'));
        this.app.use(cors());
        this.app.use(express.json()); 
        this.app.use(express.urlencoded({extended: false}));

    }

    routes(): void{
        this.app.use('/api/',indexRoutes); 
        this.app.use('/api/usuarios',usuariosRoutes);
        this.app.use('/api/registros',registrosRoutes);
        this.app.use('/api/estadosciviles',estadosCivilesRoutes);
        this.app.use('/api/tiposusuarios',tiposUsuariosRoutes);
        this.app.use('/api/fotosusuarios',usuariosFotoRoutes);
        this.app.use('/api/huellasusuarios',usuarioHuellaRoutes);
        this.app.use('/api/generos',generosRoutes);
    }

    start(): void{
        this.app.listen(this.app.get('port'), () => {
            console.log('server on port ', this.app.get('port'));
        });
    }
}

const server = new Server(); 
server.start();