package ma.ychakir.rz.launchermaker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ma.ychakir.rz.launchermaker.Controllers.Controller;

/**
 * @author Yassine
 */
public class LauncherMaker extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller.setStage(primaryStage);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(getClass().getResource("Views/img/Icon.png").toExternalForm()));
        Font.loadFont(getClass().getResource("Views/fonts/fontawesome-webfont.ttf").toExternalForm(), 12);
        Font.loadFont(getClass().getResource("Views/fonts/Comfortaa-Regular.ttf").toExternalForm(), 12);
        Parent root = FXMLLoader.load(getClass().getResource("Views/main.fxml"));
        primaryStage.setTitle("Pack Manager");
        primaryStage.setScene(new Scene(root, 500, 350));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
