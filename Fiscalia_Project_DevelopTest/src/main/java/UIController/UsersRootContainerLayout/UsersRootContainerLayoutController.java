package UIController.UsersRootContainerLayout;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import resources.ManageLayout;

public class UsersRootContainerLayoutController extends Application {

    private static UsersRootContainerLayoutController userItemContainerClass = null;

    public static UsersRootContainerLayoutController getInstance(){
        if(userItemContainerClass == null){
            userItemContainerClass = new UsersRootContainerLayoutController();
        }

        return userItemContainerClass;
    }

    @FXML
    private VBox usersRootContainer;

    public void paintUserItem(){

        ManageLayout manageLayoutClass = ManageLayout.getInstance();
        manageLayoutClass.loadLayout("layout/UsersRootContainer.fxml", "Usuarios");

        /*
        try {
            usersRootContainer.getChildren().add((Node) FXMLLoader.load(getClass().getResource("layout/UserItemLayout.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

         */
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}
