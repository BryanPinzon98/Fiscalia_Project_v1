package UIController.ProfileLayout;

import UIController.SignUpLayout.SignUpLayoutController;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import dataManager.Connection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import objects.User;
import resources.ManageLayout;
import verify.Verify;

import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

public class ProfileLayoutController implements Initializable {

    private ProfileLayoutController actualInstance = null;
    private Connection DDBBConnectionClass = Connection.getInstance();
    private ManageLayout manageLayoutClass = ManageLayout.getInstance();
    private User actualUser = null;
    private DPFPTemplate dpfpTemplate = null;

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
    private Button btnCompareFingerprint;

    @FXML
    public void compareFingerprint() {
        String base64Sample = "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";

        byte[] fingerprintArray = Base64.getDecoder().decode(base64Sample);

        DPFPVerification matcher = DPFPGlobal.getVerificationFactory().createVerification();
        dpfpTemplate = DPFPGlobal.getTemplateFactory().createTemplate();
        dpfpTemplate.deserialize(fingerprintArray);

        Verify verify = new Verify(actualInstance);
        verify.setVisible(true);
    }

    @FXML
    public void editUserProfile() {
        FXMLLoader signUpFXMLLoader = manageLayoutClass.loadLayout("layout/SignUpLayout.fxml", "Editar Profile", false);
        SignUpLayoutController signUpLayoutController = signUpFXMLLoader.getController();
        Stage signUpStage = manageLayoutClass.getStage();

        signUpLayoutController.setSignUpTitle("Editar Perfil Usuario");
        signUpLayoutController.setSubmitButtonTitle("Aceptar");
        signUpLayoutController.setVisibleEnrollFingerprint(false);
        signUpLayoutController.setActualUserEditMode(actualUser);
        signUpLayoutController.setUpLayoutEditMode();


        signUpStage.setTitle("Editar Perfil");
        signUpStage.getIcons().add(new Image("images/Fiscalia_Web_Logo.jpg"));
        signUpStage.setResizable(false);
        signUpStage.show();

    }


    public void setActualUser(User actualUser) {
        this.actualUser = actualUser;
    }

    public void setActualInstance(ProfileLayoutController actualInstance) {
        this.actualInstance = actualInstance;
    }

    public DPFPTemplate getDpfpTemplate() {
        return dpfpTemplate;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setUpLayout() {
        this.profileUserPicture.setImage(DDBBConnectionClass.decodeUserProfilePhoto(actualUser));
        this.profileUsername.setText(actualUser.getNombres_usuario());
        this.profileUserLastname.setText(actualUser.getApellidos_usuario());
        this.profileUserAdress.setText(actualUser.getDireccion_usuario());
        this.profileUserRFC.setText(actualUser.getRfc_usuario());
        this.profileUserEmail.setText(actualUser.getCorreo_usuario());

        this.profileTypeUser.setText(DDBBConnectionClass.getTypeUserString(actualUser.getId_tipo_usuario()));
        this.profileUserGenre.setText(DDBBConnectionClass.getStringGenre(actualUser.getId_genero()));
        this.profileUserMaritalStatus.setText(DDBBConnectionClass.getMaritalStatusString(actualUser.getId_estado_civil()));
    }
}
