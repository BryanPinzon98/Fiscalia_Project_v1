export interface User{
    id?: number;
    rfc?: string;
    nombres: string;
    apellidos: string;
    direccion?: string;
    correo?: string;
    contraseña?: string;
    nombre_genero: string;
    nombre_estado_civil: string;
    nombre_tipo_usuario: string;
}