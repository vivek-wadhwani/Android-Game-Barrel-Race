/*
 * This View deals with GUI and interection with the user on canvas and its methods... 
 * 
 * 
 * 
 * */
package com.example.barrelrace;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView.HitTestResult;
import android.widget.Button;
import android.widget.TextView;


public class BarrelRaceView extends SurfaceView implements SurfaceHolder.Callback
{
	//Barrels co-ordinates and images
	private static Bitmap barrel_Bitmap;
	private static Bitmap barrel_1Bitmap;
	private static Bitmap barrel_2Bitmap;
	private static Bitmap barrel_3Bitmap;
	private static int barrell_1x;
	private static int barrell_1y;
	private static int barrell_2x;
	private static int barrell_2y;
	private static int barrell_3x;
	private static int barrell_3y;

	// Fence Images and coordinates
	private static int fence1_x;
	private static int fence1_y;
	private static int fence2_x;
	private static int fence2_y;
	private static int fence3_x;
	private static int fence3_y;
	private static int fence4_x;
	private static int fence4_y;
	private static int fence5_x;
	private static int fence5_y;
	private static Bitmap fence1;
	private static Bitmap fence2;
	private static Bitmap fence3;
	private static Bitmap fence4;
	private static Bitmap fence5;
	
	//Bitmap to show the positions..................
	private static Bitmap greenDotBitmap;
	private static Bitmap redDotBitmap;


	//checkpoints................................
	private static Bitmap check_1; 
	private static Bitmap check_2;
	private static Bitmap check_3;
	private static Bitmap check_4;
	private static Bitmap check_5;
	private static Bitmap check_6;
	private static Bitmap check_7;
	private static Bitmap check_8;
	private static Bitmap check_9;
	private static Bitmap check_10;
	private static Bitmap check_11;
	private static Bitmap check_12;



	//check point coordinates.....................
	private static int g1_x;
	private static int g1_y;
	private static int g2_x;
	private static int g2_y;
	private static int g3_x;
	private static int g3_y;
	private static int g4_x;
	private static int g4_y;
	private static int g5_x;
	private static int g5_y;
	private static int g6_x;
	private static int g6_y;
	private static int g7_x;
	private static int g7_y;
	private static int g8_x;
	private static int g8_y;
	private static int g9_x;
	private static int g9_y;
	private static int g10_x;
	private static int g10_y;
	private static int g11_x;
	private static int g11_y;
	private static int g12_x;
	private static int g12_y;

	private static int barrelDiameter;
	private static int barrelRadius;

	private static int dotDiameter;
	private static int dotRadius;
	
	private static int fence1_Height;
	private static int fence1_Width;
	private static int fence2_Height;
	private static int fence2_Width;
	private static int fence3_Height;
	private static int fence3_Width;
	private static int fence4_Height;
	private static int fence4_Width;
	private static int fence5_Height;
	private static int fence5_Width;
	
	//private AvoiderThread avoiderThread;
	private static Activity activity;
	private static Context cont;
	private static Resources resources;
	
	// Motion parameters
	public static double totalElapsedTime;
	public static boolean gameOver = true;
	
	
	// Sound variables and constants
	private static final int TARGET_SOUND_ID = 0;
	private static final int BLOCKER_SOUND_ID = 1;
	private static SoundPool soundPool;
	//map(which sound it should be, resource id).......
	private static Map<Integer,Integer> soundMap;
	
	// Game variables
	public static android.graphics.PointF horse = new android.graphics.PointF();
	
	private static Bitmap rodeoBitmap;
	private static Bitmap backgroundBitmap;

	private static int horseInitialX;
	private static int horseInitialY;
	private static int screenWidth;
	private static int screenHeight;
	
	public static int horseDiameter;
	public static int horseRadius;

	private static boolean isCollision =false;
	private boolean isNewGame= false;

	private static Rectangle rectBarrel_1;
	private static Rectangle rectBarrel_2;
	private static Rectangle rectBarrel_3;
	private static float l1x = 0 ,l1y = 0,l2x= 0,l2y= 0, p1x= 0,p1y= 0 ,p2x= 0,p2y= 0;
	private static float m1x = 0 ,m1y = 0,m2x= 0,m2y= 0, q1x= 0,q1y= 0 ,q2x= 0,q2y= 0;
	private static float n1x = 0 ,n1y = 0,n2x= 0,n2y= 0, r1x= 0,r1y= 0 ,r2x= 0,r2y= 0;
	private static boolean barrel_1_Collision = false;
	private static boolean barrel_2_Collision = false;
	private static boolean barrel_3_Collision = false;
	private static Line l,m,n;
	private static Line p,q,r; 
	private static Paint p34 , p35 , p36;
	private static boolean touchFence = false;
	private static boolean gameWin = false; 
	private static boolean touchFence1 = false;
	private static boolean touchFence2 = false;
	private static boolean touchFence3 = false;
	private static boolean touchFence4 = false;
	private static boolean touchFence5 = false;
	private static int horsetoBarrel1 = 0;
	private static int horsetoBarrel2 = 0;
	private static int horsetoBarrel3 = 0;
	private static int nearest;

	public BarrelRaceView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		activity = (Activity) context;
		cont = context;
		resources = getResources();
		
		getHolder().addCallback(this);
								
