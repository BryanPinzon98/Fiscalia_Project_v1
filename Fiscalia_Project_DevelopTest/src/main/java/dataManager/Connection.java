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

    private String GET_REQUEST(String URI) {

        String jsonStringResponse = null;

        try {

            Client client = Client.create();
            WebResource webResource = client.resource(URI);
            ClientResponse response = null;

            try{
                response = webResource.accept("application/json").get(ClientResponse.class);
            } catch (ClientHandlerException e) {
                throw new ClientHandlerException("No se pudo comunicar con la API");
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

    public ArrayList<User> getUsers(String userNames, String userLastNames) {


        String jsonStringUsersResponse = null;
        String USERS_BY_NAME_URI = DDBB_URI + "/usuarios/buscarpornombre?";


        try {

            Client client = Client.create();
            WebResource webResource = client.resource(USERS_BY_NAME_URI);
            //ClientResponse response = webResource.queryParam("nombre", URLEncoder.encode(userNames, StandardCharsets.UTF_8.toString())).queryParam("apellido", URLEncoder.encode(userLastNames, StandardCharsets.UTF_8.toString())).type("charset=utf-8").get(ClientResponse.class);
            ClientResponse response = webResource.queryParam("nombre", userNames).queryParam("apellido", userLastNames).get(ClientResponse.class);


            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed: HTTP error code: " + response.getStatus());
            }

            jsonStringUsersResponse = response.getEntity(String.class);
            System.out.println(jsonStringUsersResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(jsonStringUsersResponse);


        ArrayList<User> foundUsers = null;

        try {
            foundUsers = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(jsonStringUsersResponse, new TypeReference<ArrayList<User>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return foundUsers;
    }

    public void loadMaritalStatusChoiceBoxOptions() {

        String MARITALSTATUS_URI = DDBB_URI + "/estadosciviles";
        String jsonStringMaritalResponse = GET_REQUEST(MARITALSTATUS_URI);

        //System.out.println(jsonStringMaritalResponse);

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

        //System.out.println(jsonStringGenresResponse);

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
