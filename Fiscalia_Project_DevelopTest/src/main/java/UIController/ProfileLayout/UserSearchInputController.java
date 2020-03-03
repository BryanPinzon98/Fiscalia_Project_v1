package UIController.ProfileLayout;

import resources.ManageLayout;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class UserSearchInputController implements Initializable {

    private ManageLayout manageLayoutClass = ManageLayout.getInstance();

    @FXML
    private void handleButtonClicks(MouseEvent mouseEvent){

        Button buttonPressed = (Button) mouseEvent.getSource();

        switch(buttonPressed.getId()){

            case "btnSearchDataUser":
                System.out.println("Botón de búsqueda presionado");
                manageLayoutClass.loadLayout("layout/ProfileLayout.fxml", "Perfil");
                manageLayoutClass.closeLayout((Stage) buttonPressed.getScene().getWindow());
                break;

            case "btnCancelSearchDataUser":
                System.out.println("Botón de cancelar presionado");
                manageLayoutClass.closeLayout((Stage) buttonPressed.getScene().getWindow());
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
