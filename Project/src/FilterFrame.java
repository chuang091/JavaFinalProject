import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

		Button button1 = new Button("Back to Welcome Page");
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

		    // Check if no options are selected, then select all options by default
		    if (selectedTypes.isEmpty()) {
		        // Select all types
		        selectedTypes = getAllTypes(); // Replace getAllTypes() with the method that returns all available types
		    }
		    if (selectedTimes.isEmpty()) {
		        // Select all times
		        selectedTimes = getAllTimes(); // Replace getAllTimes() with the method that returns all available times
		    }
		    if (selectedLocations.isEmpty()) {
		        // Select all locations
		        selectedLocations = getAllLocations(); // Replace getAllLocations() with the method that returns all available locations
		    }

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
		            int id = resultSet.getInt("ID");
		            String name = resultSet.getString("Name");
		            double lowerLimit = resultSet.getDouble("P_lower_limit");
		            double higherLimit = resultSet.getDouble("P_higher_limit");
		            int veg = resultSet.getInt("Veg");
		            String type = resultSet.getString("Type");
		            String diningTime = resultSet.getString("Dining_time");
		            String position = resultSet.getString("Position");
		            double rate = resultSet.getDouble("Rate");
		            double latitude = resultSet.getDouble("Latitude");
		            double longitude = resultSet.getDouble("Longitude");
		            selectedPlacesList.add(new Place(id, name, lowerLimit, higherLimit, veg, type, diningTime, position, rate, latitude, longitude));
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
		title.setFont(Font.font("Serif", FontWeight.NORMAL, 25));

		Label q1 = new Label("價格");
		//CheckBox c11 = new CheckBox("(1)");

		Label q2 = new Label("種類");
		HBox c2box = new HBox(10);
		String[] c2 = { "台式", "日式", "美式", "韓式", "餐酒館" };
		for (int i = 0; i < c2.length; i++) {
			CheckBox c = new CheckBox(c2[i]);
			c2box.getChildren().add(c);
			// c.setIndeterminate(true);

			final int index = i;

			c.selectedProperty().addListener(
					(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
						if (newValue) {
							typeResult.add(c2[index]);
						} else {
							typeResult.remove(c2[index]);
						}
					});
		}

		Label q3 = new Label("預計用餐時間");
		HBox c3box = new HBox(10);
		String[] c3 = { "1小時內", "1-2小時", "3小時以上" };
		for (int i = 0; i < c3.length; i++) {
			CheckBox c = new CheckBox(c3[i]);
			c3box.getChildren().add(c);
			// c.setIndeterminate(true);

			final int index = i;

			c.selectedProperty().addListener(
					(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
						if (newValue) {
							timeResult.add(c3[index]);
						} else {
							timeResult.remove(c3[index]);
						}
					});
		}

		Label q4 = new Label("地點");
		HBox c4box = new HBox(10);
		String[] c4 = { "新光路", "道南橋後", "東側", "麥側" };
		for (int i = 0; i < c4.length; i++) {
			CheckBox c = new CheckBox(c4[i]);
			c4box.getChildren().add(c);
			// c.setIndeterminate(true);

			final int index = i;

			c.selectedProperty().addListener(
					(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
						if (newValue) {
							locationResult.add(c4[index]);
						} else {
							locationResult.remove(c4[index]);
						}
					});
		}
		// CheckBox c41 = new CheckBox("新光路");
		// CheckBox c42 = new CheckBox("道南橋後");
		// CheckBox c43 = new CheckBox("東側");
		// CheckBox c44 = new CheckBox("麥側");

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

		VBox layout = new VBox(10); // 10 is the spacing between elements in the VBox
		HBox buttonbox = new HBox(50);

		buttonbox.getChildren().addAll(button1, button2);

		layout.getChildren().addAll(title, q1, slider, q2, c2box, q3, c3box, q4, c4box, buttonbox);
		// layout.setAlignment(Pos.CENTER_LEFT);
		layout.setPadding(new Insets(10, 10, 10, 10));

		Scene scene = new Scene(layout, 320, 350);
		this.setTitle("Filter Frame");
		this.setScene(scene);

	}



	public ArrayList<String> getAllTypes() {
	    ArrayList<String> allTypes = new ArrayList<>();
	    allTypes.add("台式");
	    allTypes.add("日式");
	    allTypes.add("美式");
	    allTypes.add("韓式");
	    allTypes.add("餐酒館");
	    return allTypes;
	}

	public ArrayList<String> getAllTimes() {
	    ArrayList<String> allTimes = new ArrayList<>();
	    allTimes.add("1小時內");
	    allTimes.add("1-2小時");
	    allTimes.add("3小時以上");
	    return allTimes;
	}

	public ArrayList<String> getAllLocations() {
	    ArrayList<String> allLocations = new ArrayList<>();
	    allLocations.add("新光路");
	    allLocations.add("道南橋後");
	    allLocations.add("東側");
	    allLocations.add("麥側");
	    return allLocations;
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
