package UIController.UserItemLayout;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import objects.User;
import resources.ManageLayout;

import java.util.ArrayList;

public class UsersListLogicController {

    private ManageLayout manageLayoutClass = ManageLayout.getInstance();
    private ArrayList<User> foundUsers = null;
    private static UsersListLogicController usersListClass = null;

    public static UsersListLogicController getInstance() {

        if (usersListClass == null) {
            usersListClass = new UsersListLogicController();
        }

        return usersListClass;
    }

    public void loadRootContainer() {

        FXMLLoader fxmlLoaderRootContainer = manageLayoutClass.loadLayout("layout/UsersRootContainer.fxml", "Item", false);
        UsersRootContainerLayoutController userRootContainerClass = fxmlLoaderRootContainer.getController();
        Stage containerStage = manageLayoutClass.getStage();


        for (User usuario : foundUsers) {

            FXMLLoader fxmlLoaderItem = manageLayoutClass.loadLayout("layout/UserItemLayout.fxml", "Item", false);
            UserItemLayoutController userItemClass = fxmlLoaderItem.getController();

            //profilePhotoUserItem.setImage(usuario.);
            userItemClass.setNamesUserItem(usuario.getNombres_usuario());
            userItemClass.setLastNamesUserItem(usuario.getApellidos_usuario());
            userItemClass.setTypeUserItem(usuario.getNombre_tipos_usuario());
            userItemClass.setRFCUserItem(usuario.getRfc_usuario());
            userItemClass.setAddressUserItem(usuario.getDireccion_usuario());

            userRootContainerClass.setItemParent(manageLayoutClass.getParent());
            userRootContainerClass.paintUserItem();
        }

        containerStage.setTitle("RootLayout");
        containerStage.getIcons().add(new Image("images/Fiscalia_Web_Logo.jpg"));
        containerStage.setResizable(true);
        containerStage.show();
    }

    public void setFoundUsers(ArrayList<User> foundUsers) {
        this.foundUsers = foundUsers;
    }
}
