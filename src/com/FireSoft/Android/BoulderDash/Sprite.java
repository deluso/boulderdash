package com.FireSoft.Android.BoulderDash;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

@SuppressLint("DrawAllocation")
public class Sprite {
	int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private int x = 0;
    private int y = 0;
    private int xSpeed = 0, ySpeed = 0;
    private GameView gameView;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width, height;

	public Sprite(GameView gameView, Bitmap bmp) {
		this.gameView = gameView;
		this.bmp = bmp;
		this.width = bmp.getWidth() / BMP_COLUMNS;
		this.height = bmp.getHeight() / BMP_ROWS;
	}

	protected void update(int px, int py) {
		if (px != -1 || py != -1) {
			xSpeed = px;
			if (!((px < 0 && x <= 0) || (px > 0 && x >= gameView.getWidth() - height)))
				x = x + px;	
			ySpeed = py;
			if (!((py < 0 && y <= 0) || (py > 0 && y >= gameView.getHeight() - height))) 
				y = y + py;			
			currentFrame = ++currentFrame % BMP_COLUMNS;
		}
	}

	@SuppressLint("DrawAllocation")
	protected void onDraw(Canvas canvas) {
		int srcX = currentFrame * width;
		int srcY = getAnimationRow() * height;
		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect(x, y, x + width, y + height);
		canvas.drawBitmap(bmp, src, dst, null);
	}

	private int getAnimationRow() {
		double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
		int direction = (int) Math.round(dirDouble) % BMP_ROWS;
		return DIRECTION_TO_ANIMATION_MAP[direction];
	}
	
}