import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MapFrame extends Application {

	private static final String HTML = "<!DOCTYPE html>\r\n"
            + "<html>\r\n"
            + "  <head>\r\n"
            + "    <title>Simple Map</title>\r\n"
            + "    <script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyCpZC_JNaWH4qN4y5QauFxaOHxhH55jrqk\"></script>\r\n"
            + "    <script>\r\n"
            + "      var map;\r\n"
            + "      function initialize() {\r\n"
            + "        map = new google.maps.Map(document.getElementById('map'), {\r\n"
            + "          center: {lat: 24.987585, lng: 121.5759248},\r\n"
            + "          zoom: 14\r\n"
            + "        });\r\n"
            + "        var marker1 = new google.maps.Marker({\r\n"
            + "          position: {lat: 24.986936, lng: 121.576880},\r\n"
            + "          map: map,\r\n"
            + "          icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'\r\n"
            + "        });\r\n"
            + "        var marker2 = new google.maps.Marker({\r\n"
            + "          position: {lat: 24.9914219, lng: 121.5735300},\r\n"
            + "          map: map\r\n"
            + "        });\r\n"
            + "        var infoWindow = new google.maps.InfoWindow({\r\n"
            + "          content: '李氏餐酒館'\r\n"
            + "        });\r\n"
            + "        marker2.addListener('click', function() {\r\n"
            + "          infoWindow.open(map, marker2);\r\n"
            + "        });\r\n"
            + "      }\r\n"
            + "    </script>\r\n"
            + "  </head>\r\n"
            + "  <body onload=\"initialize()\">\r\n"
            + "    <div id=\"map\" style=\"height: 100vh; width: 100%\"></div>\r\n"
            + "  </body>\r\n"
            + "</html>";





    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView(); //map
        WebEngine webEngine = webView.getEngine();
        webEngine.loadContent(HTML);

        VBox webViewContainer = new VBox(webView);

        Button button1 = new Button("Go to Filter");
        button1.setOnAction(e -> new FilterFrame().show());

        VBox buttonContainer = new VBox(button1);
        
        Label label = new Label("這邊會放餐廳內容 要再想怎麼呈現");
        VBox textContainer = new VBox(label);

        BorderPane layout = new BorderPane();
        layout.setTop(buttonContainer);
        layout.setCenter(textContainer);
        layout.setBottom(webViewContainer);

        Scene scene = new Scene(layout, 500, 500);
        webViewContainer.setMaxHeight(250);
        
        // responsive design
        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            webViewContainer.setMaxHeight(newValue.doubleValue() / 2);
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }



    }

