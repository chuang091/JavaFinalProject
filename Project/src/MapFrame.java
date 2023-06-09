import java.util.HashSet;
import java.util.Set;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MapFrame extends Application {

    private final Place[] places;
    private final ObservableList<SelectedPlace> dataPlace = FXCollections.observableArrayList();

    public MapFrame(Place[] places) {
        this.places = places;
    }
    private static String generateHtml(Place[] places) {
        StringBuilder markers = new StringBuilder();
        for (int i = 0; i < places.length; i++) {
            markers.append(String.format(
                "var marker%d = new google.maps.Marker({\r\n" +
                "  position: {lat: %f, lng: %f},\r\n" +
                "  map: map,\r\n" +
                "  icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'\r\n" +
                "});\r\n" +
                "var infoWindow%d = new google.maps.InfoWindow({\r\n" +
                "  content: '%s'\r\n" +
                "});\r\n" +
                "marker%d.addListener('click', function() {\r\n" +
                "  infoWindow%d.open(map, marker%d);\r\n" +
                "});\r\n", i, places[i].lat, places[i].lng, i, places[i].name, i, i, i));
        }
        return String.format("<!DOCTYPE html>\r\n" +
            "<html>\r\n" +
            "  <head>\r\n" +
            "    <title>Simple Map</title>\r\n" +
            "    <script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyCpZC_JNaWH4qN4y5QauFxaOHxhH55jrqk\"></script>\r\n" +
            "    <script>\r\n" +
            "      var map;\r\n" +
            "      function initialize() {\r\n" +
            "        map = new google.maps.Map(document.getElementById('map'), {\r\n" +
            "          center: {lat: 24.987585, lng: 121.5759248},\r\n" +
            "          zoom: 14\r\n" +
            "        });\r\n" +
            "%s" +
            "      }\r\n" +
            "    </script>\r\n" +
            "  </head>\r\n" +
            "  <body onload=\"initialize()\">\r\n" +
            "    <div id='map' style=\"height: 100vh; width: 100%%\"></div>\r\n" +
            "  </body>\r\n" +
            "</html>", markers.toString());
    }

    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView(); //map
        WebEngine webEngine = webView.getEngine();
        webEngine.loadContent(generateHtml(this.places));

        VBox webViewContainer = new VBox(webView);

        Button button1 = new Button("Back to Filter");
        button1.setOnAction(e -> new FilterFrame().show());
        Button button2 = new Button("Quick selction by Wheel");
        button2.setOnAction(e ->{
        	if(places.length<=6) {
        		String[] names = new String[places.length];
        		for(int i=0; i<places.length;i++) {
        			names[i] = places[i].name;
        		}
        		new RouletteFrame(names).show();
        	}else {
        		String[] names = new String[6];
        		Set<Object> set = new HashSet<>();
    	        while(true) {
    	        	int max = places.length-1;
    	            int n = (int)(Math.random()*(max-0+1))+1;
    	            //System.out.println(n);
    	            set.add(n);
    	            if(set.size()==6) {
    	                break;
    	            }
    	        }
    	        Object[] index = set.toArray();
    	        
    	        for(int i=0; i<index.length;i++) {
        			names[i] = places[(Integer)index[i]].name;
        		}
        		new RouletteFrame(names).show();
        	}
        });


        String c1 = "依價格排序";
        String c2 = "依評價排序";
        ChoiceBox<String> cb = new ChoiceBox<String>();
        cb.getItems().addAll(c1,c2);

       
        TableView resulttable = new TableView();
        TableColumn nameCol = new TableColumn("name");
        TableColumn priceCol = new TableColumn("price");
        TableColumn rateCol = new TableColumn("rate");
        resulttable.setEditable(false);
        resulttable.getColumns().addAll(nameCol, rateCol, priceCol);
        for(int i=0;i<places.length;i++) {
        	dataPlace.add(new SelectedPlace(places[i].name, places[i].rate, places[i].pLLimit));
        }
        nameCol.setCellFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellFactory(new PropertyValueFactory<>("price"));
        rateCol.setCellFactory(new PropertyValueFactory<>("rate"));
        resulttable.setItems(dataPlace);
        
        Text result = new Text("RESULT");
        result.setFont(Font.font("Serif", FontWeight.NORMAL, 25));
        
        
        
        TextArea output = new TextArea(getInfo());
        output.setEditable(false);
        VBox textContainer = new VBox(output);
        
        VBox buttonContainer = new VBox();
        buttonContainer.getChildren().addAll(cb, resulttable, button1, button2);
       // output.setAlignment(Pos.BASELINE_RIGHT);
        button1.setAlignment(Pos.BOTTOM_RIGHT);
        
        cb.setOnAction((event) ->{
        	String order = (String) cb.getValue();
        	if(order.equals("依評價排序")) {
        		for (int i = 0; i < places.length; i++){  
        			for (int j = i + 1; j < places.length; j++){  
        				Place tmp;  
        				if (places[i].rate > places[j].rate){  
        					tmp = places[i];  
        					places[i] = places[j];  
        					places[j] = tmp;  
        				}  
        			}  
        		}
        		output.setText(getInfo());
        	}
        	
        	if(order.equals("依價格排序")) {
        		for (int i = 0; i < places.length; i++){  
        			for (int j = i + 1; j < places.length; j++){  
        				Place tmp;  
        				if (places[i].pLLimit > places[j].pLLimit){  
        					tmp = places[i];  
        					places[i] = places[j];  
        					places[j] = tmp;  
        				}  
        			}  
        		}
        		output.setText(getInfo());
        	}
        });

        BorderPane layout = new BorderPane();
        layout.setTop(textContainer);
        layout.setCenter(buttonContainer);
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
    
    public String getInfo() {
    	String info = "RESULT";
        for(int i=0; i<places.length;i++) {
        	
        	info+="\n"+places[i].name+places[i].rate+"..."+places[i].pLLimit;
        }
        return info;
    }
    
    
}

class SelectedPlace {
	private SimpleStringProperty name;
	private SimpleDoubleProperty rate;
	private SimpleIntegerProperty avgPrice;
	
	public SelectedPlace(String name, double rate, int avgprice) {
		this.name = new SimpleStringProperty(name);
		this.rate = new SimpleDoubleProperty(rate);
		this.avgPrice = new SimpleIntegerProperty(avgprice);		
	}
	
	public String getName() {
		return name.get();
	}
	
	public double getRate() {
		return rate.get();
	}
	
	public int getPrice() {
		return avgPrice.get();
	}
}
