package view;

import java.util.List;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DataGraphs extends VBox {
	
	LineChart<Number, Number> values;
	LineChart<Number, Number> corelative;
	XYChart algorithem;
	ObservableList<Series<Number, Number>>  vals;
	private AnchorPane bottom;
	
	public DataGraphs() {
		setPrefHeight(620);
		setPrefWidth(360);
		setAlignment(Pos.CENTER);
		HBox box = new HBox();
		box.setPrefHeight(310);
		box.setPrefWidth(360);
		box.setSpacing(20);
		values = new LineChart<Number, Number>(new NumberAxis(), new NumberAxis());
		values.setPrefWidth(150);
		values.setAnimated(false);
		box.getChildren().add(values);
		corelative = new LineChart<Number, Number>(new NumberAxis(), new NumberAxis());
		corelative.setPrefWidth(150);
		corelative.setAnimated(false);
		box.getChildren().add(corelative);
		bottom = new AnchorPane();
		bottom.setPrefWidth(360);
		bottom.setPrefHeight(310);
		

		getChildren().add(box);
		getChildren().add(bottom);
		vals = values.getData();
		
	}
	
	public void addAlg(XYChart<Double, Double> al) {
		
		algorithem = al;

		algorithem.setPrefWidth(340);
		algorithem.setPrefHeight(230);
		algorithem.setAnimated(false);
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		bottom.getChildren().add(algorithem);
    }
});
		
	}
	
	public void replaceAlg(XYChart<Double, Double> al) {
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		bottom.getChildren().remove(algorithem);
		    }
		});
		addAlg(al);
		
	}
	
	public void updateValues(List<Double> list2) {
		XYChart.Series<Number, Number> dataSeries1 = new XYChart.Series<Number, Number>();
		dataSeries1.setName("Values");
        int time = 0;
		for (Double dot : list2) {
			dataSeries1.getData().add(new XYChart.Data<Number, Number>(time, dot));
			time++;
		}
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	vals.clear();
		    	vals.add(dataSeries1);
		    }
		});
	}
	
	public void addValue(Double val) {
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	vals.get(0).getData().add(new XYChart.Data<Number, Number>(vals.get(0).getData().size(), val));
		    }
		});
		
	}
	
	public void updateCorelative(List<Double> list2) {
		XYChart.Series<Number, Number> dataSeries1 = new XYChart.Series<Number, Number>();
        dataSeries1.setName("Corelative");
        int time = 0;
		for (Double dot : list2) {
			dataSeries1.getData().add(new XYChart.Data<Number, Number>(time, dot));
			time++;
		}
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	corelative.getData().clear();
		    	corelative.getData().add(dataSeries1);
		    }
		});
	}
	
	public void updateAlgorithem(List<Integer> data) {
		XYChart.Series<Number, Number> dataSeries1 = new XYChart.Series<Number, Number>();
        int time = 0;
		for (int dot : data) {
			dataSeries1.getData().add(new XYChart.Data<Number, Number>(time, dot));
		}
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	algorithem.getData().add(dataSeries1);
		    }
		});
	}

}
