package com.example.barrelrace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.Time;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

public class BarrelRace extends Activity implements SensorEventListener {

	private BarrelRaceView barrelRaceView;
	private SensorManager sensorManager;
	private Sensor accelerometer;
	public static Timer tmr;
	public static TimerTask tsk = null;
	public static Handler handler = new Handler();
	public static Runnable runnable;
	int mScrWidth, mScrHeight;
   public static android.graphics.PointF horsePosition, horseSpeed;
   public static Button startButton;
   public static boolean isStart =false;
       private Button pauseButton;
       public static TextView timerValue;
       public static TextView bestTime;
       private static long startTime = 0L;
       private static Handler customHandler = new Handler();
       static long timeInMilliseconds = 0L;
       static long timeSwapBuff = 0L;
       static long updatedTime = 0L;
       private static TextView fenceHitCount;
       public static int hitCount =0;
       public static String gameTime;
       public static String penaltyTime;
      public static int totalhits=0;
      private static String highestApScore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		getWindow().setFlags(0xFFFFFFFF, LayoutParams.FLAG_FULLSCREEN | LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		timerValue = (TextView) findViewById(R.id.timerValue);
		        startButton = (Button) findViewById(R.id.startButton);
		        startButton.setOnClickListener(new View.OnClickListener() {
		            public void onClick(View view) {
		            	isStart= true;
		                startTime = SystemClock.uptimeMillis();
		                customHandler.postDelayed(updateTimerThread, 0);
		                startButton.setEnabled(false);
		                bestTime.setText("Last Time is: "+readScore(getApplicationContext()));
		                highestApScore =readScore(getApplicationContext());
		            }
		        });

	
		
		barrelRaceView = (BarrelRaceView) findViewById(R.id.barrelRaceView);
		fenceHitCount = (TextView) findViewById(R.id.fenceHitCount);
	///	bestTime = (TextView) findViewById(R.id.highScore);
	///	bestTime.setText("Best Time is: "+readScore(getApplicationContext()));
	///	Log.v(" this is sparta 4", bestTime.getText().toString());
		// Add sensor listener 
		// Set the screen always portrait
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		
		 //get screen dimensions
        Display display = getWindowManager().getDefaultDisplay();  
        mScrWidth = display.getWidth(); 
        mScrHeight = display.getHeight();
        horsePosition = new android.graphics.PointF();
    	horseSpeed = new android.graphics.PointF();
        
        //create variables for horse position and speed
    	horsePosition.x = mScrWidth/2; 
        horsePosition.y =mScrHeight; 
        horseSpeed.x = 0;
        horseSpeed.y = 0; 
        		
        //listener for accelerometer, use anonymous class for simplicity
        ((SensorManager)getSystemService(Context.SENSOR_SERVICE)).registerListener(
    		new SensorEventListener() {    
    			@Override  
    			public void onSensorChanged(SensorEvent event) {  
    			    //set horse speed based on phone tilt (ignore Z axis)
    				//timer event will redraw horse
    				if(isStart==true){
    					horseSpeed.y = event.values[0];
        				horseSpeed.x = event.values[1];
    				}
    				
    				
    			}
        		@Override  
        		public void onAccuracyChanged(Sensor sensor, int accuracy) {} //ignore this event
        	},
        	((SensorManager)getSystemService(Context.SENSOR_SERVICE))
        	.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_FASTEST);
	}
	
	public static void stopTimer(){
		isStart= false;
	    
		 gameTime = timerValue.getText().toString();
		Log.v("gameTime ","is" +gameTime);
		Log.v("gameTime ","is" +gameTime.substring(2,4));
		Log.v("gameTime ","is" +gameTime.substring(0,1));
		Log.v("gameTime ","is" +gameTime.substring(5,8));
		
	    int sec= Integer.parseInt(gameTime.substring(2, 4)) ;
	    int min=Integer.parseInt(gameTime.substring(0, 1)) ;
		int msec=Integer.parseInt(gameTime.substring(5, 8)) ;
		Log.v("min ","" +min);
		Log.v("sec ","" +sec);
		Log.v("msec ","" +msec);
		Log.v("hitCount ","" +hitCount);
		sec = sec + (hitCount *5);
		totalhits = hitCount;
		int minutes = sec / 60;
		if(minutes!=0){
			min = min +minutes;
			sec= sec % 60;
		}
		penaltyTime = "" + min + ":"
                + String.format("%02d", sec) + ":"
                + String.format("%03d", msec);
			startButton.setEnabled(true);
			hitCount=0;
			fenceHitCount.setText(""+hitCount);
		 customHandler.removeCallbacks(updateTimerThread);
		 timerValue.setText("" + 0 + ":"
                 + String.format("%02d", 0) + ":"
                 + String.format("%03d", 0));
		 
		// bestTime.setText("Last Time is: "+penaltyTime);

	}
	
