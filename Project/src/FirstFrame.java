import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FirstFrame extends Stage {

    private static final String DB_SERVER = "jdbc:mysql://140.119.19.73:3315/";
    private static final String DATABASE = "109304018";
    private static final String URL = DB_SERVER + DATABASE + "?useSSL=false";
    private static final String USERNAME = "109304018";
    private static final String PASSWORD = "x6ewb";

    public FirstFrame() {
        String[] resNames = new String[8];
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            System.out.println("DB in FirstFrame connected");

            Set<Integer> set = new HashSet<>();
            while (true) {
                int n = (int) (Math.random() * (35 - 1 + 1)) + 1;
                set.add(n);
                if (set.size() > 7) {
                    break;
                }
            }

            Statement stat = conn.createStatement();
            Object[] resID = set.toArray();

            for (int i = 0; i < resID.length; i++) {
                int n = (Integer) resID[i];
                String query = "SELECT `Name` FROM `restaurant` WHERE ID =" + n;
                stat.execute(query);
                ResultSet result = stat.getResultSet();
                resNames[i] = showResultSet(result);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Button button1 = new Button("Go to Filter");
        button1.setPrefWidth(120);
        button1.setPrefHeight(50);
        button1.setOnAction(e -> new FilterFrame().show());

        Button button2 = new Button("Go to Wheel");
        button2.setPrefWidth(120);
        button2.setPrefHeight(50);
        button2.setOnAction(e -> new RouletteFrame(resNames).show());

        // button(x,y)
        button1.setLayoutX(100); 
        button1.setLayoutY(200); 
        button2.setLayoutX(220); 
        button2.setLayoutY(200); 

        button1.setStyle("-fx-background-color: white; -fx-text-fill: #FAA381; -fx-font-size: 14px; -fx-font-family: 'Consolas'; -fx-font-weight: bold;");
        button2.setStyle("-fx-background-color: white; -fx-text-fill: #FAA381; -fx-font-size: 14px; -fx-font-family: 'Consolas'; -fx-font-weight: bold;");

        Text title = new Text("What to Eat?");
        title.setFont(Font.font("Consolas", FontWeight.BOLD, 40));
        title.setFill(Color.web("#FAA381"));

        VBox vbroot = new VBox(20);
        // 背景顏色
        vbroot.setStyle("-fx-background-color: #FCF6BD;");
        vbroot.setAlignment(Pos.CENTER);

        StackPane imagePane = new StackPane();

        // 圖片
        Image image = new Image("file:/C:/Users/Ann/Desktop/sticker (2).png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        imagePane.getChildren().add(imageView);

        HBox buttonBox = new HBox(60);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(button2, button1);

        vbroot.getChildren().addAll(title, imagePane, buttonBox);

        StackPane root = new StackPane(vbroot);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 320, 480);
        scene.getStylesheets().add("path/to/styles.css");

        this.setTitle("Frame1");
        this.setScene(scene);
    }

    public static String showResultSet(ResultSet result) throws SQLException {
        ResultSetMetaData metaData = result.getMetaData();
        int columnCount = metaData.getColumnCount();
        StringBuilder output = new StringBuilder();

        while (result.next()) {
            for (int i = 1; i <= columnCount; i++) {
                output.append(result.getString(i));
            }
            output.append("\n");
        }

        return output.toString();
    }
}
