package com.example.parker.pong;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Random;

/**
 * PongMainActivity
 * 
 * This is the activity for the Pong game. It attaches a PongAnimator to
 * an AnimationSurface.
 * 
 * @author Andrew Nuxoll
 * @author Steven R. Vegdahl
 * @version July 2013
 * 
 */
public class PongMainActivity extends Activity  implements View.OnClickListener, AdapterView.OnItemSelectedListener {

	public static boolean addBall = false;
	public static boolean reset = false;
	public static boolean pause = false;
	protected Button pauseButton;
	protected Button newBallButton;
	protected Button resetButton;
	protected String[] ballSizeOptions;
	protected String[] paddleSizeOptions;
	protected Spinner ballSpin;
	protected Spinner paddleSpin;

	public static int ballSize = 20;
	public static int paddleFactor = 8;
	/**
	 * creates an AnimationSurface containing a TestAnimator.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pong_main);

		pauseButton = (Button) findViewById(R.id.pauseButton);
		pauseButton.setOnClickListener(this);

		newBallButton = (Button) findViewById(R.id.newBallButton);
		newBallButton.setOnClickListener(this);

		resetButton = (Button) findViewById(R.id.resetButton);
		resetButton.setOnClickListener(this);

		ballSizeOptions = getResources().getStringArray(R.array.ball_size);
		paddleSizeOptions = getResources().getStringArray(R.array.paddle_size);

		ballSpin = (Spinner) findViewById(R.id.ballSizes);
		paddleSpin = (Spinner) findViewById(R.id.paddleSizes);

		/**
		 External Citation
		 Date: 26 February 2016
		 Problem: I could not figure out how to initialize and set up
		 listeners for spinners. I could not find anything clear
		 in API or on other sites that assist with writing code
		 Resource:
		 Code from Lab 4 of Object Oriented Design with Dr. Vegdahl
		 Solution: There was a spinner set up with a listener in this lab,
		 so I used that code as a basis for how I set up all of my different spinners
		 */
		ArrayAdapter ballSizeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, ballSizeOptions);
		ballSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ballSpin.setAdapter(ballSizeAdapter);
		ballSpin.setOnItemSelectedListener(this);

		ArrayAdapter paddleSizeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, paddleSizeOptions);
		paddleSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		paddleSpin.setAdapter(paddleSizeAdapter);
		paddleSpin.setOnItemSelectedListener(this);

		// Connect the animation surface with the animator
		AnimationSurface mySurface = (AnimationSurface) this
				.findViewById(R.id.animationSurface);
		mySurface.setAnimator(new PongAnimator());
	}




	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.pauseButton){
			pause = !pause;
		}
		if(v.getId() == R.id.newBallButton){
			addBall = true;
		}
		if(v.getId() == R.id.resetButton){
			reset = true;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Spinner spinner = (Spinner) parent;
		if(spinner.getId() == R.id.ballSizes){
			if(parent.getSelectedItemPosition() == 0){ballSize = 20;}
			else if(parent.getSelectedItemPosition() == 1){ballSize = 40;}
			else if(parent.getSelectedItemPosition() == 2){ballSize = 60;}
		}
		if(spinner.getId() == R.id.paddleSizes){
			if(parent.getSelectedItemPosition() == 0){
				paddleFactor = 8;
			}
			else if(parent.getSelectedItemPosition() == 1){
				paddleFactor = 16;
			}
			else if(parent.getSelectedItemPosition() == 2){
				paddleFactor = 24;;
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}
}
