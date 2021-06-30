package view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class DataList extends Pane {
	
	ListView<String> list;
	ObservableList<String> items;
	
	public DataList() {
		list = new ListView<String>();
		items = FXCollections.observableArrayList();
		setPrefHeight(620);
		setPrefWidth(360);
		
		this.getChildren().add(list);
		list.setLayoutX(30);
		list.setLayoutY(25);
		list.setPrefHeight(550);
		list.setPrefWidth(300);
		list.setItems(items);

	}
	
	public void addVals(List<String> vals) {
        items.setAll(vals);
	}
	
    public void add(String s) {
    	items.add(s);
    }
    
    public void init() {
    	items = FXCollections.observableArrayList();
    	list.setItems(items);
    }

}
