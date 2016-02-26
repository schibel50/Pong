package com.example.parker.pong;

import android.content.Context;
import android.graphics.*;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;


/**
 * class that animates a ball repeatedly moving diagonally on
 * simple background
 * 
 * @author Steve Vegdahl
 * @author Andrew Nuxoll
 * @version February 2016
 */

public class PongAnimator implements Animator{

	// instance variables
	protected ArrayList<Ball> balls = new ArrayList<Ball>();
	Random randNum = new Random(); //random number generator that assists in random placement of
								   //new ball
	private int rightWall;
	private int bottomWall;

	private boolean reverseX = false; // whether clock is ticking backwards
	private boolean reverseY = false;
	private boolean score = false;
	//public boolean addBall = false;

	/**
	 * Interval between animation frames: .03 seconds (i.e., about 33 times
	 * per second).
	 * 
	 * @return the time interval between frames, in milliseconds.
	 */
	public int interval() {
		return 30;
	}
	
	/**
	 * The background color: a light blue.
	 * 
	 * @return the background color onto which we will draw the image.
	 */
	public int backgroundColor() {
		// create/return the background color
		return Color.argb(255,127,0,255);
	}

	@Override
	public boolean doPause() {
		return false;
	}

	@Override
	public boolean doQuit() {
		return false;
	}

	/**
	 * Action to perform on clock tick
	 * 
	 * @param g the graphics object on which to draw
	 */
	public void tick(Canvas g) {
//		 bump our count either up or down by one, depending on whether
//		 we are in "backwards mode".
		Paint paddlePaint = new Paint();
		paddlePaint.setColor(Color.argb(255,255,255,255));
		Paddle mainPaddle = new Paddle(g.getWidth() - 100,
				((g.getHeight()/2) - (g.getHeight()/PongMainActivity.paddleFactor)), 50,
				((g.getHeight()/2) + (g.getHeight()/PongMainActivity.paddleFactor)));
		g.drawRect(mainPaddle.position, mainPaddle.yPos, mainPaddle.width,
				   mainPaddle.height, paddlePaint);
		g.drawRect(0,0,100, g.getHeight(), paddlePaint);
		g.drawRect(0,0, g.getWidth(), 100, paddlePaint);
		g.drawRect(0,g.getHeight()-100,g.getWidth(), g.getHeight(), paddlePaint);
		if(PongMainActivity.addBall){
			balls.add(new Ball(g.getWidth()/2, ((randNum.nextInt(g.getHeight()/2))+(g.getHeight()/4)),
							   randNum.nextInt(15)+10, randNum.nextInt(15)+10, randNum.nextBoolean(),
							   randNum.nextBoolean()));
			PongMainActivity.addBall = false;
		}
		if(PongMainActivity.reset){
			balls.clear();
			PongMainActivity.reset = false;
		}
		if(!balls.isEmpty() && !PongMainActivity.pause) {
			rightWall = g.getWidth();
			int j = balls.size();
			for (int i = 0; i < j; i++) {
					Ball tempBall = balls.get(i);
					tempBall.adjustPos();
					if(tempBall.score){}
					else if ((tempBall.x+PongMainActivity.ballSize >= mainPaddle.position) &&
						!(tempBall.y - PongMainActivity.ballSize > mainPaddle.height ||
						tempBall.y + PongMainActivity.ballSize < mainPaddle.yPos)){
						tempBall.xDirection = !tempBall.xDirection;
					}
					else if((tempBall.x+PongMainActivity.ballSize >= mainPaddle.position
							&& tempBall.x <= mainPaddle.width) &&
							(tempBall.y+PongMainActivity.ballSize > mainPaddle.height
							|| tempBall.y-PongMainActivity.ballSize < mainPaddle.yPos)){
						tempBall.score = true;
					}
					else if (tempBall.x <= PongMainActivity.ballSize + 100) {
						tempBall.xDirection = !tempBall.xDirection;
					}
					else if (tempBall.y >= g.getHeight()-100-PongMainActivity.ballSize || tempBall.y <= PongMainActivity.ballSize + 100) {
						tempBall.yDirection = !tempBall.yDirection;
					}
			}
			for (int k = 0; k < j; k++){
				Ball tempBall = balls.get(k);
				if(tempBall.x >=rightWall) {
					balls.remove(k);
					j--;
				}
			}
		}
		for(Ball item: balls){
			Paint ballPaint = new Paint();
			ballPaint.setColor(Color.argb(255,255,255,255));
			g.drawCircle(item.x, item.y, PongMainActivity.ballSize, ballPaint);
		}
	}

	/**
	 * reverse the ball's direction when the screen is tapped
	 */
	public void onTouch(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{

		}
	}
}//class TextAnimator
