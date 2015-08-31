package com.vote.app;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RewardDetailActivity extends Activity implements Runnable {
ImageView imgView;
	
    private Handler handler = new Handler();  
    private ProgressDialog dialog;  
    String imageName="";
    //a string array for the file name  
    private String[] filepath; 
    
    //a variable to store the downloaded Bitmap  
    public Bitmap downloadedBitmap;  

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("rewarddetail Activity", "inside rewardetail page");
        setContentView(R.layout.reward_detail);
        Intent intent = getIntent();
        dialog = new ProgressDialog(this);  
        //set the title of the dialog  
        dialog.setTitle("Downloading Image");  
        //Set if the dialog can be skipped  
        dialog.setCancelable(false);  
        //Set if the dialog doesn't have a estimated time to be dismissed  
        dialog.setIndeterminate(true);  
		imgView = (ImageView) findViewById(R.id.imgView);
		 dialog.show();
		  new Thread(this).start(); 

        // fetch value from key-value pair and make it visible on TextView.
        String rewardDetail = intent.getStringExtra("rewarddesc");
        String rid = intent.getStringExtra("rid");
        String sdate = intent.getStringExtra("rewardsdate");
        String edate = intent.getStringExtra("rewardedate");
        imageName = intent.getStringExtra("imageName");
        Log.i("reward detail", rewardDetail);
        Log.i("reward  id", rid);
     //   Log.i("reward  sdate", sdate);
        Log.i("reward  edate", edate);
        Log.i("image Name", imageName);

        TextView offer = (TextView) findViewById(R.id.offertext);
        TextView validity = (TextView) findViewById(R.id.validitytext);
        offer.setText(rewardDetail);
        String validityText = "from " + sdate + " to " + edate;
        validity.setText(validityText);

    }
    private Bitmap DownloadBMP(String url) throws IOException  
    {  
        //create a URL object from the passed string  
        URL location = new URL(url);  
          
        /*Get the name of the file and its path, and break it into different parts. 
         *Store each of these parts as elements in the filepath array.*/  
        filepath = location.getFile().split("\u002F");  
          
        /*The last element of the filepath array will be the name of the file. 
         *Display the name in the progress dialog.*/  
        dialog.setMessage("Downloading " + filepath[filepath.length-1]);  
          
        //create a InputStream object, to read data from a remote location  
        InputStream input_s = location.openStream();   
          
        //use the BitmapFactory to decode the downloaded stream into a Bitmap  
        Bitmap returnedBMP = BitmapFactory.decodeStream(input_s);  
          
        //close the InputStream  
        input_s.close();  
          
        //returns the downloaded bitmap  
        return returnedBMP;  
    }  
//this method must be overridden, as we are implementing the Runnable interface  
    public void run()   
    {  
        //Download the image  
        try   
        {  
            downloadedBitmap = DownloadBMP("http://52.74.246.67:8080/images/"+imageName);  
        }   
        catch (IOException e)   
        {  
            //If the image couldn't be downloaded, use the standard 'image not found' bitmap  
            downloadedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);  
        }  
          
        //update the canvas from this thread using the Handler object  
        handler.post(new Runnable()   
        {  
            @Override  
            public void run()   
            {  
                //set the image on the View to display the downloaded Bitmap.  
            	imgView.setImageBitmap(downloadedBitmap);  
                  
                //close the progress dialog.  
                dialog.dismiss();  
            }  
        });   
    }  


    public void showReward(View view) {
        Intent intent = new Intent(this, RewardActivity.class);
        startActivity(intent);
    }
}
