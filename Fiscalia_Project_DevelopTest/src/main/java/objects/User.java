package objects;

public class User {

    private int id_usuario;
    private String rfc_usuario;
    private String nombres_usuario;
    private String apellidos_usuario;
    private String direccion_usuario;
    private String correo_usuario;
    private String clave_usuario;
    private int id_genero;
    private int id_estado_civil;
    private int id_tipo_usuario;
    private String archivo_huella;
    private String imagen_huella;
    private String archivo_foto;


    public User() {
    }

    public User(int id_usuario, String rfc_usuario, String nombres_usuario, String apellidos_usuario, String direccion_usuario, String correo_usuario, String clave_usuario, int id_genero, int id_estado_civil, int id_tipo_usuario, String archivo_huella, String archivo_foto) {
        this.id_usuario = id_usuario;
        this.rfc_usuario = rfc_usuario;
        this.nombres_usuario = nombres_usuario;
        this.apellidos_usuario = apellidos_usuario;
        this.direccion_usuario = direccion_usuario;
        this.correo_usuario = correo_usuario;
        this.clave_usuario = clave_usuario;
        this.id_genero = id_genero;
        this.id_estado_civil = id_estado_civil;
        this.id_tipo_usuario = id_tipo_usuario;
        this.archivo_huella = archivo_huella;
        //this.imagen_huella = imagen_huella;
        this.archivo_foto = archivo_foto;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getRfc_usuario() {
        return rfc_usuario;
    }

    public void setRfc_usuario(String rfc_usuario) {
        this.rfc_usuario = rfc_usuario;
    }

    public String getNombres_usuario() {
        return nombres_usuario;
    }

    public void setNombres_usuario(String nombres_usuario) {
        this.nombres_usuario = nombres_usuario;
    }

    public String getApellidos_usuario() {
        return apellidos_usuario;
    }

    public void setApellidos_usuario(String apellidos_usuario) {
        this.apellidos_usuario = apellidos_usuario;
    }

    public String getDireccion_usuario() {
        return direccion_usuario;
    }

    public void setDireccion_usuario(String direccion_usuario) {
        this.direccion_usuario = direccion_usuario;
    }

    public String getCorreo_usuario() {
        return correo_usuario;
    }

    public void setCorreo_usuario(String correo_usuario) {
        this.correo_usuario = correo_usuario;
    }

    public String getClave_usuario() {
        return clave_usuario;
    }

    public void setClave_usuario(String clave_usuario) {
        this.clave_usuario = clave_usuario;
    }

    public int getId_genero() {
        return id_genero;
    }

    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }

    public int getId_estado_civil() {
        return id_estado_civil;
    }

    public void setId_estado_civil(int id_estado_civil) {
        this.id_estado_civil = id_estado_civil;
    }

    public int getId_tipo_usuario() {
        return id_tipo_usuario;
    }

    public void setId_tipo_usuario(int id_tipo_usuario) {
        this.id_tipo_usuario = id_tipo_usuario;
    }

    public String getArchivo_huella() {
        return archivo_huella;
    }

    public void setArchivo_huella(String archivo_huella) {
        this.archivo_huella = archivo_huella;
    }

    public String getImagen_huella() {
        return imagen_huella;
    }

    public void setImagen_huella(String imagen_huella) {
        this.imagen_huella = imagen_huella;
    }

    public String getArchivo_foto() {
        return archivo_foto;
    }

    public void setArchivo_foto(String archivo_foto) {
        this.archivo_foto = archivo_foto;
    }

    @Override
    public String toString() {
        return "User{" +
                "id_usuario=" + id_usuario +
                ", rfc_usuario='" + rfc_usuario + '\'' +
                ", nombres_usuario='" + nombres_usuario + '\'' +
                ", apellidos_usuario='" + apellidos_usuario + '\'' +
                ", direccion_usuario='" + direccion_usuario + '\'' +
                ", correo_usuario='" + correo_usuario + '\'' +
                ", clave_usuario='" + clave_usuario + '\'' +
                ", id_genero=" + id_genero +
                ", id_estado_civil=" + id_estado_civil +
                ", id_tipo_usuario=" + id_tipo_usuario +
                ", archivo_huella='" + archivo_huella + '\'' +
                ", archivo_foto='" + archivo_foto + '\'' +
                '}';
    }
}