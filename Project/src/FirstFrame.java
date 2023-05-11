import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FirstFrame extends Stage {
    public FirstFrame() {
        Button button1 = new Button("Go to Filter");
        button1.setOnAction(e -> new FilterFrame().show());

        StackPane layout = new StackPane();
        layout.getChildren().add(button1);


        Scene scene = new Scene(layout, 300, 250);
        this.setTitle("Frame1");
        this.setScene(scene);
    }
}
