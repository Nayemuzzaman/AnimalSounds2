package com.lightcone.animalsounds2;

import java.util.Random;
import android.os.Bundle;
import android.app.Activity;
import android.media.MediaPlayer;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;


public class MainActivity extends Activity implements OnClickListener, 
	MediaPlayer.OnCompletionListener{
	
	private ImageView button1;
	private ImageView button2;
	private ImageView button3;
	private int sound1 = R.raw.cow;
	private int sound2 = R.raw.duck;
	private int sound3 = R.raw.sheep;
	private int buttonPressed;
	private MediaPlayer mp;
	private Random randomInt;
	private int randy;
	
	private int pictures[] = {R.drawable.button1, R.drawable.button2, R.drawable.button3,
			R.drawable.buttonbaby};
	
	private int sounds[] = {R.raw.cow, R.raw.duck, R.raw.sheep, R.raw.baby_laugh};
	
	private int currentChoice[] = {0, 1, 2};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Add click listeners to all the ImageButtons   
        button1 = (ImageView) findViewById(R.id.imageButton1);
        button2 = (ImageView) findViewById(R.id.imageButton2);
        button3 = (ImageView) findViewById(R.id.imageButton3);    
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        
        // Create integer random number generator
        randomInt = new Random();   
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present. 
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    // Required method if OnClickListener is implemented
    
	@Override
	public void onClick(View v) {
		
		// Play only one sound at a time	
		if(mp != null) mp.release();
		
		// Find which ImageButton was pressed and take appropriate action
		
		switch(v.getId()){
		
			// The first button
			case R.id.imageButton1:
				mp = MediaPlayer.create(this, sound1);
				buttonPressed = 1;
			break;
			
			// The second button
			case R.id.imageButton2:
				mp = MediaPlayer.create(this, sound2);
				buttonPressed = 2;
			break;
			
			// The third button
			case R.id.imageButton3:
				mp = MediaPlayer.create(this, sound3);
				buttonPressed = 3;
			break;
		
		}
		mp.setOnCompletionListener(this);  // To change image/sound for button
		mp.seekTo(0);
		mp.start();	
	}
	
	@Override
	public void onPause() {
		super.onPause();
		// Release the MediaPlayer if going into background
		if(mp != null) mp.release();
	}
	
	@Override
	public void  onCompletion  (MediaPlayer mp)  {
		switchMedia(buttonPressed);
	}
	
	// Method to process changing picture and sound after button press plays sound
	
	public void switchMedia(int buttonPressed){
		
		// Change image/sound randomly for button that was pressed after sound plays,
		// but don't let same image appear twice on screen at once
		
		while(true){
			randy = randomInt.nextInt(4) ;   // Random int from 0 to 3
			if(randy != currentChoice[0] && randy !=currentChoice[1] 
			    && randy != currentChoice[2]) {
					currentChoice[buttonPressed-1] = randy;
					break;
			}
		}
		
		switch (buttonPressed){
			case 1:
				button1.setImageResource(pictures[randy]);
				sound1 = sounds[randy];
				break;
				
			case 2:
				button2.setImageResource(pictures[randy]);
				sound2 = sounds[randy];
				break;
				
			case 3:
				button3.setImageResource(pictures[randy]);
				sound3 = sounds[randy];
				break;
		}	
	}
}