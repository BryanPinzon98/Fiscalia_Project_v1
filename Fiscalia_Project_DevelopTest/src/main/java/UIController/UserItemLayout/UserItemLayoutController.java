package UIController.UserItemLayout;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import objects.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserItemLayoutController implements Initializable {

    private ArrayList<User> foundUsers = null;

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

    public void loadFXML() {


        for (User usuario : foundUsers) {
            namesUserItem.setText(usuario.getNombres_usuario());
            lastNamesUserItem.setText(usuario.getApellidos_usuario());
            typeUserItem.setText(usuario.getNombre_tipos_usuario());
            RFCUserItem.setText(usuario.getRfc_usuario());
            addressUserItem.setText(usuario.getDireccion_usuario());

            //UsersRootContainerLayoutController userItemContainerClass = UsersRootContainerLayoutController.getInstance();
            //userItemContainerClass.paintUserItem();
        }
    }

    public void setUsersArray(ArrayList<User> foundUsers) {
        this.foundUsers = foundUsers;
        loadFXML();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}
}
