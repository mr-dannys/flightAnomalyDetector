package viewModel;

import java.util.List;

public class Statistics {

	
	public Double pearson(List<Double> x, List<Double> y, double per) {
		Double mx = median(x);
		Double my = median(y);
		Double sum = 0.0;
		Double devx = 0.0;
		Double devy = 0.0;
		for(int i=0; i< x.size() && i< y.size(); i++) {
			
			sum += (x.get(i) - mx) * (y.get(i) - my);
			devx += Math.pow((x.get(i) - mx), 2);
			devy += Math.pow((y.get(i) - my), 2);
		}
		Double dev = Math.sqrt(devx*devy);
		if(dev != 0 && sum/dev > per)
			return sum/dev;
		else
			return -1.0;
	}
	
	private Double median(List<Double> data) {
		Double count =0.0;
		for (Double d : data) {
			count += d;
		}
		if(data.size() > 0)
		count=count/data.size();
		return count;
	}
}
