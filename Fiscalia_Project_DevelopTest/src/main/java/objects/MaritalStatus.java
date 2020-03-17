package objects;

public class MaritalStatus {

    private int id_estado_civil;
    private String nombre_estado_civil;

    public MaritalStatus() {
    }

    public MaritalStatus(int id_estado_civil, String nombre_estado_civil) {
        this.id_estado_civil = id_estado_civil;
        this.nombre_estado_civil = nombre_estado_civil;
    }

    public int getId_estado_civil() {
        return id_estado_civil;
    }

    public void setId_estado_civil(int id_estado_civil) {
        this.id_estado_civil = id_estado_civil;
    }

    public String getNombre_estado_civil() {
        return nombre_estado_civil;
    }

    public void setNombre_estado_civil(String nombre_estado_civil) {
        this.nombre_estado_civil = nombre_estado_civil;
    }
}
