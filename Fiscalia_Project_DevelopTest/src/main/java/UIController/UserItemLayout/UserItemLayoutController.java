package UIController.UserItemLayout;

import UIController.ProfileLayout.ProfileLayoutController;
import dataManager.Connection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import objects.User;
import resources.ManageLayout;

import java.net.URL;
import java.util.ResourceBundle;

public class UserItemLayoutController implements Initializable {

    private ManageLayout manageLayoutClass = ManageLayout.getInstance();
    private User actualUser = null;
    private Connection DDBBConnectionClass = Connection.getInstance();
    private Stage userListRootContainer = null;


    @FXML
    private AnchorPane userItemAnchorPane;

    @FXML
    private ImageView profilePhotoUserItem;

    @FXML
    private Text namesUserItem;

    @FXML
    private Text lastNamesUserItem;

    @FXML
    private Text typeUserItem;

    @FXML
    private Text RFCUserItem;

    @FXML
    private Text addressUserItem;

    public void setUpLayout() {

        if (actualUser.getArchivo_foto() != null) {
            this.profilePhotoUserItem.setImage(DDBBConnectionClass.decodeUserProfilePhoto(actualUser));
        }

        if (actualUser.getNombres_usuario() != null) {
            this.namesUserItem.setText(actualUser.getNombres_usuario());
        } else {
            this.namesUserItem.setText("Sin información");
        }

        if (actualUser.getApellidos_usuario() != null) {
            this.lastNamesUserItem.setText(actualUser.getApellidos_usuario());
        } else {
            this.lastNamesUserItem.setText("Sin información");
        }

        if (actualUser.getRfc_usuario() != null) {
            this.RFCUserItem.setText(actualUser.getRfc_usuario());
        } else {
            this.RFCUserItem.setText("Sin información");
        }

        if (actualUser.getDireccion_usuario() != null) {
            this.addressUserItem.setText(actualUser.getDireccion_usuario());
        } else {
            this.addressUserItem.setText("Sin información");
        }

        if (!DDBBConnectionClass.getTypeUserString(actualUser.getId_tipo_usuario()).isEmpty()) {
            this.typeUserItem.setText(DDBBConnectionClass.getTypeUserString(actualUser.getId_tipo_usuario()));
        } else {
            this.typeUserItem.setText("Sin información");
        }
    }

    public void setActualUser(User actualUser) {
        this.actualUser = actualUser;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    @FXML
    public void userItemAnchorPaneHandler() {

        //Aquí tengo que enviar el ID del usuario que el guardia seleccionó para que Juan Diego me regrese todos los datos del men.

        System.out.println(namesUserItem.getText());
        FXMLLoader fxmlLoaderProfileLayout = manageLayoutClass.loadLayout("layout/ProfileLayout.fxml", "Perfil", true);
        ProfileLayoutController profileLayoutController = fxmlLoaderProfileLayout.getController();

        profileLayoutController.setActualUser(actualUser);
        profileLayoutController.setActualInstance(profileLayoutController);


        //Envia los datos a la ventana de perfil
        profileLayoutController.setUpLayout();
        userListRootContainer.close();

        //manageLayoutClass.closeLayout((Stage) buttonPressed.getScene().getWindow());
    }

    public void setUserListRootContainer(Stage userListRootContainer) {
        this.userListRootContainer = userListRootContainer;
    }
}
