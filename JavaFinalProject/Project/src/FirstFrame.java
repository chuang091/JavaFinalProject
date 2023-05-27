import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FirstFrame extends Stage {
    public FirstFrame() {
        Button button1 = new Button("Go to Filter");
        button1.setOnAction(e -> new FilterFrame().show());
        Button button2 = new Button("Go to wheel");
        Text title = new Text("WELCOME");
        title.setFont(Font.font("Serif", FontWeight.NORMAL, 40));

        VBox vbroot = new VBox(100);
        vbroot.setAlignment(Pos.CENTER);
        HBox titlebox = new HBox();
        titlebox.setAlignment(Pos.TOP_CENTER);
        HBox buttonbox = new HBox();
        buttonbox.setAlignment(Pos.BOTTOM_CENTER);
        
        titlebox.getChildren().addAll(title);
        buttonbox.getChildren().addAll(button2, button1);
        
        
        vbroot.getChildren().addAll(titlebox, buttonbox);
        
        button1.setPadding(new Insets(10,10,10,10));
        button2.setPadding(new Insets(10,10,10,10));


        Scene scene = new Scene(vbroot, 300, 250);
        this.setTitle("Frame1");
        this.setScene(scene);
    }
}
