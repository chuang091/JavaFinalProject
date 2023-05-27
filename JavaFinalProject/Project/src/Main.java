import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
        System.out.print("hi");
        System.out.print("hi");
    }

    @Override
    public void start(Stage primaryStage) {
        new FirstFrame().show();
    }
}
