import javafx.application.Application;
import javafx.scene.Scene;
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
            + "          zoom: 16.65\r\n"
            + "        });\r\n"
            + "      }\r\n"
            + "    </script>\r\n"
            + "  </head>\r\n"
            + "  <body onload=\"initialize()\">\r\n"
            + "    <div id=\"map\" style=\"height: 100vh; width: 100%\"></div>\r\n"
            + "  </body>\r\n"
            + "</html>\"";

    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.loadContent(HTML);
        primaryStage.setScene(new Scene(webView));
        primaryStage.show();
    }
}