	 private static Runnable updateTimerThread = new Runnable() {
		         public void run() {
		        	 	if(isStart==true){
		        		 fenceHitCount.setText("" +hitCount);
		        		 
		        		 timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
			             updatedTime = timeSwapBuff + timeInMilliseconds;
			             int secs = (int) (updatedTime / 1000);
			             int mins = secs / 60;
			             secs = secs % 60;
			             int milliseconds = (int) (updatedTime % 1000);
			             timerValue.setText("" + mins + ":"
			                     + String.format("%02d", secs) + ":"
			                     + String.format("%03d", milliseconds));
			             customHandler.postDelayed(this, 0);
		        	 }
		        	 
		            
		         }
		     };

	
	   //listener for menu button on phone
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Exit"); //only one menu item
        return super.onCreateOptionsMenu(menu);
    }
    
    //listener for menu item clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// Handle item selection    
    	if (item.getTitle() == "Exit") //user clicked Exit
    		finish(); //will call onPause
   		return super.onOptionsItemSelected(item);    
    }
	
    public void unregisterSensors(){
    	sensorManager.unregisterListener(this);
    }
	
	@Override
	public void onPause() 
	{
		super.onPause();
//		tmr.cancel();
//		tmr = null;
		sensorManager.unregisterListener(this);
		BarrelRaceView.stopGame();
		finish();

	}
	
    @Override
    public void onResume() //App moved to foreground (also occurs at app startup)
    {
    	 super.onResume();
    	sensorManager.registerListener(this,
 		sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
 		SensorManager.SENSOR_DELAY_NORMAL);  // Registering this class as a listener for the accelerometer sensor
        //create timer task to move horse to new position
//        tmr = new Timer(); 
//    	tsk = new TimerTask() {
    	runnable = new Runnable() {
    		@Override
			public void run() {
				//if debugging with external device, 
				//  a cat log viewer will be needed on the device
				//android.util.Log.d(
				  //  "TiltBall","Timer Hit - " + horsePosition.x + ":" + horsePosition.y);
			    //move horse based on current speed
				horsePosition.x += horseSpeed.x;
				horsePosition.y += horseSpeed.y;
				//if horse goes off screen, reposition to opposite side of screen
				if (horsePosition.x+ BarrelRaceView.horseDiameter > mScrWidth){
					horsePosition.x=mScrWidth-BarrelRaceView.horseDiameter;
					//android.util.Log.d("Right","Right");
				}
				if (horsePosition.y+BarrelRaceView.horseDiameter+BarrelRaceView.horseDiameter+BarrelRaceView.horseRadius> mScrHeight){
					horsePosition.y=mScrHeight-BarrelRaceView.horseDiameter-BarrelRaceView.horseDiameter-BarrelRaceView.horseRadius;
					//android.util.Log.d("Bottom","Bottom");
				}// horsePosition.y=0;
				if (horsePosition.x < 0)
				{
					horsePosition.x=0;
					//android.util.Log.d("Left","Left");
				}//horsePosition.x=mScrWidth;
				if (horsePosition.y < 0)
				{
					horsePosition.y=0;
					//android.util.Log.d("Top","Top");
				}//horsePosition.y=mScrHeight;
				//update horse class instance
				if(isStart==true){
				BarrelRaceView.horse.x = horsePosition.x;
				BarrelRaceView.horse.y = horsePosition.y;
				
					BarrelRaceView.updatePositions();
				}
				
				//redraw horse. Must run in background thread to prevent thread lock.
				handler.post(new Runnable() {
				    public void run() {	
				    	barrelRaceView.invalidate();
				  }});
				handler.postDelayed(this, 0);
			}
		}; // TimerTask
		
//        tmr.schedule(tsk,10,10); //start timer
		
       
    } // onResume
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		barrelRaceView.releaseResources();
		System.runFinalizersOnExit(true); //wait for threads to exit before clearing app
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}
	
    //listener for config change. 
    //This is called when user tilts phone enough to trigger landscape view
    //we want our app to stay in portrait view, so bypass event 
    @Override 
    public void onConfigurationChanged(Configuration newConfig)
	{
       super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	

 public void saveScore(Context c){
	try {
		 FileOutputStream fos = c.openFileOutput("Game Score",
					Context.MODE_PRIVATE);	
		
		Log.v("save score","save");
		//fos.write(penaltyTime.getBytes());
				Log.v("High Score Ap in string", "-"+ highestApScore);
		    	int sec= Integer.parseInt(penaltyTime.substring(2, 4)) ;
		 	    int min=Integer.parseInt(penaltyTime.substring(0, 1)) ;
		 		int msec=Integer.parseInt(penaltyTime.substring(5, 8)) ;
					
					Log.v("Minute", ":"+ min);
					Log.v("sec", ":"+ sec);
					Log.v("Mili sec", ":"+ msec);
					
					//if(!highScore.equals("") || !highScore.equals(null)){
					Log.v("Highscore", highestApScore);
					Log.v("hsec", highestApScore.substring(2, 4));
					Log.v("hmin", highestApScore.substring(0, 1));
					Log.v("hmsec", highestApScore.substring(5, 8));
					
					
					int hsec= Integer.parseInt(highestApScore.substring(2, 4)) ;
			 	    int hmin=Integer.parseInt(highestApScore.substring(0, 1)) ;
			 		int hmsec=Integer.parseInt(highestApScore.substring(5, 8)) ;
			 		
			 		if((min>hmin) || ((min==hmin)&&(sec>hsec)) || ((min==hmin)&&(sec==hsec) &&(msec>hmsec)) && (!highestApScore.equals("0:00:000")))
			 		{
			 			fos.flush();
			 			fos.write(highestApScore.getBytes());
			 			bestTime.setText("Best Time is: "+highestApScore);

			 		}else{
			 			fos.flush();

			 			fos.write(penaltyTime.getBytes());
			 			bestTime.setText("Best Time is: "+penaltyTime);
			 		}
			 		
			 	
			 		Log.v("Minute1", ":"+ hmin);
					Log.v("sec1", ":"+ hsec);
					Log.v("Mili sec1", ":"+ hmsec);
					//}
					
		    
		  
			//msec.highScore.substring(6, 7);
			//if
			//Saves the scores in android's file system.
			
			//if(highScore.equalsIgnoreCase(timerValue.getText().toString())){
				
			//}
			
			

		    
		    
		    /*else if(highScore.equals("00")){
		    	highScore = "" + 0 + ":"
		                 + String.format("%02d", 0) + ":"
		                 + String.format("%03d", 0);
		    	fos.write(highScore.getBytes());
				 
		    }*/
		    fos.close();
	} catch (IOException e) {
		e.printStackTrace();
	}

}

public String readScore(Context c){
	try{
		Log.v("read" , "aa");
		String GAme = "Game Score";
		File file = c.getFileStreamPath(GAme);
		Log.v("jj" +file.getName(), "aa");
		//If file exists, message is displayed to the user.
		if (file.exists()) {
			InputStream in = c.openFileInput("Game Score");
			Log.v("file" +file.getName(), " exists!!!");
			if (in != null) {
				Log.v("file" +file.getName(), " is Not null!!!");
				InputStreamReader tmp = new InputStreamReader(in);
				BufferedReader reader = new BufferedReader(tmp);
				String str;
				StringBuilder buffer = new StringBuilder();
				while ((str = reader.readLine()) != null) {
					//Log.v();
					Log.v(" this is sparta :", str);
					//buffer.append(str);
					buffer.insert(0, str);
					Log.v(" this is sparta2 :", buffer.toString());
				in.close();
				reader.close();
				tmp.close();
				
				if(buffer.equals(""))
				{
					buffer.insert(0, "" + 9 + ":"
			                 + String.format("%02d", 0) + ":"
			                 + String.format("%03d", 0));
					
				}
				else{
					Log.v(" this is sparta3 :", buffer.toString());
					return buffer.toString();
					}
				}	
			}
		}
		
		else{
			
			return "" + 9 + ":"
	                 + String.format("%02d", 0) + ":"
	                 + String.format("%03d", 0);
			}
		
		}
	catch(IOException ex){
		return "" + 9 + ":"
                + String.format("%02d", 0) + ":"
                + String.format("%03d", 0);
	}
	return "" + 9 + ":"
            + String.format("%02d", 0) + ":"
           + String.format("%03d", 0) ;
	
}
}
