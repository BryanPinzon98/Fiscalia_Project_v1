package UIController.ProfileLayout;

import UIController.SignUpLayout.SignUpLayoutController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import resources.ManageLayout;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileLayoutController implements Initializable {

    private ManageLayout manageLayoutClass = ManageLayout.getInstance();

    @FXML
    private ImageView profileUserPicture;

    @FXML
    private Text profileUsername;

    @FXML
    private Text profileUserLastname;

    @FXML
    private Text profileTypeUser;

    @FXML
    private Text profileUserGenre;

    @FXML
    private Text profileUserAdress;

    @FXML
    private Text profileUserMaritalStatus;

    @FXML
    private Text profileUserRFC;

    @FXML
    private Text profileUserEmail;

    @FXML
    private Button btnEditUserProfile;

    @FXML
    public void editUserProfile(){
        FXMLLoader signUpFXMLLoader = manageLayoutClass.loadLayout("layout/SignUpLayout.fxml", "Editar Profile", false);
        SignUpLayoutController signUpLayoutController = signUpFXMLLoader.getController();
        Stage signUpStage = manageLayoutClass.getStage();

        signUpLayoutController.setSignUpTitle("Editar Perfil Usuario");
        signUpLayoutController.setSubmitButtonTitle("Aceptar");
        signUpLayoutController.setVisibleEnrollFingerprint(false);

        //------
        signUpLayoutController.setUserNames(profileUsername.getText());
        signUpLayoutController.setUserLastNames(profileUserLastname.getText());
        signUpLayoutController.setUserRFC(profileUserRFC.getText());
        signUpLayoutController.setUserGenre(profileUserGenre.getText());
        signUpLayoutController.setUserMaritalStatus(profileUserMaritalStatus.getText());
        signUpLayoutController.setUserAddress(profileUserAdress.getText());
        signUpLayoutController.setUserEmail(profileUserEmail.getText());
        signUpLayoutController.setTypeUser(profileTypeUser.getText());
        //------

        signUpStage.setTitle("Editar Perfil");
        signUpStage.getIcons().add(new Image("images/Fiscalia_Web_Logo.jpg"));
        signUpStage.setResizable(false);
        signUpStage.show();

    }

    public void setProfileUsername(String profileUsername) {
        this.profileUsername.setText(profileUsername);
    }

    public void setProfileUserLastname(String profileUserLastname) {
        this.profileUserLastname.setText(profileUserLastname);
    }

    public void setProfileTypeUser(String profileTypeUser) {
        this.profileTypeUser.setText(profileTypeUser);
    }

    public void setProfileUserPicture(ImageView profileUserPicture) {
        this.profileUserPicture = profileUserPicture;
    }

    public void setProfileUserGenre(String profileUserGenre) {
        this.profileUserGenre.setText(profileUserGenre);
    }

    public void setProfileUserAdress(String profileUserAdress) {
        this.profileUserAdress.setText(profileUserAdress);
    }

    public void setProfileUserMaritalStatus(String profileUserMaritalStatus) {
        this.profileUserMaritalStatus.setText(profileUserMaritalStatus);
    }

    public void setProfileUserRFC(String profileUserRFC) {
        this.profileUserRFC.setText(profileUserRFC);
    }

    public void setProfileUserEmail(String profileUserEmail) {
        this.profileUserEmail.setText(profileUserEmail);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
