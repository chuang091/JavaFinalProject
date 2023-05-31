import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

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
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class FirstFrame extends Stage {
	public FirstFrame() {
	    //database
	    String server = "jdbc:mysql://140.119.19.73:3315/";
	    String database = "109304018"; 
	    String url = server + database + "?useSSL=false";
	    String username = "109304018"; 
	    String password = "x6ewb";
	    
	    String[] resNames = new String[8];
	    
	    try (Connection conn = DriverManager.getConnection(url, username, password)) {
	        System.out.println("DB in FirstFrame connected");
	        Set<Object> set = new HashSet<>();
	        while(true) {
	            int n = (int)(Math.random()*(35-1+1))+1;
	            //System.out.println(n);
	            set.add(n);
	            if(set.size()>7) {
	                break;
	            }
	        }
	        Object[] resID = set.toArray(); 
	        //String[] resNames = new String[8];
	        
	        Statement stat = conn.createStatement();
	        
	        for(int i=0; i<resID.length;i++) {
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
        button1.setOnAction(e -> new FilterFrame().show());
        Button button2 = new Button("Go to wheel");
        button2.setOnAction(e -> new RouletteFrame(resNames).show());
        
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
    
    public static String showResultSet(ResultSet result) throws SQLException {
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		String output = "";
		//for (int i = 1; i <= columnCount; i++) {
		//	output += String.format(metaData.getColumnLabel(i));
		//}
		//output += "\n";
		while (result.next()) {
			for (int i = 1; i <= columnCount; i++) {
				output += String.format(result.getString(i));
			}
			output += "\n";
		}
		//System.out.print(output);
		return output;
	}
}
