package UIController.UsersRootContainerLayout;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UsersRootContainerLayoutController extends Application {

    private Pane itemPane = null;

    @FXML
    private VBox usersRootContainer;

    private static UsersRootContainerLayoutController userItemContainerClass = null;


    public static UsersRootContainerLayoutController getInstance(){
        if(userItemContainerClass == null){
            userItemContainerClass = new UsersRootContainerLayoutController();
        }

        return userItemContainerClass;
    }

    public void paintUserItem(){

        AnchorPane anchorRootPane = (AnchorPane) itemPane;
        usersRootContainer.getChildren().add(anchorRootPane);
    }

    public void setItemPane(Pane itemPane){
        this.itemPane = itemPane;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }

}
