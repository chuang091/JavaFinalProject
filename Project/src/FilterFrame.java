import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FilterFrame extends Stage {
	private ArrayList<String> typeResult = new ArrayList<String>();
	private ArrayList<String> timeResult = new ArrayList<String>();
	private ArrayList<String> locationResult = new ArrayList<String>();
	private double priceMax=400;

	public FilterFrame() {

		Button button1 = new Button("Back to Home");
		button1.setOnAction(e -> {
			new FirstFrame().show();
			// this.close(); // Close current window
		});

		Button button2 = new Button("Go to Map");
		button2.setOnAction(e -> {
		    String server = "jdbc:mysql://140.119.19.73:3315/";
		    String database = "109304018"; 
		    String url = server + database + "?useSSL=false";
		    String username = "109304018"; 
		    String password = "x6ewb";
		    
		    // Get the selected filters
		    ArrayList<String> selectedTypes = getSelectedType();
		    ArrayList<String> selectedTimes = getSelectedTime();
		    ArrayList<String> selectedLocations = getSelectedLocation();
		    double selectedPriceMax = getPriceMax();

		    try (Connection conn = DriverManager.getConnection(url, username, password)) {
		        // Build a query with a ? placeholder for each selected item
		        String query = String.format(
		            "SELECT * FROM restaurant " +
		            "WHERE Type IN (%s) " +
		            "AND Dining_time IN (%s) " +
		            "AND Position IN (%s) " +
		            "AND P_higher_limit <= ?",
		            String.join(", ", Collections.nCopies(selectedTypes.size(), "?")),
		            String.join(", ", Collections.nCopies(selectedTimes.size(), "?")),
		            String.join(", ", Collections.nCopies(selectedLocations.size(), "?"))
		        );

		        PreparedStatement preparedStatement = conn.prepareStatement(query);

		        int i = 1;
		        for (String type : selectedTypes) {
		            preparedStatement.setString(i++, type);
		        }
		        for (String time : selectedTimes) {
		            preparedStatement.setString(i++, time);
		        }
		        for (String location : selectedLocations) {
		            preparedStatement.setString(i++, location);
		        }
		        preparedStatement.setDouble(i, selectedPriceMax);

		        ArrayList<Place> selectedPlacesList = new ArrayList<>();

		        // Execute the query
		        ResultSet resultSet = preparedStatement.executeQuery();
		        while (resultSet.next()) {
		            String name = resultSet.getString("Name");
		            double latitude = resultSet.getDouble("Latitude");
		            double longitude = resultSet.getDouble("Longitude");
		            selectedPlacesList.add(new Place(latitude, longitude, name));
		            //System.out.println(name);
		        }

		        // Convert the ArrayList to an array
		        Place[] selectedPlaces = selectedPlacesList.toArray(new Place[0]);

		        new MapFrame(selectedPlaces).start(new Stage());
		     // this.close(); // Close current window
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
		});


		Text title = new Text("Filter");
		title.setFont(Font.font("Consolas", FontWeight.BOLD, 40));
		title.setFill(Color.web("#FAA381"));
		title.setLayoutX(10);
		title.setLayoutY(10);

		Label q1 = new Label("種類");
		q1.setFont(Font.font("Microsoft YaHei", 13));
		HBox c1box = new HBox(10);
		String[] c1 = { "台式", "日式", "美式", "韓式", "餐酒館" };
		for (int i = 0; i < c1.length; i++) {
			CheckBox c = new CheckBox(c1[i]);
			c1box.getChildren().add(c);
			// c.setIndeterminate(true);

			final int index = i;

			c.selectedProperty().addListener(
					(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
						if (newValue) {
							typeResult.add(c1[index]);
						} else {
							typeResult.remove(c1[index]);
						}
					});
		}

		Label q2 = new Label("預計用餐時間");
		q2.setFont(Font.font("Microsoft YaHei", 13));
		HBox c2box = new HBox(10);
		String[] c2 = { "1小時內", "1-2小時", "3小時以上" };
		for (int i = 0; i < c2.length; i++) {
			CheckBox c = new CheckBox(c2[i]);
			c2box.getChildren().add(c);
			// c.setIndeterminate(true);

			final int index = i;

			c.selectedProperty().addListener(
					(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
						if (newValue) {
							timeResult.add(c2[index]);
						} else {
							timeResult.remove(c2[index]);
						}
					});
		}

		Label q3 = new Label("地點");
		q3.setFont(Font.font("Microsoft YaHei", 13));
		HBox c3box = new HBox(10);
		String[] c3 = { "新光路", "道南橋後", "東側", "麥側" };
		for (int i = 0; i < c3.length; i++) {
			CheckBox c = new CheckBox(c3[i]);
			c3box.getChildren().add(c);
			// c.setIndeterminate(true);

			final int index = i;

			c.selectedProperty().addListener(
					(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
						if (newValue) {
							locationResult.add(c3[index]);
						} else {
							locationResult.remove(c3[index]);
						}
					});
		}
		// CheckBox c31 = new CheckBox("新光路");
		// CheckBox c32 = new CheckBox("道南橋後");
		// CheckBox c33 = new CheckBox("東側");
		// CheckBox c34 = new CheckBox("麥側");

		// TODO slider part
		Slider slider = new Slider();

		slider.setMin(0);
		slider.setMax(1000);
		slider.setValue(400);

		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);

		slider.setBlockIncrement(50);
		slider.valueProperty()
				.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
					priceMax = newValue.doubleValue();
				});
		
		
		// TODO slider part

		VBox layout = new VBox(20); // 10 is the spacing between elements in the VBox
		HBox buttonbox = new HBox(60);
		layout.setStyle("-fx-background-color: #FCF6BD;");

		button1.setStyle("-fx-background-color: white; -fx-text-fill: #FAA381; -fx-font-size: 14px; -fx-font-family: 'Consolas'; -fx-font-weight: bold;");
        button2.setStyle("-fx-background-color: white; -fx-text-fill: #FAA381; -fx-font-size: 14px; -fx-font-family: 'Consolas'; -fx-font-weight: bold;");
        button1.setPrefWidth(120);
        button1.setPrefHeight(50);
        button2.setPrefWidth(120);
        button2.setPrefHeight(50);
        // button(x,y)
        button1.setLayoutX(70); 
        button1.setLayoutY(200); 
        button2.setLayoutX(210); 
        button2.setLayoutY(200); 
		buttonbox.getChildren().addAll(button1, button2);
		

		layout.getChildren().addAll(title, slider, q1, c1box, q2, c2box, q3, c3box, buttonbox);
		layout.setAlignment(Pos.CENTER_LEFT);
		layout.setPadding(new Insets(10, 10, 10, 10));

		Scene scene = new Scene(layout, 320, 480);
		this.setTitle("Filter Frame");
		this.setScene(scene);

	}

	public double getPriceMax() {
		return priceMax;
	}

	public ArrayList<String> getSelectedType() {
		return typeResult;
	}

	public ArrayList<String> getSelectedTime() {
		return timeResult;
	}

	public ArrayList<String> getSelectedLocation() {
		return locationResult;
	}
}
