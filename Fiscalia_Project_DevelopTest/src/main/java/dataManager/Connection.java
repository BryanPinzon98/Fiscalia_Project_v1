package dataManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import objects.Genre;
import objects.MaritalStatus;
import objects.TypeUser;
import objects.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

public class Connection {

    private static Connection connection = null;
    private String DDBB_URI = "http://192.168.0.100:3000/api";

    private ArrayList<MaritalStatus> maritalStatusArrayOptions = null;
    private ArrayList<Genre> genreArrayOptions = null;
    private ArrayList<TypeUser> typeUserArrayOptions = null;

    public static Connection getInstance() {
        if (connection == null) {
            connection = new Connection();
        }

        return connection;
    }

    private Connection() {
    }

    public void POST_REQUEST(String user) {

        String POST_USER_URI = DDBB_URI + "/usuarios/createall/";

        Client client = Client.create();
        WebResource webResource = client.resource(POST_USER_URI);
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, user);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed: HTTP error code : " + response.getStatus());
        }

        System.out.println("Server response... \n");
        String output = response.getEntity(String.class);
        System.out.println(output);
    }

    public void UPDATE_REQUEST(String userJSON, int userID) {

        String UPDATE_USER_URI = DDBB_URI + "/usuarios/crud/" + userID;

        Client client = Client.create();
        WebResource webResource = client.resource(UPDATE_USER_URI);
        ClientResponse response = webResource.type("application/json").put(ClientResponse.class, userJSON);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed: HTTP error code : " + response.getStatus());
        }
/*
        System.out.println("Server response... \n");
        String output = response.getEntity(String.class);
        System.out.println(output);

 */
    }

    public void POST_PHOTO_USER(String photoJSON) {

        String POST_PHOTO_URI = DDBB_URI + "/fotosusuarios/crud/";

        Client client = Client.create();
        WebResource webResource = client.resource(POST_PHOTO_URI);
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, photoJSON);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed: HTTP error code: " + response.getStatus());
        }
