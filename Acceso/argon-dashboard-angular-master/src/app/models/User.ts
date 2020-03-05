export interface User{
    id_usuario?: number;
    rfc_usuario?: string;
    nombres_usuario: string;
    apellidos_usuario: string;
    direccion_usuario?: string;
    correo_usuario?: string;
    contraseña_usuario?: string;
    nombre_genero: string;
    nombre_estado_civil: string;
    nombre_tipo_usuario: string;
    fecha_registro: string
}