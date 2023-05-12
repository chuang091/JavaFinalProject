import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
//import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Group;

public class RouletteFrame extends Stage {
    private RotateTransition rotate;

    public RouletteFrame() {
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: white;");

        Group rouletteGroup = createRouletteGroup();
        Pane pane = new Pane();
        pane.getChildren().add(rouletteGroup);
        rouletteGroup.setTranslateX(75); // Center the group
        rouletteGroup.setTranslateY(75); // Center the group

        // Create a rotate animation //動畫properties
        rotate = new RotateTransition();
        rotate.setNode(rouletteGroup);
        rotate.setDuration(Duration.seconds(5));
        rotate.setByAngle(360);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.play();

        Button stopButton = new Button("Stop");
        stopButton.setOnAction(e -> {
            rotate.stop();
        });
        Button restartButton = new Button("ReStart");
        restartButton.setOnAction(e -> {
            rotate.play();
        });

        Polygon pointer = new Polygon();
        pointer.getPoints().addAll(new Double[]{
            140.0, 30.0,
            150.0, 60.0,
            160.0, 30.0 }); //調大小的
        pointer.setFill(Color.BLACK);

        BorderPane.setAlignment(pointer, Pos.TOP_CENTER);
        layout.setTop(pointer);
        layout.setCenter(pane);
        HBox hbox = new HBox(10); // 10 is the spacing between elements in the VBox
        hbox.getChildren().addAll(stopButton, restartButton);
        layout.setBottom(hbox);

        Scene scene = new Scene(layout, 300, 300);
        this.setTitle("Roulette Frame");
        this.setScene(scene);
    }

    private Group createRouletteGroup() {
        Group group = new Group();

        double centerX = 75;
        double centerY = 75;
        double radius = 100;

        for (int i = 0; i < 8; i++) {
            Arc arc = new Arc(centerX, centerY, radius, radius, i * 45, 45);
            arc.setType(ArcType.ROUND);
            arc.setFill(i % 2 == 0 ? Color.WHITE : Color.RED);
            group.getChildren().add(arc);

            // Create text and add it to the group
            Text text = new Text("Section " + (i + 1));
            text.setFont(new Font(5)); // Set the font size to ensure the text is visible

            // Calculate position of the text
            double angle = Math.toRadians(i * 45 + 22.5); // Convert to radians and adjust to center of the arc
            double textX = centerX + (radius / 2) * Math.cos(angle); 
            double textY = centerY + (radius / 2) * Math.sin(angle);

            text.setX(textX - text.getLayoutBounds().getWidth() / 2); // Center the text
            text.setY(textY);

            group.getChildren().add(text);
        }

        return group;
    }

}
