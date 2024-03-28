package com.tazerdev.spa;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;


public class LoginController {
    public AnchorPane ap;
    public Text fpass;
    @FXML
    private TextField EmailText;

    @FXML
    private PasswordField PassText;

    Stage stage = null;

    /**
     * Try to log in and redirect to program if user is correct
     * @throws Exception
     */
    @FXML
    void login() throws Exception {
        boolean testing = false;
        User testuser = new User("Saud", "Alshareef","x@x.com","tester.lol","tester.lol");
        if(!testing) {
            User user = new User("Test", "Test", EmailText.getText(), PassText.getText(), PassText.getText());
            InputStreamReader isReader = new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("config.txt")));
            BufferedReader br = new BufferedReader(isReader);
            String line;
            String UsersNoline = "";
            int UsersNo = 0;
            StringBuffer inputBuffer = new StringBuffer();
            do {
                line = br.readLine();
                inputBuffer.append(line);
                inputBuffer.append("\n");
                if (line != null) {
                    if (line.startsWith("number_user")) {
                        UsersNoline = line;
                        UsersNo = Integer.parseInt(line.replace("number_user: ", ""));
                    }
                }
            } while (UsersNoline.equals(""));
            br.close();
            isReader.close();
            if (UsersNo != 0) {
                ObjectInputStream read = new ObjectInputStream(Objects.requireNonNull(this.getClass().getResourceAsStream("users.txt")));
                ArrayList<User> users = (ArrayList<User>) read.readObject();
                boolean flag = false;
                for (User u : users) {
                    if (u.getEmail().equals(user.getEmail()) && u.getPassword().equals(user.getPassword())) {
                        flag = true;
                        user = u;
                    }
                }
                if (flag) {
                    LoginHandler.Init(user, stage);
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Credentials");
                    a.setContentText("Please Make Sure You Have The Right Credentials");
                    a.show();
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("No Users");
                a.setContentText("There Is No Users");
                a.show();
            }
        }else{
            LoginHandler.Init(testuser, stage);
        }
    }

    /**
     * Redirect to register form
     * @throws IOException
     */
    @FXML
    void Register() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        RegisterController register = loader.getController();
        register.setStage(stage);
    }

    /**
     * Exits Program
     */
    @FXML
    void Quit(){
        stage.close();
    }

    /**
     * Minimizes Program
     */
    @FXML
    void Minmize(){
        stage.setIconified(true);
    }

    /**
     * assign stage in this file
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        Shared.Draggable(stage,ap);
    }
}
