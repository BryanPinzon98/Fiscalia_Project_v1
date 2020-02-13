import {Request, Response} from 'express';

class IndexController{

    public index (req: Request, res: Response){
        res.send('holaputo')
    }
}
export const indexController = new IndexController();