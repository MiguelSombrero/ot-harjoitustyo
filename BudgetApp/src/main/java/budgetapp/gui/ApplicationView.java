
package budgetapp.gui;

import budgetapp.domain.CostController;
import budgetapp.domain.UserController;
import java.time.LocalDate;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ApplicationView {
    
    private UserController userController;
    private CostController costController;
    private Scene loginScene;
    
    public ApplicationView (UserController userController, CostController costController, Scene loginScene) {
        this.userController = userController;
        this.costController = costController;
        this.loginScene = loginScene;
    }
    
    public Scene getLoginScene () {
        return this.loginScene;
    }
    
    public Scene getApplicationScene (Stage primaryStage) {
        BorderPane layout = new BorderPane();
        layout.getStyleClass().add("application");
        Label kirjautuneena = new Label(""); // keksi miten tähän saa käyttäjän
        kirjautuneena.setAlignment(Pos.CENTER);
        
        // main view on left
        
        VBox buttonFrame = new VBox();
        buttonFrame.getStyleClass().add("buttonframe");
        
        Button addCost = new Button("Add cost");
        Button daily = new Button("Daily costs");
        Button monthly = new Button("Monhtly costs");
        Button yearly = new Button("Yearly costs");
        Button summary = new Button("Summary");
        Button logout = new Button("Logout user");
        Button changePassword = new Button("Change password");
        Button removeUser = new Button("Remove user");
        
        // password view on center
        
        GridPane passwordFrame = new GridPane();
        passwordFrame.getStyleClass().add("form");
        
        Button changeButton = new Button("Change");
            
        Label newPasswordText1 = new Label("New password:");
        Label newPasswordText2 = new Label("New password again:");
        PasswordField newPassword1 = new PasswordField();
        PasswordField newPassword2 = new PasswordField();
            
        passwordFrame.add(newPasswordText1, 0, 0);
        passwordFrame.add(newPassword1, 1, 0);
        passwordFrame.add(newPasswordText2, 0, 1);
        passwordFrame.add(newPassword2, 1, 1);
        passwordFrame.add(changeButton, 1, 2);
            
        // add new event/cost view on center
        
        GridPane addCostFrame = new GridPane();
        addCostFrame.getStyleClass().add("form");
        
        Button addButton = new Button("Add");
          
        Label categoryText = new Label("Category:");
        Label priceText = new Label("Price:");
        Label purchasedText = new Label("Purchased:");
        
        ComboBox category = new ComboBox();
        category.getStyleClass().add("listbutton");
        category.getItems().addAll(
                "House", "Food", "Restaurants", "Cafe", "Clothes", "Cosmetics", "Holiday",
                "Transportation", "Alcohol", "Culture", "Sports", "Sports gear", "Gifts",
                "Donations", "Mobile", "Random");
        
        TextField price = new TextField();
        TextField purchased = new TextField();
        
        addCostFrame.add(categoryText, 0, 0);
        addCostFrame.add(category, 1, 0);
        addCostFrame.add(priceText, 0, 1);
        addCostFrame.add(price, 1, 1);
        addCostFrame.add(purchasedText, 0, 2);
        addCostFrame.add(purchased, 1, 2);
        addCostFrame.add(addButton, 1, 3);
        
        // some error windows
        
        Alert error = new Alert(Alert.AlertType.ERROR);
        Alert warning = new Alert(Alert.AlertType.WARNING);
        Alert information = new Alert(Alert.AlertType.INFORMATION);
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.getButtonTypes().clear();
        
        ButtonType remove = new ButtonType("Remove");
        ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        confirmation.getButtonTypes().addAll(remove, cancel);
            
        addCost.setOnAction((event) -> {
            layout.setCenter(addCostFrame);
        });
        
        addButton.setOnAction((event1) -> {
            String cat = category.getValue().toString();
            Double pri = Double.valueOf(price.getText());
            LocalDate pur = LocalDate.parse(purchased.getText());
                
            int addCostInfo = costController.addCost(cat, pri, pur, userController.getUser());
                
            switch (addCostInfo) {
                case 0:
                    information.setContentText("Cost added succesfully!");
                    information.showAndWait();
                    break;
                case 2:
                    error.setContentText("Add cost failed. Try again later!");
                    error.showAndWait();
                    break;
                default:
                    break;    
            }
                
            price.clear();
            category.setValue(null);
            purchased.clear();
        });
        
        daily.setOnAction((event) -> {
            
        });
        
        monthly.setOnAction((event) -> {
            
        });
        
        yearly.setOnAction((event) -> {
            
        });
        
        summary.setOnAction((event) -> {
            
        });
        
        logout.setOnAction((event) -> {
            logout(primaryStage);
        });
        
        changePassword.setOnAction((event) -> {
            layout.setCenter(passwordFrame);
        });
        
        changeButton.setOnAction((event1) -> {
            if (!newPassword1.getText().equals(newPassword2.getText())) {
                warning.setContentText("New password typed wrong!");
                warning.showAndWait();
            }
            else if (!checkSyntax(newPassword1.getText())) {
                warning.setContentText("Username and password must be 5-15 caharacters!");
                warning.showAndWait();
            }
            else {
                int changeInfo = userController.changePassword(newPassword1.getText());
                    
                switch (changeInfo) {
                    case 0:
                        information.setContentText("Password changed succesfully!");
                        information.showAndWait();
                        break;
                    case 2:
                        error.setContentText("Changing password failed. Try again later!");
                        error.showAndWait();
                        break;
                    default:
                        break;    
                }
            }
                
            newPassword1.clear();
            newPassword2.clear();
        });
        
        removeUser.setOnAction((event) -> {
            confirmation.setContentText("Are you sure you want to remove user " + userController.getUser() + "?\n"
                    + "This will remove user and all it's data.");
            
            Optional<ButtonType> result = confirmation.showAndWait();
            
            if (result.get() == remove) {
                int removeInfo = userController.removeUser();
            
                switch (removeInfo) {
                    case 0:
                        information.setContentText("User removed succesfully!");
                        information.showAndWait();
                        logout(primaryStage);
                        break;
                    case 2:
                        error.setContentText("Remove user failed. Try again later!");
                        error.showAndWait();
                        break;
                    default:
                        break;
                }
            }
        });
        
        buttonFrame.getChildren().addAll(
                addCost, daily, monthly, yearly, summary, logout, changePassword, removeUser);
        
        buttonFrame.getChildren().forEach(button -> button.getStyleClass().add("listbutton"));
        
        layout.setLeft(buttonFrame);
        layout.setTop(kirjautuneena); // keksi miten tähän saadaan käyttäjä
        layout.setCenter(addCostFrame);
        
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("styles.css");
        
        return scene;
    }
    
    public boolean checkSyntax (String string) {
        return (string.length() > 4 && string.length() < 16);
    }
    
    public void logout (Stage primaryStage) {
        userController.logoutUser();
        primaryStage.setScene(loginScene);
    }
    
}
