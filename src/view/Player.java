package view;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class Player extends HBox{

	public Player() {
		setSpacing(1);
		setAlignment(Pos.BASELINE_CENTER);
		
		Button openButton = new Button("Open"); 
		
		Button b1 = new Button("<<<"); 
		Button b2 = new Button("<<"); 
		//Creating the play button 
		Button playButton = new Button("Play");   
		  
		//Creating the stop button 
		Button pauseButton = new Button("pause"); 
		 
		//Creating the stop button 
		Button stopButton = new Button("stop"); 
		  
		Button b3 = new Button(">>"); 
		Button b4 = new Button(">>>"); 
		
		Label label = new Label("Play speed");
		
		TextField speed = new TextField("1.5");
		speed.setMaxWidth(40);
	  
		Label time = new Label("00:00:00");
		
		setMargin(openButton, new Insets(20, 20, 20, 20));
		setMargin(b1, new Insets(20, 5, 20, 20)); 
		setMargin(b2, new Insets(20, 5, 20, 5));
		setMargin(playButton, new Insets(20, 5, 20, 5)); 
		setMargin(pauseButton, new Insets(20, 5, 20, 5));
		setMargin(stopButton, new Insets(20, 5, 20, 5));
		setMargin(b3, new Insets(20, 5, 20, 5)); 
		setMargin(b4, new Insets(20, 20, 20, 5));
		setMargin(label, new Insets(20, 5, 20, 20));
		setMargin(speed, new Insets(20, 20, 20, 5));
		setMargin(time, new Insets(20, 20, 20, 20));
		 
		//retrieving the observable list of the HBox 
		ObservableList<Node> list = getChildren();  
		  
		//Adding all the nodes to the observable list (HBox) 
		list.addAll(openButton,b1,b2,playButton,pauseButton, stopButton,b3,b4,label,speed,time);  
		
	}
}
