package com.tazerdev.spa;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class RegisterController {
    @FXML
    public AnchorPane ap;
    @FXML
    private TextField Fname;
    @FXML
    private TextField Lname;
    @FXML
    private TextField EmailText;

    @FXML
    private PasswordField PassText;

    @FXML
    private PasswordField ConfPassText;

    Stage stage = null;

    /**
     * redirect to login
     * @throws IOException
     */
    @FXML
    void Login() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        LoginController login = loader.getController();
        login.setStage(stage);
    }

    /**
     * validates and creates account if valid will redirect to program
     */
    @FXML
    void register() {
        try{
            User user = new User(Fname.getText(),Lname.getText(),EmailText.getText(),PassText.getText(),ConfPassText.getText());
            InputStreamReader isReader = new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("config.txt")));
            BufferedReader br = new BufferedReader(isReader);
            String line;
            String UsersNoline = "";
            int UsersNo = 0;
            StringBuffer inputBuffer = new StringBuffer();
            do{
                line = br.readLine();
                inputBuffer.append(line);
                inputBuffer.append("\n");
                if(line != null){
                    if(line.startsWith("number_user")) {
                        UsersNoline = line;
                        UsersNo = Integer.parseInt(line.replace("number_user: ", ""));
                    }
                }
            }while (UsersNoline.equals(""));
            br.close();
            isReader.close();
            ArrayList<User> users;
            if(UsersNo == 0){
                users = new ArrayList<>();
            }else{
                ObjectInputStream read = new ObjectInputStream(Objects.requireNonNull(this.getClass().getResourceAsStream("users.txt")));
                users = (ArrayList<User>) read.readObject();
                read.close();
            }
            users.add(user);
            FileOutputStream userout = new FileOutputStream(new File(Objects.requireNonNull(this.getClass().getResource("users.txt")).toURI()));
            ObjectOutputStream writer = new ObjectOutputStream(userout);
            writer.writeObject(users);
            writer.close();
            userout.close();
            String editusernoline = UsersNoline.replace(String.valueOf(UsersNo), String.valueOf(UsersNo+1));
            String str = inputBuffer.toString().replace(UsersNoline,editusernoline);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(Objects.requireNonNull(this.getClass().getResource("config.txt")).toURI()));
            fileOutputStream.write(str.getBytes());
            fileOutputStream.close();
            RegisterHandler.Init(user, stage);
        }catch (IOException e){
          e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
            if (e.getMessage() == null){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Values");
                a.setContentText("Please Make Sure You Fill all the values");
                a.show();
            }else if (e.getMessage().equals("Conf and Pass Not Equal")){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Confirm Password");
                a.setContentText("Please Make Sure The Password And The Confirmation Are Equal");
                a.show();
            }else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Values");
                a.setContentText("Please Make Sure All the values are correct");
                a.show();
            }
        }
    }

    /**
     * exit program
     */
    @FXML
    void Quit(){
        stage.close();
    }

    /**
     * minimizes the program
     */
    @FXML
    void Minmize(){
        stage.setIconified(true);
    }

    /**
     * sets the stage so we are able to change it later
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        Shared.Draggable(stage,ap);
    }
}

