package gov.fged.java.UIController.ProfileLayout;

import gov.fged.java.resources.ManageLayout;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserSearchInputController implements Initializable {

    private ManageLayout loadLayoutClass = ManageLayout.getInstance();

    @FXML
    private Button btnSearchDataUser;

    @FXML
    private Button btnCancelSearchDataUser;

    @FXML
    private void handleButtonClicks(MouseEvent mouseEvent){

        Button buttonPressed = (Button) mouseEvent.getSource();

        switch(buttonPressed.getId()){

            case "btnSearchDataUser":
                System.out.println("Botón de búsqueda presionado");
                loadLayoutClass.loadLayout("gov/fged/res/layout/ProfileLayout.fxml", "Perfil");
                loadLayoutClass.closeLayout((Stage) buttonPressed.getScene().getWindow());
                break;

            case "btnCancelSearchDataUser":
                System.out.println("Botón de cancelar presionado");
                loadLayoutClass.closeLayout((Stage) buttonPressed.getScene().getWindow());
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
