package dataManager;

import UIController.UserItemLayout.UserItemLayoutController;
import UIController.UsersRootContainerLayout.UsersRootContainerLayoutController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import objects.User;
import resources.ManageLayout;

import java.util.ArrayList;

public class Connection {

    private static Connection connection = null;
    private final String DDBB_URI = "http://10.11.34.194:3000/api/usuarios";
    private ArrayList<User> foundUsers = null;
    private ManageLayout manageLayoutClass = ManageLayout.getInstance();


    public static Connection getInstance() {
        if (connection == null) {
            connection = new Connection();
        }

        return connection;
    }

    private Connection() {
    }

    public void getUsers(String userNames, String userLastNames) {

        //Find ID user by Names or Last Names.

        try {
            Client client = Client.create();
            WebResource webResource = client.resource(DDBB_URI);

            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed: HTTP error code : " + response.getStatus());
            }

            String jsonString = response.getEntity(String.class);

            foundUsers = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(jsonString, new TypeReference<ArrayList<User>>() {
            });

            FXMLLoader fxmlLoaderRootContainer = manageLayoutClass.loadLayout("layout/UsersRootContainer.fxml", "Item", false);
            UsersRootContainerLayoutController userRootContainerClass = fxmlLoaderRootContainer.getController();
            Stage containerStage = manageLayoutClass.getStage();


            for (User usuario : foundUsers) {

                FXMLLoader fxmlLoaderItem = manageLayoutClass.loadLayout("layout/UserItemLayout.fxml", "Item", false);
                UserItemLayoutController userItemClass = fxmlLoaderItem.getController();

                //profilePhotoUserItem.setImage(usuario.);
                userItemClass.setNamesUserItem(usuario.getNombres_usuario());
                userItemClass.setLastNamesUserItem(usuario.getApellidos_usuario());
                userItemClass.setTypeUserItem(usuario.getNombre_tipos_usuario());
                userItemClass.setRFCUserItem(usuario.getRfc_usuario());
                userItemClass.setAddressUserItem(usuario.getDireccion_usuario());

                userRootContainerClass.setItemPane(manageLayoutClass.getPane());
                userRootContainerClass.paintUserItem();
            }

            containerStage.setTitle("RootLayout");
            containerStage.getIcons().add(new Image("images/Fiscalia_Web_Logo.jpg"));
            containerStage.setResizable(true);
            containerStage.show();

            //-------------------------------------------------------------
            System.out.println("Output from server .... \n");
            System.out.println(jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
