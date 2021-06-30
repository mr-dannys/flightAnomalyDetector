package view;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import viewModel.Controller;

public class MainWindowController implements Initializable, Observer{
	
	
	Controller controller;
	
	@FXML
	DataList dataList;
	@FXML
	JoyStick joyStick;
	@FXML
	DataGraphs dataGraphs;
	
	@FXML
	Button open;
	@FXML
	Button fullBack;
	@FXML
	Button back;
	@FXML
	Button play; 
	@FXML
	Button pause;
	@FXML
	Button stop;
	@FXML
	Button forward;
	@FXML
	Button fullForward;
	@FXML
	TextField speed;
	@FXML
	Label timer;
	
	List<String> list;
	
	String selected;
	
	int aileron = 0, elevators = 0, rudder = 0, throttle = 0;
	
	public void addAlg(XYChart<Double, Double> al) {
		dataGraphs.addAlg(al);
	}
	
	public void replaceAlg(XYChart<Double, Double> al) {
		dataGraphs.replaceAlg(al);
	}
	
	public void addProperties(List<String> l){
		list.addAll(l);
		dataList.addVals(l);
	}
	
	public void addProperty(String s){
		list.add(s);
		dataList.add(s);
	}

	public MainWindowController() {
		controller = new Controller();
		list = new ArrayList<String>();
		selected = "";
	}
	
	
    public void setThrottle(Double val) {
    	joyStick.setThrottle(val);
    }
    
    public void setRudder(Double val) {
    	joyStick.setRudder(val);
    }
    
    public void move(Double double1, Double double2, Double double3, Double double4) {
    	joyStick.move(double1, double2, double3, double4);
    }
    
	public void updateValues(List<Double> list2) {
		dataGraphs.updateValues(list2);
	}
	
	public void addValue(Double val) {
		dataGraphs.addValue(val);
		
	}
	
	public void updateCorelative(List<Double> list2) {
		dataGraphs.updateCorelative(list2);
	}
	
	public void updateAlgorithem(List<Integer> data) {
		dataGraphs.updateAlgorithem(data);
	}
	
	public void timeUpdate(int time) {
		int miliseconds = time%100;
		int seconds = (time/100)%60;
		int minuts = (time/100)/60;
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	timer.setText(minuts + ":" + seconds + ":" + miliseconds);
		    }
		});
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		controller.observe(this);
		dataList.list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        // Your action here
		    	if(selected != newValue) {
			    	selected = newValue;
			    	controller.choose(selected);
		    	}
		    }
		});
		
		////////////////////////////////////////
		
		 // action event
		open.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
            	FileChooser fileChooser = new FileChooser();
            	fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV files (*.csv)", "*.csv"));
            	String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
            	fileChooser.setInitialDirectory(new File(currentPath));
            	File selectedFile = fileChooser.showOpenDialog(new Stage());
            	if(selectedFile != null) {
            		controller.open(selectedFile);
            		dataList.list.getSelectionModel().select(0);
            	}
            }
        });
		
		fullBack.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
               controller.fullBack();
            }
        });
		back.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
               controller.back();
            }
        });
		play.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
               controller.play();
            }
        });
		pause.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
               controller.pause();
            }
        });
		stop.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
               controller.stop();
            }
        });
		forward.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
               controller.forward();
            }
        });
		fullForward.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
               controller.fullForward();
            }
        });
		
		speed.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
               controller.speed(Double.parseDouble(speed.getText()));
            }
        });
        
		
	}


	
	
	
	/////////////////////////
	


}
