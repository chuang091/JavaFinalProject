import javax.swing.JRadioButton;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
        
        Text title = new Text("Filter");
        title.setFont(Font.font("Serif", FontWeight.NORMAL, 25));
        
        Label q1 = new Label("Price");
        RadioButton r1 = new RadioButton("(1)");
        
        Label q2 = new Label("Food");
        RadioButton r2 = new RadioButton("(1)");
        
        Label q3 = new Label("Number of people");
        RadioButton r3 = new RadioButton("(1)");
        
        Label q4 = new Label("Purpose");
        RadioButton r4 = new RadioButton("(1)");
        
        

        VBox layout = new VBox(10); // 10 is the spacing between elements in the VBox
        HBox buttonbox = new HBox(50);
        
        buttonbox.getChildren().addAll(button1, button2);
        
        layout.getChildren().addAll(title, q1, r1, q2, r2, q3, r3, q4, r4, buttonbox);
        //layout.setAlignment(Pos.CENTER_LEFT);
        layout.setPadding(new Insets(10,10,10,10));

        Scene scene = new Scene(layout, 300, 350);
        this.setTitle("Filter Frame");
        this.setScene(scene);
    }
}
