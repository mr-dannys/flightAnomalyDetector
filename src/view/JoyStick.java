package view;

import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class JoyStick extends AnchorPane{

	Slider throttle;
	Slider rudder;
	Circle joyStick;
	Circle backStick;
	
	public JoyStick() {
		throttle = new Slider();
		throttle.setOrientation(Orientation.VERTICAL);
		rudder = new Slider();
		getChildren().add(throttle);
		getChildren().add(rudder);
		throttle.setLayoutY(54);
		throttle.setLayoutX(69);
		throttle.setMin(-1);
		throttle.setMax(1);
		rudder.setLayoutY(204);
		rudder.setLayoutX(110);
		rudder.setMin(-1);
		rudder.setMax(1);
		throttle.setShowTickMarks(isCache());
		rudder.showTickMarksProperty();
		
        backStick = new Circle();
        backStick.setLayoutY(124);
        backStick.setLayoutX(189);
        backStick.setRadius(61);
        backStick.setFill(Color.web("#1f93ff00",1));
        backStick.setStroke(Color.BLACK);
        backStick.setStrokeType(StrokeType.INSIDE);
        
        joyStick = new Circle();
        joyStick.setLayoutY(124);
        joyStick.setLayoutX(189);
        joyStick.setRadius(28);
        joyStick.setStroke(Color.BLACK);
        joyStick.setStrokeType(StrokeType.INSIDE);
        
		getChildren().add(joyStick);
		getChildren().add(backStick);
	}
	
	
    public void setThrottle(Double double4) {
    	throttle.setDisable(false);
    	throttle.setValue(double4);
    	throttle.setDisable(true);
    }
    
    public void setRudder(Double double3) {
    	rudder.setDisable(false);
    	rudder.setValue(double3);
    	rudder.setDisable(true);
    }
    
    public void move(Double double1, Double double2, Double double3, Double double4) {
    	setThrottle(double4);
    	setRudder(double3);
    	joyStick.setLayoutX(backStick.getLayoutX()+(60*double1));
    	joyStick.setLayoutY(backStick.getLayoutY()+(60*double2));
    }
    
}