/*
        System.out.println("Server response... \n");
        String output = response.getEntity(String.class);
        System.out.println(output);
 */
    }

    public void POST_NEW_FINGERPRINT(String newFingerprintJSON) {

        String POST_NEW_FINGERPRINT_URI = DDBB_URI + "/huellasusuarios/crud/";
        String prueba = "{\"archivo_huella\": \"" + "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" + "\", \"id_usuario\"" + ": " + 126 + "}";
        //System.out.println(prueba);


        Client client = Client.create();
        WebResource webResource = client.resource(POST_NEW_FINGERPRINT_URI);
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, prueba);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed: HTTP error code: " + response.getStatus());
        }

        System.out.println("Server response... \n");
        String output = response.getEntity(String.class);
        System.out.println(output);
    }

    public ArrayList<User> getUsers(String userNames, String userLastNames) {


        String jsonStringUsersResponse = null;
        String USERS_BY_NAME_URI = DDBB_URI + "/usuarios/buscarpornombre?";


        try {

            Client client = Client.create();
            WebResource webResource = client.resource(USERS_BY_NAME_URI);
            ClientResponse response = webResource.queryParam("nombre", userNames).queryParam("apellido", userLastNames).get(ClientResponse.class);


            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed: HTTP error code: " + response.getStatus());
            }

            jsonStringUsersResponse = response.getEntity(String.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<User> foundUsers = null;

        try {
            foundUsers = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(jsonStringUsersResponse, new TypeReference<ArrayList<User>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return foundUsers;
    }

    //Get initial data to new user form.
    private String GET_REQUEST(String URI) {

        String jsonStringResponse = null;

        try {

            Client client = Client.create();
            WebResource webResource = client.resource(URI);
            ClientResponse response = null;

            try {
                response = webResource.accept("application/json").get(ClientResponse.class);
            } catch (ClientHandlerException e) {
                throw new ClientHandlerException("No se pudo comunicar con la API.");
            }

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed: HTTP error code: " + response.getStatus());
            }

            jsonStringResponse = response.getEntity(String.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonStringResponse;
    }

    public void loadMaritalStatusChoiceBoxOptions() {

        String MARITALSTATUS_URI = DDBB_URI + "/estadosciviles";
        String jsonStringMaritalResponse = GET_REQUEST(MARITALSTATUS_URI);

        if (jsonStringMaritalResponse != null) {
            try {
                maritalStatusArrayOptions = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(jsonStringMaritalResponse, new TypeReference<ArrayList<MaritalStatus>>() {
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No se han podido obtener los datos de Estados Civiles desde la API.");
        }
    }

    public void loadGenreChoiceBoxOptions() {

        String GENRES_URI = DDBB_URI + "/generos";
        String jsonStringGenresResponse = GET_REQUEST(GENRES_URI);

        if (jsonStringGenresResponse != null) {
            try {
                genreArrayOptions = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(jsonStringGenresResponse, new TypeReference<ArrayList<Genre>>() {
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No se han podido obtener los datos de Generos desde la API.");
        }
    }

    public void loadTypeUserChoiceBoxOptions() {

        String TYPEUSERS_URI = DDBB_URI + "/tiposusuarios";
        String jsonStringTypeUsersResponse = GET_REQUEST(TYPEUSERS_URI);

        //System.out.println(jsonStringTypeUsersResponse);

        if (jsonStringTypeUsersResponse != null) {
            try {
                typeUserArrayOptions = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(jsonStringTypeUsersResponse, new TypeReference<ArrayList<TypeUser>>() {
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No se han podido obtener los datos de Tipos de Usuario desde la API.");
        }
    }

    public User getOneUser(int userID) {

        String GET_ONE_USER = DDBB_URI + "/usuarios/crud/" + userID;
        String jsonUserResponse = GET_REQUEST(GET_ONE_USER);

        User foundUser = null;

        try {
            foundUser = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(jsonUserResponse, User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return foundUser;
    }

    public String getTypeUserString(int idTipoUsuario) {

        String maritalStatus = null;

        for (TypeUser typeUserObject : typeUserArrayOptions) {
            if (idTipoUsuario == typeUserObject.getId_tipos_usuario()) {
                maritalStatus = typeUserObject.getNombre_tipos_usuario();
            }
        }

        return maritalStatus;
    }

    public String getStringGenre(int idGenre) {

        String genre = null;

        for (Genre genreObject : genreArrayOptions) {
            if (idGenre == genreObject.getId_genero()) {
                genre = genreObject.getNombre_genero();
            }
        }

        return genre;
    }

    public String getMaritalStatusString(int idMaritalStatus) {

        String maritalStatus = null;

        for (MaritalStatus maritalStatusObject : maritalStatusArrayOptions) {
            if (idMaritalStatus == maritalStatusObject.getId_estado_civil()) {
                maritalStatus = maritalStatusObject.getNombre_estado_civil();
            }
        }

        return maritalStatus;
    }

    public Image decodeUserProfilePhoto(User usuario) {

        BufferedImage bufferedPhoto = null;
        Image userPhoto = null;
        String stringBase64UserPhoto = usuario.getArchivo_foto();

        byte[] photoByteArray = Base64.getDecoder().decode(stringBase64UserPhoto);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(photoByteArray);

        try {
            bufferedPhoto = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        userPhoto = SwingFXUtils.toFXImage(bufferedPhoto, null);

        return userPhoto;
    }

    // Getter y Setter


    public ArrayList<MaritalStatus> getMaritalStatusArrayOptions() {
        return maritalStatusArrayOptions;
    }

    public ArrayList<Genre> getGenreArrayOptions() {
        return genreArrayOptions;
    }

    public ArrayList<TypeUser> getTypeUserArrayOptions() {
        return typeUserArrayOptions;
    }
}
