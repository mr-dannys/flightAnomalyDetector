package model;

import java.util.List;

import javafx.scene.chart.XYChart;

public interface IAlgorithem {
	
	public XYChart create(List<String> features, List<List<Double>> train);

	public XYChart calc(List<Double> data, String name);
	
	public void add(Double data);
}