import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FilterFrame extends Stage {
	private ArrayList<String> priceResult = new ArrayList<String>();
	private ArrayList<String> typeResult = new ArrayList<String>();
	private ArrayList<String> timeResult = new ArrayList<String>();
	private ArrayList<String> locationResult = new ArrayList<String>();
	
    public FilterFrame() {
        Button button1 = new Button("Back to Welcome Page");
        button1.setOnAction(e -> {
            new FirstFrame().show();
            //this.close(); // Close current window
        });

        Button button2 = new Button("Go to Map");
        button2.setOnAction(e -> {
            new MapFrame().start(new Stage());
            //this.close(); // Close current window
        });
        
        Text title = new Text("Filter");
        title.setFont(Font.font("Serif", FontWeight.NORMAL, 25));
        
        Label q1 = new Label("價格");
        CheckBox c11 = new CheckBox("(1)");
        
        Label q2 = new Label("種類"); 
        HBox c2box = new HBox(10);
        String[] c2 = {"台式", "日式", "美式", "韓式", "餐酒館"};
        for(int i=0;i<c2.length;i++) {
        	CheckBox c = new CheckBox(c2[i]);
        	c2box.getChildren().add(c);
        	//c.setIndeterminate(true);
        	
        	if(c.isSelected()) {
        		typeResult.add(c2[i]);
        	}
        }
        
        Label q3 = new Label("預計用餐時間");
        HBox c3box = new HBox(10);
        String[] c3 = {"1小時內", "1-2小時", "3小時以上"};
        for(int i=0;i<c3.length;i++) {
        	CheckBox c = new CheckBox(c3[i]);
        	c3box.getChildren().add(c);
        	//c.setIndeterminate(true);
        	
        	if(c.isSelected()) {
        		timeResult.add(c3[i]);
        	}
        }
               
        Label q4 = new Label("地點");
        HBox c4box = new HBox(10);
        String[] c4 = {"新光路", "道南橋後", "東側", "麥側"};
        for(int i=0;i<c4.length;i++) {
        	CheckBox c = new CheckBox(c4[i]);
        	c4box.getChildren().add(c);
        	//c.setIndeterminate(true);
        	
        	if(c.isSelected()) {
        		locationResult.add(c4[i]);
        	}
        }
        CheckBox c41 = new CheckBox("新光路");
        CheckBox c42 = new CheckBox("道南橋後");
        CheckBox c43 = new CheckBox("東側");
        CheckBox c44 = new CheckBox("麥側");
        

        VBox layout = new VBox(10); // 10 is the spacing between elements in the VBox
        HBox buttonbox = new HBox(50);
        
        buttonbox.getChildren().addAll(button1, button2);
        
        layout.getChildren().addAll(title, q1, c11, q2, c2box, q3, c3box, q4, c4box, buttonbox);
        //layout.setAlignment(Pos.CENTER_LEFT);
        layout.setPadding(new Insets(10,10,10,10));

        Scene scene = new Scene(layout, 320, 350);
        this.setTitle("Filter Frame");
        this.setScene(scene);
		
		
    }
    
    public ArrayList<String> getSelectedPrice(){
    	return priceResult;
    }
    
    public ArrayList<String> getSelectedType(){
    	return typeResult;
    }
    
    public ArrayList<String> getSelectedTime(){
    	return timeResult;
    }
    
    public ArrayList<String> getSelectedLocation(){
    	return locationResult;
    }
}

