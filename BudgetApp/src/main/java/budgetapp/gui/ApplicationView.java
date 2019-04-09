
package budgetapp.gui;

import budgetapp.domain.Category;
import budgetapp.domain.Cost;
import budgetapp.domain.CostController;
import budgetapp.domain.UserController;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
        // main window
        TabPane application = new TabPane();
        Tab costsTab = new Tab("Costs");
        Tab userTab = new Tab("User");
        application.getTabs().addAll(costsTab, userTab);
        application.getTabs().forEach(tab -> tab.setClosable(false));
        
        // some error windows to show when something bad/good happens
        
        Alert error = new Alert(Alert.AlertType.ERROR);
        Alert warning = new Alert(Alert.AlertType.WARNING);
        Alert information = new Alert(Alert.AlertType.INFORMATION);
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "", new ButtonType("Remove", ButtonData.APPLY), new ButtonType("Cancel", ButtonData.CANCEL_CLOSE));
        
        // main view on Costs tab
        
        BorderPane costsLayout = new BorderPane();
        costsLayout.getStyleClass().add("application");
        
        // left view on Costs tab
        
        VBox costsButtons = new VBox();
        costsButtons.getStyleClass().add("buttonframe");
        
        Button addCost = new Button("Add cost");
        Button viewCosts = new Button("View costs");
        Button weekday = new Button("Weekday");
        Button month = new Button("Month");
        Button category = new Button("Category");
        Button weekdayYearly = new Button("Weekday yearly");
        Button monthYearly = new Button("Month yearly");
        Button categoryYearly = new Button("Category yearly");
        
        Label summaryText = new Label("");
        Label yearlyText = new Label("");
        
        // add new cost view on center Costs tab
        
        GridPane addCostFrame = new GridPane();
        addCostFrame.getStyleClass().add("form");
        
        Button addButton = new Button("Add");
        Label helpText = new Label("ADD NEW COST");
        helpText.setId("heading");
        Label categoryText = new Label("Category:");
        Label priceText = new Label("Price:");
        Label purchasedText = new Label("Purchased:");
        
        ComboBox categorySelection = new ComboBox();
        categorySelection.getStyleClass().add("listbutton");
        categorySelection.setItems(FXCollections.observableArrayList(Category.values()));
        
        ComboBox purchased = new ComboBox();
        purchased.getStyleClass().add("listbutton");
        for (int i = 0; i < 31; i++) purchased.getItems().add(LocalDate.now().minusDays(i));
        
        TextField price = new TextField();
            
        addCostFrame.add(helpText, 1, 0);
        addCostFrame.add(categoryText, 0, 1);
        addCostFrame.add(categorySelection, 1, 1);
        addCostFrame.add(priceText, 0, 2);
        addCostFrame.add(price, 1, 2);
        addCostFrame.add(purchasedText, 0, 3);
        addCostFrame.add(purchased, 1, 3);
        addCostFrame.add(addButton, 1, 4);
        
        // view costs view on center Costs tab
        
        ListView<Cost> costList = new ListView<>();
        
        // button listeners for Cost tab
        
        costList.setCellFactory(lv -> new ListCell<Cost>() {
            @Override
            public void updateItem(Cost cost, boolean empty) {
                super.updateItem(cost, empty);
                setText(empty ? null : cost.toString());
            }
        });
        
        // TÄMÄ METODI KAIPAA VIILAUSTA: POISTON JÄLKEEN OLISI HYVÄ PÄIVITTÄÄ LISTVIEW
        // EN SAA PÄIVITYSTÄ TOIMIMAAN, ILMAN ETTÄ TÖKKÄÄ INDEX OUT OF BOUNDS HERJAAN
        
        costList.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends Cost> observable, Cost oldValue, Cost newValue) -> {
            
            if (newValue == null) return;
            
            Optional<ButtonType> result = createAlert(confirmation,
                    "Are you sure you want to remove cost: \n" + newValue.toString());
            
            if (result.get().getButtonData().equals(ButtonData.APPLY)) {
                
                int removeInfo = costController.removeCost(newValue.getId());
                costController.emptyCostsCache(userController.getUser().getUsername());
                
                switch (removeInfo) {
                    case 0:
                        createAlert(information, "Cost removed succesfully!");
                        break;
                    case 2:
                        createAlert(error, "Something went wrong! Try again later.");
                        break;
                    default:
                        break;
                }
            }
        });
            
        addCost.setOnAction((event) -> {
            costsLayout.setCenter(addCostFrame);
        });
        
        viewCosts.setOnAction((event) -> {
            List<Cost> costs = costController.getCosts(userController.getUser().getUsername());
            ObservableList<Cost> list = FXCollections.observableArrayList(costs);
            costList.getItems().clear();
            costList.setItems(list);
            costsLayout.setCenter(costList);
        });
               
        addButton.setOnAction((event1) -> {
            if (!isNumber(price.getText().replace(",", "."))) {
                createAlert(warning, "Oops! Price wasn't given correctly.");
                return;
            }
            Double pri = Double.parseDouble(price.getText().replace(",", "."));
            Category cat = Category.valueOf(categorySelection.getValue().toString().toUpperCase());
            LocalDate pur = LocalDate.parse(purchased.getValue().toString());
            
            int addCostInfo = costController.addCost(cat, pri, pur, userController.getUser().getUsername());
            costController.emptyCostsCache(userController.getUser().getUsername());
        
            switch (addCostInfo) {
                case 0:
                    createAlert(information, "Cost added succesfully!");
                    break;
                case 2:
                    createAlert(error, "Add cost failed. Try again later!");
                    break;
                default:
                    break;    
            }
                
            price.clear();
            categorySelection.setValue(null);
            purchased.setValue(null);
        });
        
        weekday.setOnAction((event) -> {
            List<Cost> costs = costController.getCosts(userController.getUser().getUsername());
                
            double[] money = costController.sumWeekday(costs, new double[8]);
            
            BarChart<String, Number> weekdaychart = createBarChart(
                    "Money consumption by day of week", "Weekday", "Money", false);
            
            XYChart.Series data = new XYChart.Series<>();
                
            for (int i = 1; i < money.length; i++) {
                data.getData().add(new XYChart.Data(DayOfWeek.of(i).toString(), money[i]));
            }
                
            weekdaychart.getData().add(data);
            costsLayout.setCenter(weekdaychart);
        });
        
        month.setOnAction((event) -> {
            List<Cost> costs = costController.getCosts(userController.getUser().getUsername());
            
            double[] money = costController.sumMonth(costs, new double[13]);
            
            BarChart<String, Number> monthlychart = createBarChart(
                    "Money consumption by month", "Month", "Money", false);
            
            XYChart.Series data = new XYChart.Series<>();
               
            for (int i = 1; i < money.length; i++) {
                data.getData().add(new XYChart.Data(Month.of(i).toString(), money[i]));
            }
            
            monthlychart.getData().add(data);
            costsLayout.setCenter(monthlychart);
        });
        
        category.setOnAction((event) -> {
            List<Cost> costs = costController.getCosts(userController.getUser().getUsername());
            
            int categories = Category.values().length;
            double[] money = costController.sumCategory(costs, new double[categories]);
            
            BarChart<String, Number> barchart = createBarChart(
                    "Money consumption by category", "Category", "Money", false);
            
            XYChart.Series data = new XYChart.Series<>();
            
            for (int i = 0; i < money.length; i++) {
                data.getData().add(new XYChart.Data(Category.values()[i].toString(), money[i]));
            }
            
            barchart.getData().add(data);
            costsLayout.setCenter(barchart);
        });
        
        weekdayYearly.setOnAction((event) -> {
            List<Cost> costs = costController.getCosts(userController.getUser().getUsername());
            
            double[][] money = costController.sumWeekdayYearly(costs, new double[40][8]);
            
            BarChart<String, Number> weekdaychart = createBarChart(
                    "Money consumption by year and weekday", "Weekday", "Money", true);
            
            for (int i = 10; i < money.length; i++) {
                if (money[i][0] == 0) continue;
                
                XYChart.Series data = new XYChart.Series<>();
                data.setName(String.valueOf(i + 2000));
                
                for (int j = 1; j < money[0].length; j++) {
                    data.getData().add(new XYChart.Data(DayOfWeek.of(j).toString(), money[i][j]));
                }
                weekdaychart.getData().add(data);
            }
            
            costsLayout.setCenter(weekdaychart);
        });
        
        monthYearly.setOnAction((event) -> {
            List<Cost> costs = costController.getCosts(userController.getUser().getUsername());
            
            double[][] money = costController.sumMonthYearly(costs, new double[40][13]);
            
            BarChart<String, Number> yearlychart = createBarChart(
                    "Money consumption by year and month", "Month", "Money", true);
            
            for (int i = 10; i < money.length; i++) {
                if (money[i][0] == 0) continue;
                
                XYChart.Series data = new XYChart.Series<>();
                data.setName(String.valueOf(i + 2000));
                
                for (int j = 1; j < money[0].length; j++) {
                    data.getData().add(new XYChart.Data(Month.of(j).toString(), money[i][j]));
                }
                yearlychart.getData().add(data);
            }
            
            costsLayout.setCenter(yearlychart);
        });
        
        categoryYearly.setOnAction((event) -> {
            List<Cost> costs = costController.getCosts(userController.getUser().getUsername());
            
            int categories = Category.values().length;
            double[][] money = costController.sumCategoryYearly(costs, new double[40][categories+1]);
            
            BarChart<String, Number> yearlychart = createBarChart(
                    "Money consumption by year and category", "Category", "Money", true);
            
            for (int i = 10; i < money.length; i++) {
                if (money[i][categories] == 0) continue;
                
                XYChart.Series data = new XYChart.Series<>();
                data.setName(String.valueOf(i + 2000));
                
                for (int j = 0; j < categories; j++) {
                    data.getData().add(new XYChart.Data(Category.values()[j].toString(), money[i][j]));
                }
                yearlychart.getData().add(data);
            }
            costsLayout.setCenter(yearlychart);
        });
        
        // main view on User tab
        
        BorderPane userLayout = new BorderPane();
        userLayout.getStyleClass().add("application");
        
        // left User tab
        
        VBox userButtons = new VBox();
        userButtons.getStyleClass().add("buttonframe");
        
        Button logout = new Button("Logout user");
        Button changePassword = new Button("Change password");
        Button removeUser = new Button("Remove user");
        
        // info view on top User tab
        
        HBox infoPane = new HBox();
        infoPane.setAlignment(Pos.CENTER);
        Label infoText = new Label("");
        infoPane.getChildren().add(infoText);
        
        // password view on center User tab
        
        GridPane passwordFrame = new GridPane();
        passwordFrame.getStyleClass().add("form");
        
        Button changeButton = new Button("Change");
        Label newPasswordText1 = new Label("New password:");
        Label newPasswordText2 = new Label("New password again:");
        PasswordField newPassword1 = new PasswordField();
        PasswordField newPassword2 = new PasswordField();
            
        passwordFrame.add(newPasswordText1, 0, 0);
        passwordFrame.add(newPassword1, 0, 1);
        passwordFrame.add(newPasswordText2, 0, 2);
        passwordFrame.add(newPassword2, 0, 3);
        passwordFrame.add(changeButton, 0, 4);
        
        userTab.setOnSelectionChanged((event) -> {
            infoText.setText(userController.getUser().toString());
        });
        
        logout.setOnAction((event) -> {
            logout(primaryStage, costsLayout, addCostFrame, infoText);
        });
        
        changePassword.setOnAction((event) -> {
            userLayout.setCenter(passwordFrame);
        });
        
        changeButton.setOnAction((event) -> {
            if (!newPassword1.getText().equals(newPassword2.getText())) {
                warning.setContentText("New password typed wrong!");
                warning.showAndWait();
            }
            else if (!userController.checkCredentials(newPassword1.getText().trim())) {
                warning.setContentText("Username and password must be 5-15 caharacters!");
                warning.showAndWait();
            }
            else {
                int changeInfo = userController.changePassword(newPassword1.getText().trim());
                    
                switch (changeInfo) {
                    case 0:
                        information.setContentText("Password changed succesfully!");
                        information.showAndWait();
                        break;
                    case 2:
                        error.setContentText("Change password failed. Try again later!");
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
            Optional<ButtonType> result = createAlert(confirmation,
                    "Are you sure you want to remove user " + userController.getUser().getUsername() + "?\n"
                            + "This will remove user and all it's data.");
            
            if (result.get().getButtonData().equals(ButtonData.APPLY)) {
                
                costController.emptyCostsCache(userController.getUser().getUsername());
                int removeUserInfo = userController.removeUser();
                
                switch (removeUserInfo) {
                    case 0:
                        createAlert(information, "User removed succesfully!");
                        logout(primaryStage, costsLayout, addCostFrame, infoText);
                        break;
                    case 2:
                        createAlert(error, "Remove user failed. Try again later!");
                        break;
                    default:
                        break;
                }
            }
        });
        
        userButtons.getChildren().addAll(logout, changePassword, removeUser);
        costsButtons.getChildren().addAll(addCost, viewCosts, summaryText, weekday, month,
                category, yearlyText, weekdayYearly, monthYearly, categoryYearly);
        
        costsButtons.getChildren().forEach(button -> button.getStyleClass().add("listbutton"));
        userButtons.getChildren().forEach(button -> button.getStyleClass().add("listbutton"));
        
        costsTab.setContent(costsLayout);
        userTab.setContent(userLayout);
        
        costsLayout.setLeft(costsButtons);
        costsLayout.setCenter(addCostFrame);
        userLayout.setLeft(userButtons);
        userLayout.setTop(infoPane);
        
        Scene scene = new Scene(application);
        scene.getStylesheets().add("styles.css");
        
        return scene;
    }
    
    public void logout (Stage primaryStage, BorderPane layout, GridPane addCostFrame, Label infoText) {
        costController.emptyCostsCache(userController.getUser().getUsername());
        userController.logoutUser();
        layout.setCenter(addCostFrame);
        infoText.setText("");
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
    
    public boolean isNumber (String number) {
        try {
            Double.parseDouble(number);
            
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
    
    public Optional<ButtonType> createAlert(Alert type, String text) {
        type.setContentText(text);
        return type.showAndWait();
    }
    
}
