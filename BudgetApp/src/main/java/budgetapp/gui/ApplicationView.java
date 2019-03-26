
package budgetapp.gui;

import budgetapp.domain.Category;
import budgetapp.domain.Cost;
import budgetapp.domain.CostController;
import budgetapp.domain.UserController;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
        
        // main view on left
        
        VBox buttonFrame = new VBox();
        buttonFrame.getStyleClass().add("buttonframe");
        
        Button addCost = new Button("Add cost");
        Button weekday = new Button("Weekday");
        Button month = new Button("Month");
        Button category = new Button("Category");
        Button weekdayYearly = new Button("Weekday yearly");
        Button monthYearly = new Button("Month yearly");
        Button categoryYearly = new Button("Category yearly");
        Button logout = new Button("Logout user");
        Button changePassword = new Button("Change password");
        Button removeUser = new Button("Remove user");
        
        Label summaryText = new Label("");
        Label yearlyText = new Label("");
        Label userText = new Label("");
        
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
        Label helpText = new Label("ADD NEW COST");
        Label categoryText = new Label("Category:");
        Label priceText = new Label("Price:");
        Label purchasedText = new Label("Purchased:");
        
        ComboBox categorySelection = new ComboBox();
        category.getStyleClass().add("listbutton");
        Arrays.stream(Category.values()).forEach(cat -> categorySelection.getItems().add(cat));
        
        ComboBox purchased = new ComboBox();
        purchased.getStyleClass().add("listbutton");
        for (int i = 0; i < 31; i++) purchased.getItems().add(LocalDate.now().minusDays(i));
        
        TextField price = new TextField();
            
        addCostFrame.add(helpText, 0, 0);
        addCostFrame.add(categoryText, 0, 1);
        addCostFrame.add(categorySelection, 1, 1);
        addCostFrame.add(priceText, 0, 2);
        addCostFrame.add(price, 1, 2);
        addCostFrame.add(purchasedText, 0, 3);
        addCostFrame.add(purchased, 1, 3);
        addCostFrame.add(addButton, 1, 4);
        
        // some error windows to show when something bad/good happens
        
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
            Category cat = Category.valueOf(categorySelection.getValue().toString().toUpperCase());
            Double pri = Double.valueOf(price.getText());
            LocalDate pur = LocalDate.parse(purchased.getValue().toString());
            
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
            categorySelection.setValue(null);
            purchased.setValue(null);
        });
        
        weekday.setOnAction((event) -> {
            List<Cost> costs = costController.getCosts(userController.getUser());
            double[][] money = costController.sum(costs);
            
            BarChart<String, Number> dailychart = createBarChart(
                    "Money consumption by day of week", "Day", "Money", false);
            
            XYChart.Series data = populateData("dayofweek", money[0].clone(), 8);
            dailychart.getData().add(data);
            layout.setCenter(dailychart);
        });
        
        month.setOnAction((event) -> {
            List<Cost> costs = costController.getCosts(userController.getUser());
            double[][] money = costController.sum(costs);
            
            BarChart<String, Number> monthlychart = createBarChart(
                    "Money consumption by month", "Month", "Money", false);
            
            XYChart.Series data = populateData("month", money[1].clone(), 13);
            monthlychart.getData().add(data);
            layout.setCenter(monthlychart);
        });
        
        category.setOnAction((event) -> {
            List<Cost> costs = costController.getCosts(userController.getUser());
            double[][] money = costController.sum(costs);
            
            BarChart<String, Number> barchart = createBarChart(
                    "Money consumption by category", "Category", "Money", false);
            
            XYChart.Series data = populateData("category", money[2].clone(), Category.values().length);
            barchart.getData().add(data);
            layout.setCenter(barchart);
        });
        
        weekdayYearly.setOnAction((event) -> {
            List<Cost> costs = costController.getCosts(userController.getUser());
            double[][] money = costController.sumYearly(costs);
            
            BarChart<String, Number> weekdaychart = createBarChart(
                    "Money consumption by year and weekday", "Weekday", "Money", true);
            
            for (int i = 50; i < 70; i++) {
                if (money[i][0] == 0) continue;
                
                XYChart.Series data = populateData("dayofweek", money[i].clone(), 8);
                data.setName(String.valueOf(i + 1960));
                weekdaychart.getData().add(data);
            }
            
            layout.setCenter(weekdaychart);
        });
        
        monthYearly.setOnAction((event) -> {
            List<Cost> costs = costController.getCosts(userController.getUser());
            double[][] money = costController.sumYearly(costs);
            
            BarChart<String, Number> yearlychart = createBarChart(
                    "Money consumption by year and month", "Month", "Money", true);
            
            for (int i = 10; i < 30; i++) {
                if (money[i][0] == 0) continue;
                
                XYChart.Series data = populateData("month", money[i].clone(), 13);
                data.setName(String.valueOf(i + 2000));
                yearlychart.getData().add(data);
            }
            
            layout.setCenter(yearlychart);
        });
        
        categoryYearly.setOnAction((event) -> {
            List<Cost> costs = costController.getCosts(userController.getUser());
            double[][] money = costController.sumYearly(costs);
            
            BarChart<String, Number> yearlychart = createBarChart(
                    "Money consumption by year and category", "Category", "Money", true);
            
            for (int i = 30; i < 50; i++) {
                if (money[i][0] == 0) continue;
                
                XYChart.Series data = populateData("category", money[i].clone(), Category.values().length);
                data.setName(String.valueOf(i + 1980));
                yearlychart.getData().add(data);
            }
            
            layout.setCenter(yearlychart);
        });
        
        logout.setOnAction((event) -> {
            logout(primaryStage, layout, addCostFrame);
        });
        
        changePassword.setOnAction((event) -> {
            layout.setCenter(passwordFrame);
        });
        
        changeButton.setOnAction((event) -> {
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
                costController.emptyCostsCache(userController.getUser());
                int removeUserInfo = userController.removeUser();
                
                switch (removeUserInfo) {
                    case 0:
                        information.setContentText("User removed succesfully!");
                        information.showAndWait();
                        logout(primaryStage, layout, addCostFrame);
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
                addCost, summaryText, weekday, month, category, yearlyText, weekdayYearly, monthYearly,
                categoryYearly, userText, logout, changePassword, removeUser);
        
        buttonFrame.getChildren().forEach(button -> button.getStyleClass().add("listbutton"));
        
        layout.setLeft(buttonFrame);
        layout.setCenter(addCostFrame);
        
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("styles.css");
        
        return scene;
    }
    
    public boolean checkSyntax (String string) {
        return (string.length() > 4 && string.length() < 16);
    }
    
    public void logout (Stage primaryStage, BorderPane layout, GridPane addCostFrame) {
        costController.emptyCostsCache(userController.getUser());
        userController.logoutUser();
        layout.setCenter(addCostFrame);
        primaryStage.setScene(loginScene);
    }
    
    public BarChart<String, Number> createBarChart (String title, String x, String y, boolean legend) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        
        BarChart<String, Number> barchart = new BarChart<>(xAxis, yAxis);
        
        xAxis.setLabel(x);
        yAxis.setLabel(y);
        barchart.setTitle(title);
        barchart.setLegendVisible(legend);
        
        return barchart;
    }
    
    public XYChart.Series populateData (String condition, double[] costs, int end) {
        XYChart.Series data = new XYChart.Series();
        
        for (int i = 1; i < end; i++) {
            
            if (condition.equals("dayofweek")) {
                data.getData().add(new XYChart.Data(DayOfWeek.of(i).toString(), costs[i]));
                
            } else if (condition.equals("month")) {
                data.getData().add(new XYChart.Data(Month.of(i).toString(), costs[i]));
                
            } else if (condition.equals("category")) {
                data.getData().add(new XYChart.Data(Category.values()[i].toString(), costs[i]));
            }
        }
        return data;  
    }
    
}
