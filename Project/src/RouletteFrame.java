import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
    private final String[] labels;

    public RouletteFrame(String[] labels) {
        this.labels = labels;

        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: white;");

        Group rouletteGroup = createRouletteGroup();
        Pane pane = new Pane();
        pane.getChildren().add(rouletteGroup);
        rouletteGroup.setTranslateX(75);
        rouletteGroup.setTranslateY(75);

        rotate = new RotateTransition();
        rotate.setNode(rouletteGroup);
        rotate.setDuration(Duration.seconds(14));
        rotate.setInterpolator(Interpolator.EASE_IN);
        rotate.setByAngle(7200);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.play();

        Button stopButton = new Button("Stop");
        stopButton.setOnAction(e -> {
            rotate.stop();

            RotateTransition decelerate = new RotateTransition(Duration.seconds(3.5), rouletteGroup);
            decelerate.setByAngle(360);
            decelerate.setInterpolator(Interpolator.EASE_OUT);
            decelerate.play();
        });
        Button restartButton = new Button("ReStart");
        restartButton.setOnAction(e -> {
            rotate.play();
        });

        Polygon pointer = new Polygon();
        pointer.getPoints().addAll(new Double[]{
            150.0, 90.0,
            145.0, 80.0,
            155.0, 80.0});
        pointer.setFill(Color.BLACK);
        pointer.setScaleX(2);
        pointer.setScaleY(2);

        BorderPane.setAlignment(pointer, Pos.TOP_CENTER);
        layout.setTop(pointer);
        layout.setCenter(pane);
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(stopButton, restartButton);
        layout.setBottom(vbox);

        Scene scene = new Scene(layout, 300, 300);
        this.setTitle("Roulette Frame");
        this.setScene(scene);
    }

    private Group createRouletteGroup() {
        Group group = new Group();
        Group textGroup = new Group();

        double centerX = 75;
        double centerY = 75;
        double radius = 100;

        for (int i = 0; i < this.labels.length; i++) {
            double angle = 360.0 / this.labels.length;
            Arc arc = new Arc(centerX, centerY, radius, radius, i * angle, angle);
            arc.setType(ArcType.ROUND);
            arc.setFill(i % 2 == 0 ? Color.BLACK : Color.RED);
            group.getChildren().add(arc);

            Text text = new Text(this.labels[i]);
            text.setFont(Font.font(12));
            text.setFill(i % 2 == 0 ? Color.WHITE : Color.WHITE);
            text.relocate(centerX + 0.7 * radius * Math.cos(Math.toRadians(i * angle + angle/2)) - text.getLayoutBounds().getWidth()/2,
            centerY + 0.7 * radius * Math.sin(Math.toRadians(i * angle + angle/2)) + text.getLayoutBounds().getHeight()/4);
            textGroup.getChildren().add(text);
        }

        group.getChildren().add(textGroup);

        return group;
    }
}
