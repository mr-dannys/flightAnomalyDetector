package view;

import java.util.List;

import javafx.scene.chart.XYChart;

public interface Observer {

	public void updateValues(List<Double> list2);
	
	public void updateCorelative(List<Double> list2);
	
	public void updateAlgorithem(List<Integer> data);
	
	public void timeUpdate(int time);

	public void move(Double double1, Double double2, Double double3, Double double4);

	public void addProperty(String v);

	public void replaceAlg(XYChart<Double, Double> calc);
}
