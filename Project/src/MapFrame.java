import java.util.Comparator;
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
import javafx.scene.layout.HBox;
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
		for (Place place : places) {
			dataPlace.add(new SelectedPlace(place.name, place.rate, place.avgprice));
		}
	}

	private static String generateHtml(Place[] places) {
		StringBuilder markers = new StringBuilder();
		for (int i = 0; i < places.length; i++) {
			markers.append(String.format(
					"var marker%d = new google.maps.Marker({\r\n" + "  position: {lat: %f, lng: %f},\r\n"
							+ "  map: map,\r\n" + "  icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'\r\n"
							+ "});\r\n" + "var infoWindow%d = new google.maps.InfoWindow({\r\n" + "  content: '%s'\r\n"
							+ "});\r\n" + "marker%d.addListener('click', function() {\r\n"
							+ "  infoWindow%d.open(map, marker%d);\r\n" + "});\r\n",
					i, places[i].lat, places[i].lng, i, places[i].name, i, i, i));
		}
		return String.format("<!DOCTYPE html>\r\n" + "<html>\r\n" + "  <head>\r\n" + "    <title>Simple Map</title>\r\n"
				+ "    <script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyCpZC_JNaWH4qN4y5QauFxaOHxhH55jrqk\"></script>\r\n"
				+ "    <script>\r\n" + "      var map;\r\n" + "      function initialize() {\r\n"
				+ "        map = new google.maps.Map(document.getElementById('map'), {\r\n"
				+ "          center: {lat: 24.987585, lng: 121.5759248},\r\n" + "          zoom: 14\r\n"
				+ "        });\r\n" + "%s" + "      }\r\n" + "    </script>\r\n" + "  </head>\r\n"
				+ "  <body onload=\"initialize()\">\r\n"
				+ "    <div id='map' style=\"height: 100vh; width: 100%%\"></div>\r\n" + "  </body>\r\n" + "</html>",
				markers.toString());
	}

	@Override
	public void start(Stage primaryStage) {
		WebView webView = new WebView(); // map
		WebEngine webEngine = webView.getEngine();
		webEngine.loadContent(generateHtml(this.places));

		VBox webViewContainer = new VBox(webView);

        Button button1 = new Button("Back to Filter");
        button1.setStyle("-fx-background-color: white; -fx-text-fill: #FAA381; -fx-font-size: 14px; -fx-font-family: 'Consolas'; -fx-font-weight: bold;");
        button1.setPrefWidth(140);
        button1.setPrefHeight(50);
		button1.setOnAction(e ->{ new FilterFrame().show();
		primaryStage.close();
		});
		Button button2 = new Button("Quick selction");
		button2.setStyle("-fx-background-color: white; -fx-text-fill: #FAA381; -fx-font-size: 14px; -fx-font-family: 'Consolas'; -fx-font-weight: bold;");
		button2.setPrefWidth(140);
        button2.setPrefHeight(50);
		
		button2.setOnAction(e -> {
			if (places.length <= 6) {
				String[] names = new String[places.length];
				for (int i = 0; i < places.length; i++) {
					names[i] = places[i].name;
				}
				new RouletteFrame(names).show();
			} else {
				String[] names = new String[6];
				Set<Object> set = new HashSet<>();
				while (true) {
					int max = places.length - 1;
					int n = (int) (Math.random() * (max - 0 + 1)) + 1;
					// System.out.println(n);
					set.add(n);
					if (set.size() == 6) {
						break;
					}
				}
				Object[] index = set.toArray();

				for (int i = 0; i < index.length; i++) {
					names[i] = places[(Integer) index[i]].name;
				}
				new RouletteFrame(names).show();
			}
			primaryStage.close();
		});

		String c1 = "依價格排序";
		String c2 = "依評價排序";
		ChoiceBox<String> cb = new ChoiceBox<String>();
		cb.getItems().addAll(c1, c2);
		cb.setValue("依評價排序");
		cb.setStyle("-fx-background-color: white; -fx-font-size: 14px; -fx-font-family: 'Consolas'; -fx-font-weight: bold;");
		ChoiceBox<String> comboBox = new ChoiceBox<String>();
		comboBox.getItems().addAll("升序", "降序");
		comboBox.setValue("升序"); // Set the default selected option
		comboBox.setStyle("-fx-background-color: white; -fx-font-size: 14px; -fx-font-family: 'Consolas'; -fx-font-weight: bold;");

		TableView<SelectedPlace> resulttable = new TableView<>();
		TableColumn<SelectedPlace, String> nameCol = new TableColumn<>("name");
		TableColumn<SelectedPlace, Double> rateCol = new TableColumn<>("rate");
		TableColumn<SelectedPlace, Double> priceCol = new TableColumn<>("price");

		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		rateCol.setCellValueFactory(new PropertyValueFactory<>("rate"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("avgPrice"));

		resulttable.setItems(dataPlace);
		resulttable.getColumns().addAll(nameCol, rateCol, priceCol);

		Text result = new Text("RESULT");
		result.setFont(Font.font("Serif", FontWeight.NORMAL, 25));

		// TextArea output = new TextArea(getInfo());
		// output.setEditable(false);
		// VBox textContainer = new VBox(output);

		VBox buttonContainer = new VBox();
		HBox h1 = new HBox(10);
		h1.getChildren().addAll(cb, comboBox);
		buttonContainer.getChildren().addAll(h1, resulttable);
		HBox h2 = new HBox(40);
		h2.getChildren().addAll(button1, button2);
		webViewContainer.getChildren().addAll(h2);
		// output.setAlignment(Pos.BASELINE_RIGHT);

		cb.setOnAction(event -> {
			String order = cb.getValue();
			if (order.equals("依評價排序")) {
				Comparator<SelectedPlace> rateComparator = Comparator.comparingDouble(SelectedPlace::getRate);
				FXCollections.sort(dataPlace, rateComparator);
			} else if (order.equals("依價格排序")) {
				Comparator<SelectedPlace> priceComparator = Comparator.comparingDouble(SelectedPlace::getAvgPrice);
				FXCollections.sort(dataPlace, priceComparator);
			}
		});

		boolean[] isDescendingOrder = { false }; // Default sorting order is ascending
		comboBox.setOnAction(event -> {
			String order = comboBox.getValue();
			if (order.equals("降序")) {
				isDescendingOrder[0] = true;
			} else {
				isDescendingOrder[0] = false;
			}
			String sortingOption = cb.getValue();
			if (sortingOption.equals("依評價排序")) {
				Comparator<SelectedPlace> rateComparator = Comparator.comparingDouble(SelectedPlace::getRate);
				if (isDescendingOrder[0]) {
					rateComparator = rateComparator.reversed();
				}
				FXCollections.sort(dataPlace, rateComparator);
			} else if (sortingOption.equals("依價格排序")) {
				Comparator<SelectedPlace> priceComparator = Comparator.comparingDouble(SelectedPlace::getAvgPrice);
				if (isDescendingOrder[0]) {
					priceComparator = priceComparator.reversed();
				}
				FXCollections.sort(dataPlace, priceComparator);
			}
		});

        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: #FCF6BD;");
        layout.setCenter(buttonContainer);
        
        layout.setBottom(webViewContainer);

        Scene scene = new Scene(layout, 320, 480);
        webViewContainer.setMaxHeight(250);

		// responsive design
		scene.heightProperty().addListener((observable, oldValue, newValue) -> {
			webViewContainer.setMaxHeight(newValue.doubleValue() / 2);
		});

		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
