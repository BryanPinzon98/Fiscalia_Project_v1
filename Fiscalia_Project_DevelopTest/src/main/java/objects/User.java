package objects;

import com.digitalpersona.onetouch.DPFPTemplate;

import java.awt.*;

public class User {

    private int id_usuario;
    private String rfc_usuario;
    private String nombres_usuario;
    private String apellidos_usuario;
    private String direccion_usuario;
    private String correo_usuario;
    private String clave_usuario;
    private String nombre_genero;
    private String nombre_estado_civil;
    private String nombre_tipos_usuario;

    private DPFPTemplate archivo_huella;
    private Image imagen_huella;
    private Image archivo_foto;


    public User() {
    }

    public User(int id_usuario, String rfc_usuario, String nombres_usuario, String apellidos_usuario, String direccion_usuario, String correo_usuario, String clave_usuario, String nombre_genero, String nombre_estado_civil, String nombre_tipos_usuario) {
        this.id_usuario = id_usuario;
        this.rfc_usuario = rfc_usuario;
        this.nombres_usuario = nombres_usuario;
        this.apellidos_usuario = apellidos_usuario;
        this.direccion_usuario = direccion_usuario;
        this.correo_usuario = correo_usuario;
        this.clave_usuario = clave_usuario;
        this.nombre_genero = nombre_genero;
        this.nombre_estado_civil = nombre_estado_civil;
        this.nombre_tipos_usuario = nombre_tipos_usuario;
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

    public String getNombre_genero() {
        return nombre_genero;
    }

    public void setNombre_genero(String nombre_genero) {
        this.nombre_genero = nombre_genero;
    }

    public String getNombre_estado_civil() {
        return nombre_estado_civil;
    }

    public void setNombre_estado_civil(String nombre_estado_civil) {
        this.nombre_estado_civil = nombre_estado_civil;
    }

    public String getNombre_tipos_usuario() {
        return nombre_tipos_usuario;
    }

    public void setNombre_tipos_usuario(String nombre_tipos_usuario) {
        this.nombre_tipos_usuario = nombre_tipos_usuario;
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
                ", nombre_genero='" + nombre_genero + '\'' +
                ", nombre_estado_civil='" + nombre_estado_civil + '\'' +
                ", nombre_tipos_usuario='" + nombre_tipos_usuario + '\'' +
                '}';
    }
}