package resources;

import UIController.SignUpLayout.SignUpLayoutController;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CameraManager implements Initializable {

    @FXML
    private AnchorPane cameraRootLayout;

    @FXML
    private ChoiceBox<String> choiceBoxCamerasList;

    @FXML
    private Text selectCameraLabel;

    private static CameraManager cameraManagerInstance = null;
    private Stage cameraManagerStage = null;
    private Webcam webcamObject = null;
    private SignUpLayoutController signUpLayoutController = null;
    private List<Webcam> webcamList = null;

    public static CameraManager getInstance() {
        if (cameraManagerInstance == null) {
            cameraManagerInstance = new CameraManager();
        }

        return cameraManagerInstance;
    }

    public CameraManager() {
    }

    private void setUpCamera(String cameraSelected) {

        for (Webcam webcamItem : webcamList) {
            if (webcamItem.getDevice().getName().equals(cameraSelected)) {
                webcamObject = webcamItem;
            }
        }

        webcamObject.setViewSize(getMaxCameraResolution());

        WebcamPanel webcamPanel = new WebcamPanel(webcamObject);
        webcamPanel.setFPSDisplayed(true);
        webcamPanel.setDisplayDebugInfo(true);
        webcamPanel.setImageSizeDisplayed(true);
        webcamPanel.setMirrored(true);


        SwingNode swingNode = new SwingNode();
        swingNode.setContent(webcamPanel);

        cameraRootLayout.getChildren().add(swingNode);
    }

    private Dimension getMaxCameraResolution() {
        Dimension[] cameraResolutions = Webcam.getDefault().getViewSizes();
        Dimension cameraMaxResolution = null;

        for (int i = 0; i < cameraResolutions.length; i++) {
            if (i == (cameraResolutions.length - 1)) {
                cameraMaxResolution = cameraResolutions[i];
            }
        }

        return cameraMaxResolution;
    }

    public void exitStageHandler() {
        cameraManagerStage.setOnCloseRequest(event -> {
            if (webcamObject != null) {
                webcamObject.close();
            } else {
                cameraManagerStage.close();
            }
        });
    }

    @FXML
    private void takePhoto() {

        if (webcamObject != null) {
            signUpLayoutController.convertImageToFile("userPhoto", webcamObject.getImage());
            System.out.println(webcamObject.getImage());
            signUpLayoutController.hideUserPhotoLabelWarning();

            photoShootedShowAlert();
        } else {
            selectCameraShowAlert();
        }
    }

    private void selectCameraShowAlert() {
        Alert photoShootedAlert = new Alert(Alert.AlertType.INFORMATION, "Primero, debe seleccionar una cámara");
        photoShootedAlert.setTitle("¡Cámara no seleccionada!");
        photoShootedAlert.setHeaderText(null);
        photoShootedAlert.showAndWait();
    }

    private void photoShootedShowAlert() {

        ButtonType YES_BUTTON = new ButtonType("Si", ButtonBar.ButtonData.YES);
        ButtonType NO_BUTTON = new ButtonType("No", ButtonBar.ButtonData.NO);

        Alert photoShootedAlert = new Alert(Alert.AlertType.CONFIRMATION, "La fotografía se ha tomado correctamente. ¿Desea tomarla nuevamente?", YES_BUTTON, NO_BUTTON);
        photoShootedAlert.setTitle("¡Proceso exitoso!");
        photoShootedAlert.setHeaderText(null);
        photoShootedAlert.showAndWait();

        switch (photoShootedAlert.getResult().getText()) {
            case "Si":
                break;

            case "No":
                webcamObject.close();
                cameraManagerStage.close();
                break;
        }
    }

    private void setUpCameraOptions() {

        webcamList = Webcam.getWebcams();

        for (Webcam webcamItem : webcamList) {
            choiceBoxCamerasList.setItems(FXCollections.observableArrayList(webcamItem.getDevice().getName()));
        }

        choiceBoxCamerasList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectCameraLabel.setVisible(false);
                setUpCamera(newValue);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpCameraOptions();
    }

    //Getters and Setters

    public void setCameraManagerStage(Stage cameraManagerStage) {
        this.cameraManagerStage = cameraManagerStage;
    }

    public void setSignUpLayoutController(SignUpLayoutController signUpLayoutController) {
        this.signUpLayoutController = signUpLayoutController;
    }
}
