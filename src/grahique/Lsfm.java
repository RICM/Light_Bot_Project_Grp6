package grahique;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;


public class Lsfm {

	public static RenderWindow app = new RenderWindow(new VideoMode(800, 600),"Lightbot");
	public static Clock c = new Clock();

	public static void tick(Time t){}

	public static void processEvent()
	{
		for(Event e : app.pollEvents()){
			if(e.type == Type.CLOSED)
				app.close();
		}
	}

	public static void draw(){
		app.clear(Color.BLACK);
		app.display();
	}

	public static void main(String[] args) {

		while(app.isOpen()){
			draw();
			processEvent();
			tick(c.restart());
		}

	}

}
