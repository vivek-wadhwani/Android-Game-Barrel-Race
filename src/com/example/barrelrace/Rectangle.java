package com.example.barrelrace;
import android.graphics.Point;
import android.util.Log;

public class Rectangle {
	public static int width;
	public static int height;
	public  static Point starting;
	
	public Rectangle(int w , int h , Point p){
		width = w;
		height = h;
		starting = p;
	} 
	public boolean inRectangle(Point p){
		boolean inRectangle = false;
		
		if(p.x >= starting.x && p.x <= starting.x+width && p.y >= starting.y && p.y <= starting.y+height){
			inRectangle = true;
		}
			
		return inRectangle;
	}
	public boolean checkBorderTouched(Point p){
		boolean touched = false;
		Log.v("in rect " + (starting.y+height),"in rect " + starting.x);
		if(p.x == starting.x ||p.x == starting.x+4  && p.y >= starting.y && p.y <= starting.y+ height){
			touched = true;
		}
		if(p.x == starting.x+ width|| p.x == starting.x+ width-4 && p.y >= starting.y && p.y <= starting.y+ height){
			touched = true;
		}
		if(p.y == starting.y ||p.y == starting.y+4 && p.x >= starting.x && p.x <= starting.x+ width){
			touched = true;
		}
		if(p.y == starting.y + height|| p.y <= starting.y+ height-4 && p.x >= starting.x && p.x <= starting.x+ width){
			touched = true;
		}
		
		return touched;
	}


}
