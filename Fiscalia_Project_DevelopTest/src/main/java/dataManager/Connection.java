package dataManager;

import UIController.UserItemLayout.UsersListLogicController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import objects.Genre;
import objects.MaritalStatus;
import objects.TypeUser;
import objects.User;

import java.util.ArrayList;

public class Connection {

    private static Connection connection = null;
    private String DDBB_URI = "http://192.168.0.100:3000/api";

    public static Connection getInstance() {
        if (connection == null) {
            connection = new Connection();
        }

        return connection;
    }

    private Connection() {
    }

    private String APIRequest(String[] parameters, String URI) {

        if (parameters != null) {
            //Si tiene parámetros se definen dentro de la URI
            //URI += "parameters";
        }

        String jsonStringResponse = null;

        try {

            Client client = Client.create();
            WebResource webResource = client.resource(URI);

            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed: HTTP error code: " + response.getStatus());
            }

            jsonStringResponse = response.getEntity(String.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonStringResponse;
    }

    public void getUsers(String userNames, String userLastNames) {

        //Find ID user by Names or Last Names.

        String USERS_URI = DDBB_URI + "/usuarios/invitados";
        String jsonStringUsersResponse = APIRequest(null, USERS_URI);

        ArrayList<User> foundUsers = null;

        try {
            foundUsers = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(jsonStringUsersResponse, new TypeReference<ArrayList<User>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        UsersListLogicController usersListLogicController = UsersListLogicController.getInstance();
        usersListLogicController.setFoundUsers(foundUsers);
        usersListLogicController.loadRootContainer();
    }

    public ArrayList<String> getMaritalStatusOptions() {

        String MARITALSTATUS_URI = DDBB_URI + "/estadosciviles";
        String jsonStringMaritalResponse = APIRequest(null, MARITALSTATUS_URI);

        //System.out.println(jsonStringMaritalResponse);

        ArrayList<MaritalStatus> maritalObjects = null;

        try {
            maritalObjects = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(jsonStringMaritalResponse, new TypeReference<ArrayList<MaritalStatus>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ArrayList<String> maritalStringOptions = new ArrayList<>();

        for (MaritalStatus maritalObject : maritalObjects) {
            maritalStringOptions.add(maritalObject.getNombre_estado_civil());
        }

        return maritalStringOptions;
    }

    public ArrayList<String> getGenres() {

        String GENRES_URI = DDBB_URI + "/generos";
        String jsonStringGenresResponse = APIRequest(null, GENRES_URI);

        //System.out.println(jsonStringGenresResponse);

        ArrayList<Genre> genresObjects = null;

        try {
            genresObjects = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(jsonStringGenresResponse, new TypeReference<ArrayList<Genre>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ArrayList<String> genresStringOptions = new ArrayList<>();

        for (Genre genreObject : genresObjects) {
            genresStringOptions.add(genreObject.getNombre_genero());
        }

        return genresStringOptions;
    }

    public ArrayList<String> getTypeUsers() {

        String TYPEUSERS_URI = DDBB_URI + "/tiposusuarios";
        String jsonStringTypeUsersResponse = APIRequest(null, TYPEUSERS_URI);

        //System.out.println(jsonStringTypeUsersResponse);

        ArrayList<TypeUser> typeUsersObject = null;

        try {
            typeUsersObject = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(jsonStringTypeUsersResponse, new TypeReference<ArrayList<TypeUser>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ArrayList<String> typeUsersStringOptions = new ArrayList<>();

        for (TypeUser typeUser : typeUsersObject) {
            typeUsersStringOptions.add(typeUser.getNombre_tipos_usuario());
        }

        return typeUsersStringOptions;
    }
}
