package com.FireSoft.Android.BoulderDash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;
import android.view.*;

public class GameView extends SurfaceView {
	private Bitmap bmp;
	private Bitmap buttonL;
	private Bitmap buttonR;
	private Bitmap buttonT;
	private Bitmap buttonB;
	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;
	private Sprite sprite;
	private int posx = 0, posy = 0;
	public GameView(Context context) {
		super(context);
		gameLoopThread = new GameLoopThread(this);
		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;	
				gameLoopThread.setRunning(false);
				while (retry) {
					try {
						gameLoopThread.join();
						retry = false;
					} catch (InterruptedException e) {
					}
				}
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				//gameLoopThread.setRunning(false);
				gameLoopThread.start();
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}
		});
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bad4);
		buttonL = BitmapFactory.decodeResource(getResources(),	R.drawable.rbot);
		buttonR = BitmapFactory.decodeResource(getResources(),	R.drawable.rbot);
		buttonT = BitmapFactory.decodeResource(getResources(),	R.drawable.rbot);
		buttonB = BitmapFactory.decodeResource(getResources(),	R.drawable.rbot);
		sprite = new Sprite(this, bmp);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getX() >= 1 && 
			event.getX() <= buttonL.getWidth()  && 
			event.getY() >= this.getHeight() - (buttonL.getHeight() + buttonB.getHeight()) && 
			event.getY() <= this.getHeight() - buttonL.getHeight())
		{
			posx = -5;
			posy = 0;
		}
		else if (event.getX() >= (buttonL.getWidth() + buttonT.getWidth()) && 
			event.getX() <= (buttonL.getWidth() + buttonT.getWidth() + buttonR.getWidth()) && 
			event.getY() >= this.getHeight() - (buttonL.getHeight() + buttonB.getHeight()) && 
			event.getY() <= this.getHeight() - buttonB.getHeight())
		{
			posx = 5;
			posy = 0;
		}
		else if (event.getX() >= buttonL.getWidth() && 
			event.getX() <= (buttonL.getWidth() + buttonT.getWidth()) && 
			event.getY() >= this.getHeight() - (buttonL.getHeight() + buttonT.getHeight() + buttonB.getHeight()) && 
			event.getY() <= this.getHeight() - (buttonL.getHeight() + buttonB.getHeight()))
		{
			posx = 0;
			posy = -5;
		}
		else if (event.getX() >= buttonL.getWidth() && 
			event.getX() <= (buttonL.getWidth() + buttonB.getWidth()) && 
			event.getY() >= this.getHeight() - buttonB.getHeight() && 
			event.getY() <= this.getHeight())
		{
			posx = 0;
			posy = 5;
		}
		else
		{
			posx = -1;
			posy = -1;
		}
		Canvas c = holder.lockCanvas(null);
		onDraw(c);
		holder.unlockCanvasAndPost(c);
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(buttonL, 1, this.getHeight() - (buttonL.getHeight() + buttonB.getHeight()),null);
		canvas.drawBitmap(buttonR, (buttonL.getWidth() + buttonT.getWidth()), this.getHeight() - (buttonT.getHeight() + buttonR.getHeight()),null);
		canvas.drawBitmap(buttonT,  buttonL.getWidth(), this.getHeight() - (buttonL.getHeight() + buttonT.getHeight()+buttonB.getHeight()),null);
		canvas.drawBitmap(buttonB,  buttonL.getWidth(), this.getHeight() -  buttonB.getHeight(),null);
		sprite.update(posx,posy);
		sprite.onDraw(canvas);
	}
	
}