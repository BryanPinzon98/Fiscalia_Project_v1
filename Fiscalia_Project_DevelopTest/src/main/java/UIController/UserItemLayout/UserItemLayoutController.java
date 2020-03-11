package UIController.UserItemLayout;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class UserItemLayoutController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void userItemAnchorPaneHandler() {
        System.out.println(namesUserItem.getText());
    }

}
