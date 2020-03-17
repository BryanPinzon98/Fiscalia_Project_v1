package objects;

public class TypeUser {

    private int id_tipos_usuario;
    private String nombre_tipos_usuario;

    public TypeUser() {
    }

    public TypeUser(int id_tipos_usuario, String nombre_tipos_usuario) {
        this.id_tipos_usuario = id_tipos_usuario;
        this.nombre_tipos_usuario = nombre_tipos_usuario;
    }

    public int getId_tipos_usuario() {
        return id_tipos_usuario;
    }

    public void setId_tipos_usuario(int id_tipos_usuario) {
        this.id_tipos_usuario = id_tipos_usuario;
    }

    public String getNombre_tipos_usuario() {
        return nombre_tipos_usuario;
    }

    public void setNombre_tipos_usuario(String nombre_tipos_usuario) {
        this.nombre_tipos_usuario = nombre_tipos_usuario;
    }
}
