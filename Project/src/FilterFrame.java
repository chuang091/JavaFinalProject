import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FilterFrame extends Stage {
    public FilterFrame() {
        Button button1 = new Button("Back to Welcome Page");
        button1.setOnAction(e -> {
            new FirstFrame().show();
            //this.close(); // Close current window
        });

        Button button2 = new Button("Go to Map");
        button2.setOnAction(e -> {
            new MapFrame().start(new Stage());
            //this.close(); // Close current window
        });
        Button button3 = new Button("Go to Roulette");
        button3.setOnAction(e -> {
            new RouletteFrame().show();
            //this.close(); // Close current window
        });

        VBox layout = new VBox(10); // 10 is the spacing between elements in the VBox
        layout.getChildren().addAll(button1, button2, button3);

        Scene scene = new Scene(layout, 300, 250);
        this.setTitle("Filter Frame");
        this.setScene(scene);
    }
}
