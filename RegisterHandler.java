package com.tazerdev.spa;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterHandler {
    /**
     * redirects to the program
     * @param user
     * @param stage
     * @throws IOException
     */
    public static void Init(User user, Stage stage) throws IOException {
        stage.hide();
        FXMLLoader loader = new FXMLLoader(LoginHandler.class.getResource("Home.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();
        HomeController home = loader.getController();
        home.setStage(stage);
        home.setUser(user);
    }
}