		// Initialize sounds
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		soundMap = new HashMap<Integer, Integer>();
		//soundMap.put(TARGET_SOUND_ID, soundPool.load(context, R.raw.target_hit, 1));
		//soundMap.put(BLOCKER_SOUND_ID, soundPool.load(context, R.raw.blocker_hit, 1));

	}
	//Done by Apurva......................................................................
		// Called when view is first added - set original positions
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) 
	{
		
		super.onSizeChanged(w, h, oldw, oldh);
		
		screenWidth = w;
		screenHeight = h;
		
		horseDiameter = w / 25; 
		horseRadius = horseDiameter / 2;	
		
		horseInitialX = w / 2;
		horseInitialY = h-(h/12);
		
		dotDiameter= w/30;
		dotRadius= dotDiameter/2;
		
		fence1_Height = w/60;
		fence1_Width = w/2 - w/15;
		fence2_Height = w/60;
		fence2_Width = w/2-w/15;
		fence3_Width = w/60;
		fence3_Height = h- h/8;
		fence4_Height = h- h/8;
		fence4_Width = w/60;
		fence5_Height = w/60;
		fence5_Width = w;
		
		rodeoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_rodeo);
		rodeoBitmap = Bitmap.createScaledBitmap(rodeoBitmap, horseDiameter, horseDiameter, true);
				
		backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap, screenWidth, screenHeight, true);
		
		fence1 = BitmapFactory.decodeResource(getResources(), R.drawable.fence);
		fence1 = Bitmap.createScaledBitmap(fence1, fence1_Width, fence1_Height, true);
		
		fence2 = BitmapFactory.decodeResource(getResources(), R.drawable.fence);
		fence2 = Bitmap.createScaledBitmap(fence2, fence2_Width, fence2_Height, true);
		
		fence3 = BitmapFactory.decodeResource(getResources(), R.drawable.fence);
		fence3 = Bitmap.createScaledBitmap(fence3, fence3_Width, fence3_Height, true);
		
		fence4 = BitmapFactory.decodeResource(getResources(), R.drawable.fence);
		fence4 = Bitmap.createScaledBitmap(fence4, fence4_Width, fence4_Height, true);
		
		fence5 = BitmapFactory.decodeResource(getResources(), R.drawable.fence);
		fence5 = Bitmap.createScaledBitmap(fence5, fence5_Width, fence5_Height, true);
		
		// Changed
		barrelDiameter = w/15;
		barrelRadius  = barrelDiameter/2;
	
		barrell_1x = w/4;
		barrell_1y = 2*(h/4);
		barrell_2x = w/2;
		barrell_2y = h/4;
		barrell_3x = 3*(w/4);
		barrell_3y = 2*(h/4);
	
		barrel_Bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.barrel);
		barrel_1Bitmap = barrel_Bitmap;
		barrel_1Bitmap = Bitmap.createScaledBitmap(barrel_1Bitmap, barrelDiameter, barrelDiameter, true);
	
		barrel_2Bitmap = barrel_1Bitmap;
		barrel_3Bitmap = barrel_1Bitmap;
	
		g1_x = barrell_1x - barrelDiameter;
		g1_y = barrell_1y + barrelRadius - dotRadius ;
		g2_x = barrell_1x + barrelRadius - dotRadius ;
		g2_y = barrell_1y -barrelDiameter ;
		g3_x = barrell_1x + barrelDiameter +barrelRadius ;
		g3_y = barrell_1y + barrelRadius - dotRadius;
		g4_x = barrell_1x + barrelRadius - dotRadius;
		g4_y = barrell_1y + barrelDiameter +barrelRadius;
	
	
		g5_x = barrell_2x - barrelDiameter;
		g5_y = barrell_2y + barrelRadius - dotRadius ;
		g6_x = barrell_2x + barrelRadius - dotRadius ;
		g6_y = barrell_2y -barrelDiameter ;
		g7_x = barrell_2x + barrelDiameter +barrelRadius ;
		g7_y = barrell_2y + barrelRadius - dotRadius;
		g8_x = barrell_2x + barrelRadius - dotRadius;
		g8_y = barrell_2y + barrelDiameter +barrelRadius;
	
		g9_x = barrell_3x - barrelDiameter;
		g9_y = barrell_3y + barrelRadius - dotRadius ;
		g10_x = barrell_3x + barrelRadius - dotRadius ;
		g10_y = barrell_3y -barrelDiameter ;
		g11_x = barrell_3x + barrelDiameter +barrelRadius ;
		g11_y = barrell_3y + barrelRadius - dotRadius;
		g12_x = barrell_3x + barrelRadius - dotRadius;
		g12_y = barrell_3y + barrelDiameter +barrelRadius;
	
		fence1_x = 0;
		fence1_y = h - h/6;
		fence2_x = w/2 + w/15 ;
		fence2_y = h - h/6;
		fence3_x = 0;
		fence3_y = 0;
		fence4_x = w- w/60;
		fence4_y = 0;
		
		redDotBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red_dot);
		redDotBitmap = Bitmap.createScaledBitmap(redDotBitmap, dotDiameter, dotDiameter, true);
		greenDotBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.green_dot);
		greenDotBitmap = Bitmap.createScaledBitmap(greenDotBitmap, dotDiameter, dotDiameter, true);
	
		//rectBarrel_1 = new Rectangle((w/2) - (w/15), h-(h/6), new Point(0, 0));
				//rectBarrel_2 = new Rectangle((w/3)-2, h-(h/6), new Point(w/3, 0));
				//rectBarrel_3 = new Rectangle((w/2), h-(h/6), new Point((w)/2, 0));
				rectBarrel_1 = new Rectangle(w, h-(h/6), new Point(0, 0));
				rectBarrel_2 = new Rectangle(w, h-(h/6), new Point(0, 0));
				rectBarrel_3 = new Rectangle(w, h-(h/6), new Point(0, 0));
		
				p34 = new Paint();
				p34.setColor(Color.MAGENTA);
				
				p35 = new Paint();
				p35.setColor(Color.MAGENTA);
				
				p36 = new Paint();
				p36.setColor(Color.MAGENTA);
	
		newGame();
	}
	//Done by Vidhi....................................
		//Called when the f=game is stopped....................
	public static void stopGame() {
		
		BarrelRace.handler.removeCallbacks(BarrelRace.runnable);
		BarrelRace.horseSpeed.x = 0;
		BarrelRace.horseSpeed.y = 0;
		BarrelRace.stopTimer();
	}

	//Done by Vidhi....................................
	//Releases the resources when the game is paused.......................
	public void releaseResources() {
		soundPool.release();
		soundPool = null;
	}
	//Done By Vivek..................................
		// Called when the new game is launched...............................   
	public static void newGame()
	{
		
		horse.set(screenWidth/2, screenHeight);
		
	
		barrel_1Bitmap = barrel_Bitmap;
		barrel_1Bitmap = Bitmap.createScaledBitmap(barrel_1Bitmap, barrelDiameter, barrelDiameter, true);

		barrel_2Bitmap = barrel_1Bitmap;
		barrel_3Bitmap = barrel_1Bitmap; 

		check_1 = check_2 = check_3 = check_4 = check_5 = check_6 = check_7 = check_8 = check_9 = check_10 = check_11 = check_12 = redDotBitmap;
		l1x = l1y =l2x=l2y=p1x=p1y=p2x=p2y= 0;
		m1x = m1y =m2x=m2y=q1x=q1y=q2x=q2y= 0;
		n1x=n1y =n2x=n2y=r1x=r1y= r2x=r2y= 0;
		p34.setColor(Color.MAGENTA);
		p35.setColor(Color.MAGENTA);
		p36.setColor(Color.MAGENTA);
		isCollision = false;
		BarrelRace.horsePosition.x = screenWidth/2;
		BarrelRace.horsePosition.y = screenHeight;
		BarrelRace.horseSpeed.x=0;
		BarrelRace.horseSpeed.y=0;
		
		
		
		if (gameOver)
		{
			
			gameOver = false;
		}
		
		BarrelRace.handler.postDelayed(BarrelRace.runnable, 10);
		long initialTime = System.currentTimeMillis();
		//TimeCounter=0;
		
	}
	
	
	
	
	//----------- To do while timer is running ---------------//
	public static void updatePositions()
	{
		// Don't try to update positions if we haven't started the game yet
		if (gameOver == true) {
			return;
		}
		
		checkForCollisions();
		
	}
	
	//Done by Apurva , Vidhi and Vivek...................
		//Checking for each and every event that happens in game...................................
	private static void checkForCollisions() {
		
		// Get center point of horse
		int horseX = (int) horse.x + horseRadius;
		int horseY = (int) horse.y + horseRadius;
		//Added
		if(isCollision==false){
			
			horsetoBarrel1 = (int) Math.sqrt(Math.pow(horse.x - barrell_1x+barrelRadius,2) + Math.pow(horse.y - barrell_1y+barrelRadius,2));
			horsetoBarrel2 = (int) Math.sqrt(Math.pow(horse.x - barrell_2x+barrelRadius,2) + Math.pow(horse.y - barrell_2y+barrelRadius,2));
			horsetoBarrel3 = (int) Math.sqrt(Math.pow(horse.x - barrell_3x+barrelRadius,2) + Math.pow(horse.y - barrell_3y+barrelRadius,2));
			nearest = (int) minDistance(horsetoBarrel1, horsetoBarrel2, horsetoBarrel3);
			
			
			
			double hitDistance1 = Math.sqrt(Math.pow(horse.x - barrell_1x,2) + Math.pow(horse.y - barrell_1y,2));
			if(hitDistance1<= horseRadius + barrelRadius ){
			//Log.v("target 1 Hit", "Target1");
			barrel_1Bitmap = getRoundedCornerBitmap(barrel_1Bitmap, Color.RED, 2, 2, BarrelRaceView.cont);
//				soundPool.play(soundMap.get(TARGET_SOUND_ID), 1, 1, 1, 0, 1f);
			//soundPool.play(soundMap.get(BLOCKER_SOUND_ID), 1, 1, 1, 0, 1f);


			isCollision= true;
			//GAME OVER - You lose
			
			gameWin= false; 
			stopGame();
			showGameOverDialog(R.string.game_over);
			
			}
			
			double hitDistance2 = Math.sqrt(Math.pow(horse.x - barrell_2x,2) + Math.pow(horse.y - barrell_2y,2));
			if(hitDistance2<= horseRadius + barrelRadius ){
			//Log.v("target 2 Hit", "Target2");
			barrel_2Bitmap = getRoundedCornerBitmap(barrel_2Bitmap, Color.RED, 2, 2, BarrelRaceView.cont);
			//soundPool.play(soundMap.get(TARGET_SOUND_ID), 1, 1, 1, 0, 1f);
			//soundPool.play(soundMap.get(BLOCKER_SOUND_ID), 1, 1, 1, 0, 1f);
			
			//GAME OVER - You lose
			isCollision= true;
			gameWin= false;
			stopGame();
			showGameOverDialog(R.string.game_over);
			
			}

			double hitDistance3 = Math.sqrt(Math.pow(horse.x - barrell_3x,2) + Math.pow(horse.y - barrell_3y,2));
			if(hitDistance3<= horseRadius + barrelRadius ){
			//Log.v("target 3 Hit", "Target3");
			barrel_3Bitmap = getRoundedCornerBitmap(barrel_3Bitmap, Color.RED, 2, 2, BarrelRaceView.cont);
//				soundPool.play(soundMap.get(TARGET_SOUND_ID), 1, 1, 1, 0, 1f);
			//soundPool.play(soundMap.get(BLOCKER_SOUND_ID), 1, 1, 1, 0, 1f);
			
			isCollision= true;
			gameWin= false;
			stopGame();
			
			showGameOverDialog(R.string.game_over);
			}
			
				}
		//Check if the horse hits any checking point......................................
				//--------------------------------------------------------------------------------------
		double pointDistance1 = Math.sqrt(Math.pow(horse.x - g1_x,2) + Math.pow(horse.y - g1_y,2));
		if(pointDistance1<= horseRadius + dotRadius){
		check_1 = greenDotBitmap;
		}
		double pointDistance2 = Math.sqrt(Math.pow(horse.x - g2_x,2) + Math.pow(horse.y - g2_y,2));
		if(pointDistance2<= horseRadius + dotRadius){
		check_2 = greenDotBitmap;
		}
		double pointDistance3 = Math.sqrt(Math.pow(horse.x - g3_x,2) + Math.pow(horse.y - g3_y,2));
		if(pointDistance3<= horseRadius + dotRadius){
		check_3 = greenDotBitmap;
		}
		double pointDistance4 = Math.sqrt(Math.pow(horse.x - g4_x,2) + Math.pow(horse.y - g4_y,2));
		if(pointDistance4<= horseRadius + dotRadius){
		check_4 = greenDotBitmap;
		}
		double pointDistance5 = Math.sqrt(Math.pow(horse.x - g5_x,2) + Math.pow(horse.y - g5_y,2));
		if(pointDistance5<= horseRadius + dotRadius){
		check_5 = greenDotBitmap;
		}
		double pointDistance6 = Math.sqrt(Math.pow(horse.x - g6_x,2) + Math.pow(horse.y - g6_y,2));
		if(pointDistance6<= horseRadius + dotRadius){
		check_6 = greenDotBitmap;
		}
		double pointDistance7 = Math.sqrt(Math.pow(horse.x - g7_x,2) + Math.pow(horse.y - g7_y,2));
		if(pointDistance7<= horseRadius + dotRadius){
		check_7 = greenDotBitmap;
		}
		double pointDistance8 = Math.sqrt(Math.pow(horse.x - g8_x,2) + Math.pow(horse.y - g8_y,2));
		if(pointDistance8<= horseRadius + dotRadius){
		check_8 = greenDotBitmap;
		}
		double pointDistance9 = Math.sqrt(Math.pow(horse.x - g9_x,2) + Math.pow(horse.y - g9_y,2));
		if(pointDistance9<= horseRadius + dotRadius){
		check_9 = greenDotBitmap;
		}
		double pointDistance10 = Math.sqrt(Math.pow(horse.x - g10_x,2) + Math.pow(horse.y - g10_y,2));
		if(pointDistance10<= horseRadius + dotRadius){
		check_10 = greenDotBitmap;
		}
		double pointDistance11 = Math.sqrt(Math.pow(horse.x - g11_x,2) + Math.pow(horse.y - g11_y,2));
		if(pointDistance11<= horseRadius + dotRadius){
		check_11 = greenDotBitmap;
		}
		double pointDistance12 = Math.sqrt(Math.pow(horse.x - g12_x,2) + Math.pow(horse.y - g12_y,2));
		if(pointDistance12<= horseRadius + dotRadius){
		check_12 = greenDotBitmap;
		}
		
		//fence detection.....................................................
		
				//Fence one stopping...............
				double fenceDistance1 = //Math.sqrt(Math.pow(horse.x - fence1_x,2) + Math.pow(horse.y - fence1_y,2));
										Math.abs((6*horse.y) - 5* screenHeight)/6;
				if(fenceDistance1<= horseRadius + dotRadius && horse.y<= fence1_y&& horse.x <=fence1_Width-horseRadius ){
					horse.y = fence1_y- fence1_Height- horseRadius;
					BarrelRace.horsePosition.y = fence1_y- fence1_Height- horseRadius;
					//touchFence=true;
					if(touchFence1==false)
					{
						BarrelRace.hitCount++;
						//touchFence1=false;
					}
					
				}
				if(fenceDistance1<= horseRadius + dotRadius && horse.y<= fence1_y&& horse.x <=fence1_Width-horseRadius )
				{
					touchFence1=true;
				}
				else{
					touchFence1=false;
				}
				if(fenceDistance1<= horseRadius + fence1_Height/2 && horse.y>= fence1_y&& horse.x <=fence1_Width-horseRadius){
					horse.y = fence1_y + fence1_Height;
					BarrelRace.horsePosition.y =fence1_y + fence1_Height;
				}
				
				//Fence two stopping...............
				double fenceDistance2 = //Math.sqrt(Math.pow(horse.x - fence1_x,2) + Math.pow(horse.y - fence1_y,2));
						Math.abs((6*horse.y) - 5* screenHeight)/6;
				if(fenceDistance2< horseRadius + dotRadius && horse.y< fence2_y&& horse.x >screenWidth - fence2_Width  ){
						horse.y = fence2_y- fence1_Height- horseRadius;
							BarrelRace.horsePosition.y = fence2_y- fence2_Height- horseRadius;
							if(touchFence2==false)
							{
								BarrelRace.hitCount++;
								//touchFence1=false;
							}
							
							
							
				}
				if(fenceDistance2< horseRadius + dotRadius && horse.y< fence2_y&& horse.x >screenWidth - fence2_Width  )
				{
					touchFence2=true;
				}
				else{
					touchFence2=false;
				}
				if(fenceDistance2< horseRadius + fence2_Height/2 && horse.y> fence2_y&& horse.x >screenWidth - fence2_Width){
						horse.y = fence2_y + fence2_Height;
						BarrelRace.horsePosition.y =fence2_y + fence2_Height;
				}
				
				if(horse.x<fence3_Width && horse.y < fence3_Height){
					
					BarrelRace.horsePosition.x = fence3_x+ fence3_Width;
					if(touchFence3==false)
					{
						BarrelRace.hitCount++;
						//touchFence1=false;
					}
				}
				if(horse.x<fence3_Width && horse.y < fence3_Height)
				{
					touchFence3=true;
				}
				else{
					touchFence3=false;
				}
				if(horse.x+horseDiameter>fence4_x && horse.y < fence4_Height){
					
					BarrelRace.horsePosition.x = fence4_x-horseDiameter;
					if(touchFence4==false)
					{
						BarrelRace.hitCount++;
						//touchFence1=false;
					}
				}
				if(horse.x+horseDiameter>fence4_x && horse.y < fence4_Height)
				{
					touchFence4=true;
				}
				else{
					touchFence4=false;
				}
				if(horse.y<fence5_y+fence5_Height){
					
					BarrelRace.horsePosition.y = fence1_Height ;
					
					if(touchFence5==false)
					{
						BarrelRace.hitCount++;
						//touchFence1=false;
					}
				}
				if(horse.y<fence5_y+fence5_Height )
				{
					touchFence5=true;
				}
				else{
					touchFence5=false;
				}
		
		
		
				///Check if the barrell one is in range and check the whole barrell is circled or not plus check the line connecting barrell is crossed or not
		if(barrel_1_Collision == false){
			//Log.v("Outside "+ horseX +"Orignal "+ ((int) BarrelRace.horsePosition.x + horseRadius),"Outside "+ horseX);
			barrel_1_Collision = rectBarrel_1.checkBorderTouched(new Point((int)horseX, (int)horseY)); 
			//Log.v(""+barrel_1_Collision,""+ barrel_1_Collision);
			if(barrel_1_Collision == true){
			l = new Line(horseX, horseY, barrell_1x+barrelRadius, barrell_1y+barrelRadius);

			p = l.perpendicularLine();
			l1x= horseX;
			l1y= horseY;
			l2x = barrell_1x+barrelRadius;
			l2y =  barrell_1y+barrelRadius;
			}
		}
		if(barrel_1_Collision == true){
			boolean inside_rect_1 = rectBarrel_1.inRectangle(new Point((int)horseX ,(int)horseY));
			//Log.v("Inside Rectangle", ":"+inside_rect_1);
			if(inside_rect_1 == true){
				//Log.v("Inside Rectangle",":"+inside_rect_1);
				//barrel_1Bitmap = getRoundedCornerBitmap(barrel_1Bitmap, Color.BLUE, 2, 2,cont);
				p1x =horse.x+horseRadius;
				p1y =horse.y+horseRadius;
				p2x = barrell_1x+barrelRadius;
				p2y = barrell_1y+barrelRadius;
				
				Line continueLine = new Line((int)p1x, (int)p1y, (int)p2x,(int) p2y);
				Line continueLine1 = new Line((int)l1x, (int)l1y, (int)l2x,(int) l2y);
				//Check perpendicular distance..............................
				double newDistance1 = continueLine.pointDistance(g1_x+horseRadius, g1_y+horseRadius );
				
				if((int)newDistance1 == 0&& barrel_1_Collision == true && horseX < barrell_1x && nearest == 1){
					check_1 = greenDotBitmap;
				}
				double newDistance2 = continueLine.pointDistance(g2_x+horseRadius,g2_y+horseRadius);
				
				if((int)newDistance2 == 0 && barrel_1_Collision == true&& horseY < barrell_1y && nearest == 1){
					check_2 = greenDotBitmap;
				}
				double newDistance3 = continueLine.pointDistance(g3_x+horseRadius, g3_y+horseRadius);
				
				if((int)newDistance3 == 0&& barrel_1_Collision == true&& horseX > barrell_1x){
					check_3 = greenDotBitmap;
				}
				double newDistance4 = continueLine.pointDistance(g4_x+horseRadius,  g4_y+horseRadius);
				
				if((int)newDistance4 == 0&& barrel_1_Collision == true && horseY > barrell_1y && nearest == 1){
					check_4 = greenDotBitmap;
				}
				double LastDistance = continueLine1.pointDistance(horseX, horseY);
				if((int) LastDistance == 0 && barrel_1_Collision == true && check_1 == greenDotBitmap && check_2 == greenDotBitmap && check_3 == greenDotBitmap && check_4 == greenDotBitmap && horseX >= barrell_1x ){
				 	p34.setColor(Color.GREEN);

				}
				if(check_1 == greenDotBitmap &&check_2 == greenDotBitmap &&check_3 == greenDotBitmap &&check_4 == greenDotBitmap&& p34.getColor() == Color.GREEN){
					barrel_1Bitmap = getRoundedCornerBitmap(barrel_1Bitmap, Color.GREEN, 2, 2,cont);
				}

				
			
			}
			else if(inside_rect_1 == false){
				barrel_1_Collision = false;
				l1x= 0;
				l1y= 0;
				l2x = 0;
				l2y = 0;
				p1x= 0;
				p1y= 0;
				p2x = 0;
				p2y = 0;
				if(check_1 == greenDotBitmap && check_2 == greenDotBitmap && check_3 == greenDotBitmap && check_4 == greenDotBitmap && p34.getColor() == Color.GREEN )
					barrel_1Bitmap = getRoundedCornerBitmap(barrel_1Bitmap, Color.GREEN, 2, 2,cont);
					//barrel_1Bitmap = barrel_Bitmap;
				}
			}
		else if(check_1 == redDotBitmap || check_2 == redDotBitmap || check_1 == redDotBitmap || check_2 == redDotBitmap || p34.getColor() == Color.MAGENTA){
			barrel_1Bitmap = barrel_Bitmap;
			barrel_1Bitmap = Bitmap.createScaledBitmap(barrel_1Bitmap, barrelDiameter, barrelDiameter, true);
			check_1 = redDotBitmap;
			check_2 = redDotBitmap;
			check_3 = redDotBitmap;
			check_4 = redDotBitmap;
			//p34.setColor(Color.MAGENTA);
			
		}
		
		///////////////////////////////........................................................barrel 3;
		if(barrel_3_Collision == false){
			barrel_3_Collision = rectBarrel_3.checkBorderTouched(new Point((int)horseX, (int)horseY)); 
			if(barrel_3_Collision == true){
			n = new Line(horseX, horseY, barrell_3x+barrelRadius, barrell_3y+barrelRadius);

			n1x= horseX;
			n1y= horseY;
			n2x = barrell_3x+barrelRadius;
			n2y =  barrell_3y+barrelRadius;
			}
		}
		if(barrel_3_Collision == true){
			boolean inside_rect_3 = rectBarrel_3.inRectangle(new Point(horseX ,horseY));
			//Log.v("Inside Rectangle", ":"+inside_rect_3);
			if(inside_rect_3 == true){
				//Log.v("Inside Rectangle",":"+inside_rect_3);
				//barrel_3Bitmap = getRoundedCornerBitmap(barrel_3Bitmap, Color.BLUE, 2, 2,cont);
				r1x =horse.x+horseRadius;
				r1y =horse.y+horseRadius;
				r2x = barrell_3x+barrelRadius;
				r2y = barrell_3y+barrelRadius;
				
				Line continueLine = new Line((int)r1x, (int)r1y, (int)r2x,(int) r2y);
				Line continueLine1 = new Line((int)n1x, (int)n1y, (int)n2x,(int) n2y);
				
				double newDistance1 = continueLine.pointDistance(g9_x+horseRadius, g9_y+horseRadius);
				
				if((int)newDistance1 == 0&& barrel_3_Collision == true && horseX < barrell_3x && nearest == 3){
					check_9 = greenDotBitmap;
				}
				double newDistance2 = continueLine.pointDistance(g10_x+horseRadius,g10_y+horseRadius);
				
				if((int)newDistance2 == 0 && barrel_3_Collision == true&& horseY < barrell_3y && nearest == 3){
					check_10 = greenDotBitmap;
				}
				double newDistance3 = continueLine.pointDistance(g11_x+horseRadius, g11_y+horseRadius);
				
				if((int)newDistance3 == 0&& barrel_3_Collision == true&& horseX > barrell_3x && nearest == 3){
					check_11 = greenDotBitmap;
				}
				double newDistance4 = continueLine.pointDistance(g12_x+horseRadius,  g12_y+horseRadius);
				
				if((int)newDistance4 == 0&& barrel_3_Collision == true && horseY > barrell_3y && nearest == 3){
					check_12 = greenDotBitmap;
				}
				double LastDistance = continueLine1.pointDistance(horseX, horseY);
				if((int) LastDistance == 0 && barrel_3_Collision == true && check_9 == greenDotBitmap && check_10 == greenDotBitmap && check_11 == greenDotBitmap && check_12 == greenDotBitmap  && horseX <= barrell_3x ){
				 	p36.setColor(Color.GREEN);
				}
				if(check_9 == greenDotBitmap &&check_10 == greenDotBitmap &&check_11 == greenDotBitmap &&check_12 == greenDotBitmap  && p36.getColor() == Color.GREEN){
					barrel_3Bitmap = getRoundedCornerBitmap(barrel_3Bitmap, Color.GREEN, 2, 2,cont);
				}
				
				
			
			}
			else if(inside_rect_3 == false){
				barrel_3_Collision = false;
				n1x= 0;
				n1y= 0;
				n2x = 0;
				n2y = 0;
				r1x= 0;
				r1y= 0;
				r2x = 0;
				r2y = 0;
				if(check_9 == greenDotBitmap && check_10 == greenDotBitmap && check_11 == greenDotBitmap && check_12 == greenDotBitmap && p34.getColor() == Color.GREEN )
					barrel_3Bitmap = getRoundedCornerBitmap(barrel_3Bitmap, Color.GREEN, 2, 2,cont);
					//barrel_1Bitmap = barrel_Bitmap;
				}
			}
		else if(check_9 == redDotBitmap || check_10 == redDotBitmap || check_11 == redDotBitmap || check_12 == redDotBitmap || p36.getColor() == Color.MAGENTA){
			barrel_3Bitmap = barrel_Bitmap;
			barrel_3Bitmap = Bitmap.createScaledBitmap(barrel_3Bitmap, barrelDiameter, barrelDiameter, true);
			check_9 = redDotBitmap;
			check_10 = redDotBitmap;
			check_11 = redDotBitmap;
			check_12 = redDotBitmap;
			
		}
		///////////////////////////////........................................................barrel 2;
		if(barrel_2_Collision == false){
			barrel_2_Collision = rectBarrel_2.checkBorderTouched(new Point(horseX, horseY)); 
			if(barrel_2_Collision == true){
			m = new Line(horseX, horseY, barrell_2x+barrelRadius, barrell_2y+barrelRadius);

			//p = l.perpendicularLine();
			m1x= horseX;
			m1y= horseY;
			m2x = barrell_2x+barrelRadius;
			m2y =  barrell_2y+barrelRadius;
			}
		}
		if(barrel_2_Collision == true){
			boolean inside_rect_2 = rectBarrel_2.inRectangle(new Point(horseX ,horseY));
			//Log.v("Inside Rectangle", ":"+inside_rect_2);
			if(inside_rect_2 == true){
				//Log.v("Inside Rectangle",":"+inside_rect_2);
				//barrel_2Bitmap = getRoundedCornerBitmap(barrel_2Bitmap, Color.BLUE, 2, 2,cont);
				q1x =horse.x+horseRadius;
				q1y =horse.y+horseRadius;
				q2x = barrell_2x+barrelRadius;
				q2y = barrell_2y+barrelRadius;
				
				Line continueLine = new Line((int)q1x, (int)q1y, (int)q2x,(int) q2y);
				Line continueLine1 = new Line((int)m1x, (int)m1y, (int)m2x,(int) m2y);
				
				double newDistance1 = continueLine.pointDistance(g5_x+horseRadius, g5_y+horseRadius);
				
				if((int)newDistance1 == 0&& barrel_2_Collision == true && horseX < barrell_2x && nearest == 2){
					check_5 = greenDotBitmap;
				}
				double newDistance2 = continueLine.pointDistance(g6_x+horseRadius,g6_y+horseRadius);
				
				if((int)newDistance2 == 0 && barrel_2_Collision == true&& horseY < barrell_2y && nearest == 2){
					check_6 = greenDotBitmap;
				}
				double newDistance3 = continueLine.pointDistance(g7_x+horseRadius, g7_y+horseRadius);
				
				if((int)newDistance3 == 0&& barrel_2_Collision == true&& horseX > barrell_2x && (nearest == 2|| nearest == 3) ){
					check_7 = greenDotBitmap;
				}
				double newDistance4 = continueLine.pointDistance(g8_x+horseRadius,  g8_y+horseRadius);
				
				if((int)newDistance4 == 0&& barrel_2_Collision == true && horseY > barrell_2y ){
					check_8 = greenDotBitmap;
				}
				double LastDistance = continueLine1.pointDistance(horseX, horseY);
				if((int) LastDistance == 0 && barrel_2_Collision == true && check_6 == greenDotBitmap && check_7 == greenDotBitmap && check_8 == greenDotBitmap && check_5 == greenDotBitmap ){
				 	p35.setColor(Color.GREEN);
				}
				
				if(check_6 == greenDotBitmap &&check_7 == greenDotBitmap &&check_8 == greenDotBitmap &&check_9 == greenDotBitmap  && p35.getColor() == Color.GREEN){
					barrel_2Bitmap = getRoundedCornerBitmap(barrel_2Bitmap, Color.GREEN, 2, 2, cont);
				}
				
				
			
			}
			else if(inside_rect_2 == false){
				barrel_2_Collision = false;
				m1x= 0;
				m1y= 0;
				m2x = 0;
				m2y = 0;
				q1x= 0;
				q1y= 0;
				q2x = 0;
				q2y = 0;
				if(check_5 == greenDotBitmap && check_6 == greenDotBitmap && check_7 == greenDotBitmap && check_8 == greenDotBitmap && p35.getColor() == Color.GREEN )
					barrel_2Bitmap = getRoundedCornerBitmap(barrel_2Bitmap, Color.GREEN, 2, 2,cont);
					//barrel_1Bitmap = barrel_Bitmap;
				}
			}
		else if(check_5 == redDotBitmap || check_6 == redDotBitmap || check_7 == redDotBitmap || check_8 == redDotBitmap || p35.getColor() == Color.MAGENTA){
			barrel_2Bitmap = barrel_Bitmap;
			barrel_2Bitmap = Bitmap.createScaledBitmap(barrel_2Bitmap, barrelDiameter, barrelDiameter, true);
			check_5 = redDotBitmap;
			check_6 = redDotBitmap;
			check_7 = redDotBitmap;
			check_8 = redDotBitmap;
			
		}	
		/*boolean endGame=false;
		if(endGame=(rectBarrel_1.checkBorderTouched(new Point((int)horseX, (int)horseY))  )){		*/
			if(check_1 == greenDotBitmap && check_2 == greenDotBitmap && check_3 == greenDotBitmap && check_4 == greenDotBitmap && p34.getColor() == Color.GREEN && check_5 == greenDotBitmap && check_6 == greenDotBitmap && check_7 == greenDotBitmap && check_8 == greenDotBitmap && p35.getColor() == Color.GREEN && check_9 == greenDotBitmap && check_10 == greenDotBitmap && check_11 == greenDotBitmap && check_12 == greenDotBitmap && p36.getColor() == Color.GREEN && horse.x>= fence1_Width && horse.x <= fence2_x && horse.y>=fence2_y  ){
				gameWin= true;
				stopGame();
			//LEFT	//gameWinDialog();
			//	showGameOverDialog(R.string.game_win);
				
			}

		/*}*/
		
	}
	

	//Done by Vidhi vivek and apurva................................
	// To Draw all the objects on canvas.................................................
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawBitmap(backgroundBitmap, 0, 0, null);
	
			
			canvas.drawBitmap(rodeoBitmap, horse.x, horse.y, null);	
	
		
	//	canvas.drawRect(0,0,(screenWidth/2) - (screenWidth/15) ,screenHeight-(screenHeight/6), new Paint());

		//added
		canvas.drawBitmap(barrel_1Bitmap, barrell_1x, barrell_1y, null);
		canvas.drawBitmap(barrel_2Bitmap, barrell_2x, barrell_2y, null);
		canvas.drawBitmap(barrel_3Bitmap, barrell_3x, barrell_3y, null);

		canvas.drawBitmap(rodeoBitmap, horse.x, horse.y, null);

		canvas.drawBitmap(check_1, g1_x, g1_y, null);
		canvas.drawBitmap(check_2, g2_x, g2_y, null);
		canvas.drawBitmap(check_3, g3_x, g3_y, null);
		canvas.drawBitmap(check_4, g4_x, g4_y, null);

		canvas.drawBitmap(check_5, g5_x, g5_y, null);
		canvas.drawBitmap(check_6, g6_x, g6_y, null);
		canvas.drawBitmap(check_7, g7_x, g7_y, null);
		canvas.drawBitmap(check_8, g8_x, g8_y, null);

		canvas.drawBitmap(check_9, g9_x, g9_y, null);
		canvas.drawBitmap(check_10, g10_x, g10_y, null);
		canvas.drawBitmap(check_11, g11_x, g11_y, null);
		canvas.drawBitmap(check_12, g12_x, g12_y, null);
		

		canvas.drawBitmap(fence1, fence1_x, fence1_y, null);
		canvas.drawBitmap(fence2, fence2_x, fence2_y, null);
		canvas.drawBitmap(fence3, fence3_x, fence3_y, null);
		canvas.drawBitmap(fence4, fence4_x, fence4_y, null);
		canvas.drawBitmap(fence5, fence5_x, fence5_y, null);
		
		Paint p = new Paint();
		p.setColor(Color.BLUE);
		canvas.drawLine(l1x, l1y, l2x, l2y, p34);
		//canvas.drawLine(p1x, p1y, p2x, p2y, p);
		
		canvas.drawLine(m1x, m1y, m2x, m2y, p35);
		//canvas.drawLine(q1x, q1y, q2x, q2y, p);
		
		canvas.drawLine(n1x, n1y, n2x, n2y, p36);
		//canvas.drawLine(r1x, r1y, r2x, r2y, p);
	
	}
	//Done by Vivek....................................
		//To draw the Rectangle around the barrell..... 
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int color, int cornerDips, int borderDips, Context context) {
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
	            Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);

	    final int borderSizePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) borderDips,
	            context.getResources().getDisplayMetrics());
	    final int cornerSizePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) cornerDips,
	            context.getResources().getDisplayMetrics());
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	    final RectF rectF = new RectF(rect);

	    // prepare canvas for transfer
	    paint.setAntiAlias(true);
	    paint.setColor(0xFFFFFFFF);
	    paint.setStyle(Paint.Style.FILL);
	    canvas.drawARGB(0, 0, 0, 0);
	    canvas.drawRoundRect(rectF, cornerSizePx, cornerSizePx, paint);

	    // draw bitmap
	   // paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);

	    // draw border
	    paint.setColor(color);
	    paint.setStyle(Paint.Style.STROKE);
	    paint.setStrokeWidth((float) borderSizePx);
	    canvas.drawRoundRect(rectF, cornerSizePx, cornerSizePx, paint);

	    return output;
	}

	
	//Done by Vidhi and Vivek....................
	private static void showGameOverDialog(int messageId)
	   {
		String title;
		
		if(gameWin==true){
			
			title= "You completed the game in: "+ BarrelRace.gameTime+ "\n Penalty time is "+BarrelRace.totalhits+"* 0:05:00 = " +BarrelRace.penaltyTime;
		}
		else
		{
			title = resources.getString(messageId);
		}
	      // create a dialog displaying the given String
	      final AlertDialog.Builder dialogBuilder = 
	         new AlertDialog.Builder(cont);
	      dialogBuilder.setCancelable(false);

	      // display number of shots fired and total time elapsed
	      dialogBuilder.setMessage(title);
	      dialogBuilder.setPositiveButton(R.string.reset_game,
	         new DialogInterface.OnClickListener()
	         {
	            // called when "Reset Game" Button is pressed
	            @Override
	            public void onClick(DialogInterface dialog, int which)
	            {
	            	
	            	// barrel = new BarrelRace();
	    	       if(gameWin==true){
	    	    	  BarrelRace barrel = new BarrelRace();
	    	    	  barrel.saveScore(cont);
	    	    	   //BarrelRace.
	    	    	   Log.v("winner","winner");
	    	       }
	            	newGame();
	            	
	            } // end method onClick
	         } // end anonymous inner class
	      ); // end call to setPositiveButton

	      activity.runOnUiThread(
	         new Runnable() {
	            public void run()
	            {
	               dialogBuilder.show(); // display the dialog
	            } // end method run
	         } // end Runnable
	      ); // end call to runOnUiThread
	   } // end method showGameOverDialog
	
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{
		setWillNotDraw(false);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		boolean retry = true;
		
		while (retry)
		{
			try
			{
				retry = false;
			}
			finally {}
		}
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {}
	//Done By Apurva...................
		//Returns which has the minimum distance between the horse and all barrells............................
	public static double minDistance(int pointDistance1 , int pointDistance2 , int pointDistance3 ){
		double minimumCounter = 0 , smallest;
		double[] array = {pointDistance1,pointDistance2,pointDistance3};
		smallest = pointDistance1;
		for(int j=0; j<3 ; j++){
		smallest =	Math.min(smallest,array[j]);
		if(smallest == array[j]){
			minimumCounter = j+1;
		}
		}
		return minimumCounter;
	}
		
}


