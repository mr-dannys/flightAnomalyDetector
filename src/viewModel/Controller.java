package viewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.scene.chart.XYChart;
import model.FlightManager;
import model.IAlgorithem;
import view.MainWindowController;
import view.Observer;

public class Controller {
	
	FlightManager flightManager;
	
	List<String> list;
	int joyStickX;
	int joyStickY;
	Double speed = 1.5;
	List<String> vals;
	List<Integer> corelative;
	List<Integer> algorithem;
	List<List<Double>> data;
	List<List<Double>> pearCheck = new ArrayList<>();
	String choosed;
	Integer time = 0;
	String fly[];
	int index = 0;
	Observer observer;
	Thread getData;
	Thread show;
	int aileron = 0, elevators = 0, rudder = 0, throttle = 0;
	String normal = "";
	private boolean end;
    private boolean pouse = false;
    private Object lock;
	private boolean started;
	Settings s;

	private ConcurrentLinkedQueue<String> lines;
	
	IAlgorithem alg;

	private XYChart algChart;
	
	public Controller() {
		s = new Settings();
		choosed = "";
		flightManager = new FlightManager(this, s.send, s.recive);
		normal = s.normal;
		data = new ArrayList<>();
		list = new ArrayList<>();
		corelative = new ArrayList<>();
		algorithem = new ArrayList<>();
		lines = new ConcurrentLinkedQueue<String>();
		vals = new ArrayList<String>();
		end = false;
		started = false;
		lock = new Object();
		getData = new Thread(new Runnable() {
			
			@Override
			public void run() {
				String ans = null;
				while(!end) {
					if(!pouse) {
						String data = lines.poll();
						if(data != null) {
							ans = flightManager.simulate(data);
							if(ans != null) {
								synchronized (fly) {
									fly[index] = ans;
									index++;
								}
							}
						}
					}
					try {
						Thread.sleep((long) (100));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		show = new Thread(new Runnable() {
			
			@Override
			public void run() {
				String ans = null;
				while(!end) {
					ans = null;
					if(!pouse) {
						synchronized (fly) {
							synchronized (time) {
								if(time < index) {
									ans = fly[time];
									time++;
									observer.timeUpdate(time);
								}
							}
						}
					}
					if(ans != null) {
						vals.add(ans);
						String[] parts = ans.split(",");
						for(int i=0; i< list.size(); i++) {
							data.get(i).add(Double.parseDouble(parts[i]));
						}
						int i = list.indexOf(choosed);
						int temp;
						synchronized (time) {
							temp = time -1;
						}
							if(i >= 0 && temp < data.get(i).size()) {
								notifyUpdateValues(i);
								observer.move(data.get(aileron).get(temp), data.get(elevators).get(temp), data.get(rudder).get(temp), data.get(throttle).get(temp));
							}
						
					}
					try {
						synchronized (speed) {
							Thread.sleep((long) (100/speed));
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		load();
		read();
	}


	@SuppressWarnings({ "unchecked", "deprecation" })
	private void load() {
		String input,className;
		
		input=s.algLoc; // get user input
		className=s.algType;
		URLClassLoader urlClassLoader = null;
		try {
			urlClassLoader = URLClassLoader.newInstance(new URL[] {
			new URL("file://"+input)
			});
			Class<IAlgorithem> c=(Class<IAlgorithem>) urlClassLoader.loadClass(className);
			alg = c.newInstance();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	
	public void observe(MainWindowController observer) {
		this.observer = observer;
		observer.addAlg(algChart);
	}
	
	public void read() {
		
		File selectedFile = new File(normal);
        Scanner reader;
		try {
			reader = new Scanner(selectedFile);
			String line = reader.nextLine();
			String[] vals = line.split(",");
			List<List<Double>> haha = new ArrayList<List<Double>>();
			List<String> list1 = new ArrayList<>();
			for (String v : vals) {
				list1.add(v);
				haha.add(new ArrayList<>());
			}
			List<String> lines1 = new ArrayList<String>();
	        while (reader.hasNextLine()) {
		          line = reader.nextLine();
		          lines1.add(line);
		        }
	        reader.close();
	        for (String line1 : lines1) {
				String[] s = line1.split(",");
				for(int i1=0; i1< s.length; i1++) {
					haha.get(i1).add(Double.parseDouble(s[i1]));
				}
			}
	        algChart = alg.create(list1, haha);
	        checkPirson(haha, 0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void open(File selectedFile) {
		
        Scanner reader;
		try {
			reader = new Scanner(selectedFile);
			String line = reader.nextLine();
			String[] vals = line.split(",");
			int i=0;
			for (String v : vals) {
				if(v.equals("aileron")) {
					aileron = i;
				}
				if(v.equals("elevator")) {
					elevators = i;
				}
				if(v.equals("rudder")) {
					rudder = i;
				}
				if(v.equals("throttle")) {
					throttle = i;
				}
				list.add(v);
				observer.addProperty(v);
				data.add(new ArrayList<>());
				pearCheck.add(new ArrayList<>());
				i++;
			}
	        while (reader.hasNextLine()) {
		          line = reader.nextLine();
		          lines.add(line);

					String[] parts = line.split(",");
					for(int i1=0; i1< list.size(); i1++) {
						pearCheck.get(i1).add(Double.parseDouble(parts[i1]));
					}

		        }
	        reader.close();
	        fly = new String[lines.size()];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void checkPirson(List<List<Double>> nums, double p) {
		Statistics s = new Statistics();
		Double stat = 0.0;
		Double temp = 0.0;
		int ans= -1;
		for (int i=0; i< nums.size(); i++) {
			for(int j=0; j< nums.size(); j++) {
				if(i != j) {
					temp = s.pearson(nums.get(i), nums.get(j), p);
					if(stat < temp)
					{
						stat = temp;
						ans = j;
					}
				}
			}
			corelative.add(ans);
			stat = 0.0;
			temp = 0.0;
			ans= -1;
		}
	}


	public void fullBack() {
		synchronized (time) {
			time = Math.max(time - 100, 0);
			int i = list.indexOf(choosed);
			if(i >= 0) {
				notifyUpdateValues(i);
				observer.move(data.get(i).get(aileron), data.get(i).get(elevators), data.get(i).get(rudder), data.get(i).get(throttle));
			}
		}
	}
	
	public void back() {
		synchronized (time) {
			time = Math.max(time - 10, 0);
			int i = list.indexOf(choosed);
			if(i >= 0) {
				notifyUpdateValues(i);
				observer.move(data.get(i).get(aileron), data.get(i).get(elevators), data.get(i).get(rudder), data.get(i).get(throttle));
			}
		}
	}

	public void play() {
		end = false;
		if(started) {
			synchronized (lock){
				pouse = false;
			}
		}
		if(!started) {
		flightManager.init();
			started = true;
			getData.start();
			show.start();
		}
		flightManager.start();
	}
	
	public void pause() {
		synchronized (lock){
			pouse = true;
		}
	}
	
	public void stop() {
		end = true;
		flightManager.stop();
	}
	
	public void forward() {
		synchronized (time) {
			time = Math.min(time + 10, data.get(0).size());
			int i = list.indexOf(choosed);
			if(i >= 0) {
				notifyUpdateValues(i);
				observer.move(data.get(i).get(aileron), data.get(i).get(elevators), data.get(i).get(rudder), data.get(i).get(throttle));
			}
		}
	}
	
	public void fullForward() {
		synchronized (time) {
			time = Math.min(time + 100, data.get(0).size());
			int i = list.indexOf(choosed);
			if(i >= 0) {
				notifyUpdateValues(i);
				observer.move(data.get(i).get(aileron), data.get(i).get(elevators), data.get(i).get(rudder), data.get(i).get(throttle));
			}
		}
	}
	
	public void speed(double speed) {
		synchronized (this.speed) {
			this.speed = speed;
		}
		
	}
	
	public void choose(String choice) {
		choosed = choice;
		int i = list.indexOf(choosed);
		if(i >= 0) {
			notifyUpdateValues(i);
		}
	}
	
	private void notifyUpdateValues(int i){
		int temp;
		synchronized (time) {
			temp = time;
		}
		
		if(data.get(i).size() > 0) {
		observer.updateValues(data.get(i).subList(0, Math.min(temp, data.get(i).size() - 1)));
		if(corelative.get(i) >= 0)
			observer.updateCorelative(data.get(corelative.get(i)).subList(0, Math.min(temp, data.get(i).size() - 1)));
		else
			observer.updateCorelative(data.get(i).subList(0, Math.min(temp, data.get(i).size() - 1)));
		observer.replaceAlg(alg.calc(data.get(i).subList(0, Math.min(temp, data.get(i).size() - 1)), choosed));
		}
		else {
			observer.updateValues(data.get(i));
			
			if(corelative.get(i) >= 0)
				observer.updateCorelative(data.get(corelative.get(i)));
			else
				observer.updateCorelative(data.get(i));
			observer.replaceAlg(alg.calc(data.get(i), choosed));
		}
	}
	
	


}
