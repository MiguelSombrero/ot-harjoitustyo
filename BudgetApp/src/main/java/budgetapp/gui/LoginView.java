
package budgetapp.gui;


import budgetapp.domain.CostController;
import budgetapp.domain.UserController;
import java.util.Optional;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Luokka, joka rakentaa login-näkymän.
 * 
 * @author miika
 */
public class LoginView {
    
    private UserController userController;
    private CostController eventController;
    private Scene appScene;
    
    /**
     * Luokan konstruktori.
     * 
     * @param userController Käyttäjän sovelluslogiikasta vastaava luokka
     * @param eventController Käyttäjään liittyvien kustannusten sovelluslogiikasta vastaava luokka
     */
    public LoginView (UserController userController, CostController eventController) {
        this.userController = userController;
        this.eventController = eventController;
        this.appScene = null;
    }
    
    public void setAppScene (Scene appScene) {
        this.appScene = appScene;
    }
    
    /**
     * Metodi, joka rakentaa login-näkymän.
     * 
     * @param primaryStage Stage-olio, johon metodissa luotava Scene-olio asetetaan
     * @return Login-näkymää vastaava Scene-olio
     */
    public Scene getLoginScene (Stage primaryStage) {
        BorderPane layout = new BorderPane();
        layout.getStyleClass().add("login");
                
        GridPane frame = new GridPane();
        frame.getStyleClass().add("form");
        
        Button login = new Button("Login");
        Button createUser = new Button("Create");
        Button changeView = new Button("New user ?");
        
        Label helpText = new Label("LOGIN");
        helpText.setId("heading");
        Label usernameText = new Label("Username:");
        Label passwordText = new Label("Password:");
        
        TextField username = new TextField();
        PasswordField password = new PasswordField();
        
        Alert error = new Alert(AlertType.ERROR);
        Alert warning = new Alert(AlertType.WARNING);
        Alert information = new Alert(AlertType.INFORMATION);
        
        login.setOnAction((event) -> { 
            String user = username.getText();
            String passwd = password.getText();
            
            int loginInfo = userController.loginUser(user, passwd);
            
            switch (loginInfo) {
                case 0:
                    primaryStage.setScene(appScene);
                    break;
                case 1:
                    createAlert(warning, "Wrong username or password!");
                    break;
                case 2:
                    createAlert(error, "Login failed. Please try again later!");
                    break;
                default:
                    break;
            }
            
            username.clear();
            password.clear();
        });
        
        createUser.setOnAction((event) -> {
            String user = username.getText().trim();
            String passwd = password.getText().trim();
            
            if (!userController.checkCredentials(user) || !userController.checkCredentials(passwd)) {
                createAlert(warning, "Username and password must be 5-15 caharacters!");
                username.clear();
                password.clear();
                return;
            }
            
            int createUserInfo = userController.createUser(user, passwd);
            
            switch (createUserInfo) {
                case 0:
                    createAlert(information, "New user '" + user + "' created succesfully!");
                    break;
                case 1:
                    createAlert(warning, "Username taken - select another one!");
                    break;
                case 2:
                    createAlert(error, "Creating an account failed. Please try again later!");
                    break;
                default:
                    break;
            }
            
            username.clear();
            password.clear();
            changeToLoginView(helpText, changeView, frame, login, createUser);
        });
        
        changeView.setOnAction((event) -> {
            if (helpText.getText().equals("LOGIN")) {
                changeToCreateView(helpText, changeView, frame, login, createUser);
            }
            else {
                changeToLoginView(helpText, changeView, frame, login, createUser);
            }
        });
        
        frame.add(helpText, 1, 0);
        frame.add(usernameText, 1, 2);
        frame.add(username, 1, 3);
        frame.add(passwordText, 1, 4);
        frame.add(password, 1, 5);
        frame.add(login, 1, 6);
        
        layout.setCenter(frame);
        layout.setTop(changeView);
        
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("styles.css");
        
        return scene;
    }
    
    public void changeToCreateView (Label helpText, Button changeView, GridPane frame, Button login, Button createUser) {
        helpText.setText("CREATE USER");
        changeView.setText("Login");
        frame.getChildren().remove(login);
        frame.add(createUser, 1, 6);
    }
    
    public void changeToLoginView (Label helpText, Button changeView, GridPane frame, Button login, Button createUser) {
        helpText.setText("LOGIN");
        changeView.setText("New user ?");
        frame.getChildren().remove(createUser);
        frame.add(login, 1, 6);
    }
    
    /**
     * Metodi, joka rakentaa Alert-ikkunan annettujen parametrien perusteella.
     * 
     * @param type Alert-ikkunan tyyppi
     * @param text Teksti, joka asetetaan Alert-ikkunaan
     * @return Palauttaa tiedon siitä, mitä painiketta käyttäjä painoi
     */
    public Optional<ButtonType> createAlert(Alert type, String text) {
        type.setContentText(text);
        return type.showAndWait();
    }
}
