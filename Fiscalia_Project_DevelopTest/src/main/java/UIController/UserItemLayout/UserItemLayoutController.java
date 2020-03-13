package UIController.UserItemLayout;

import UIController.ProfileLayout.ProfileLayoutController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import resources.ManageLayout;

import java.net.URL;
import java.util.ResourceBundle;

public class UserItemLayoutController implements Initializable {

    private ManageLayout manageLayoutClass = ManageLayout.getInstance();

    private int userItemID;
    private String emailUserItem;

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


    public void setUserItemID(int userItemID) {
        this.userItemID = userItemID;
    }

    public void setProfilePhotoUserItem(ImageView profilePhotoUserItem) {
        this.profilePhotoUserItem = profilePhotoUserItem;
    }

    public void setNamesUserItem(String namesUserItem) {
        this.namesUserItem.setText(namesUserItem);
    }

    public void setLastNamesUserItem(String lastNamesUserItem) {
        this.lastNamesUserItem.setText(lastNamesUserItem);
    }

    public void setTypeUserItem(String typeUserItem) {
        this.typeUserItem.setText(typeUserItem);
    }

    public void setRFCUserItem(String RFCUserItem) {
        this.RFCUserItem.setText(RFCUserItem);
    }

    public void setAddressUserItem(String addressUserItem) {
        this.addressUserItem.setText(addressUserItem);
    }

    public void setEmailUserItem(String emailUserItem){
        this.emailUserItem = emailUserItem;
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

        profileLayoutController.setProfileUsername(namesUserItem.getText());
        profileLayoutController.setProfileUserLastname(lastNamesUserItem.getText());
        profileLayoutController.setProfileTypeUser(typeUserItem.getText());
        profileLayoutController.setProfileUserGenre("YY");
        profileLayoutController.setProfileUserAdress(addressUserItem.getText());
        profileLayoutController.setProfileUserMaritalStatus("XX");
        profileLayoutController.setProfileUserRFC(RFCUserItem.getText());
        profileLayoutController.setProfileUserEmail(emailUserItem);

        //manageLayoutClass.closeLayout((Stage) buttonPressed.getScene().getWindow());
    }

}
