package com.example.barrelrace;
import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class BarrelRaceThread extends Thread {

	private SurfaceHolder surfaceHolder;
	private boolean threadIsRunning = true;
	private Canvas canvas;
	private Context context;
	private BarrelRaceView avoiderView;
	
	public BarrelRaceThread(SurfaceHolder holder, Context ctxt, BarrelRaceView aView)
	{
		surfaceHolder = holder;
		context = ctxt;
		avoiderView = aView;
		setName("AvoiderThread");
	}
	
	public void setRunning(boolean running) 
	{
		threadIsRunning = running;
	}
	
	@Override
	public void run()
	{
		Canvas canvas = null;
		long previousFrameTime = System.currentTimeMillis();
		
		while (threadIsRunning)
		{
			try
			{
				canvas = surfaceHolder.lockCanvas(null);
				
				synchronized(surfaceHolder)
				{
					long currentTime = System.currentTimeMillis();
					double elapsedTimeMS = currentTime - previousFrameTime;
					BarrelRaceView.totalElapsedTime += elapsedTimeMS / 1000.0;
					//AvoiderView.updatePositions(elapsedTimeMS);
					//AvoiderView.drawGameElements(canvas);
					previousFrameTime = currentTime;
				}
			}
			finally
			{
				if (canvas != null)
					surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
		
		
	}
	
}

