package gov.fged.java.objects;

public class User {

    private String names;
    private String lastNames;
    private String RFC;
    private String genre;
    private String maritalStatus;
    private String address;
    private String email;
    private String typeUser;

    public User(String names, String lastNames, String RFC, String genre, String maritalStatus, String address, String email, String typeUser) {
        this.names = names;
        this.lastNames = lastNames;
        this.RFC = RFC;
        this.genre = genre;
        this.maritalStatus = maritalStatus;
        this.address = address;
        this.email = email;
        this.typeUser = typeUser;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }
}
