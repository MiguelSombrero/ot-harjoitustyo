
package budgetapp;

import budgetapp.dao.DatabaseDao;
import budgetapp.dao.DbCostDao;
import budgetapp.dao.DbUserDao;
import budgetapp.domain.CostController;
import budgetapp.domain.UserController;
import budgetapp.gui.ApplicationView;
import budgetapp.gui.LoginView;
import java.io.FileInputStream;
import java.util.Properties;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BudgetApp extends Application {

    private UserController userController;
    private CostController costController;
    
    @Override
    public void init () throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));

        String path = properties.getProperty("databasePath");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String driver = properties.getProperty("driver");
        
        DatabaseDao database = new DatabaseDao(path, user, password, driver);
        DbUserDao userDao = new DbUserDao(path, user, password, driver);
        DbCostDao eventDao = new DbCostDao(path, user, password, driver);
        
        database.createDatabase();
        
        this.userController = new UserController(userDao);
        this.costController = new CostController(eventDao);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginView loginView = new LoginView(userController, costController);
        Scene loginScene = loginView.getLoginScene(primaryStage);
        
        ApplicationView appView = new ApplicationView(userController, costController, loginScene);
        Scene appScene = appView.getApplicationScene(primaryStage);
        
        loginView.setAppScene(appScene);
        
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }
    
    public static void main (String[] args) {
        launch(BudgetApp.class, args);
    }
}
