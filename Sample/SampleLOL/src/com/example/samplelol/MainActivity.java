package com.example.samplelol;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.noah.lol.config.EnvironmentConfig;
import com.noah.lol.config.Region;
import com.noah.lol.exception.NetworkException;
import com.noah.lol.listener.SimpleNetworkListener;
import com.noah.lol.summoner.Summoner;
import com.noah.lol.summoner.SummonerDto;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText et = (EditText)findViewById(R.id.editText1);
        final TextView tvLevel = (TextView)findViewById(R.id.tv_level);
        final TextView tvId = (TextView)findViewById(R.id.tv_id); 
        Button btn = (Button)findViewById(R.id.button1);

        //initialize Api Key
        EnvironmentConfig.getInstance().initialize("ff718eb6-13b5-4a10-bfa7-3c7c0edbbf69", Region.KR);
		final Summoner summoner = new Summoner();
               
        //async
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {			
		        String name = et.getText().toString();
				summoner.getAsyncSummonerInfo(name, new SimpleNetworkListener<SummonerDto>() {
		        	
		        	@Override
		        	public void onSuccess(SummonerDto data) {
		        		Log.d("getAsyncSummonerInfo", data.getName());
		        		tvLevel.setText(""+data.getSummonerLevel());
		        		tvId.setText(""+data.getId());
		        	}
		        	
		        	@Override
		        	public void onNetworkFail(int errorCode, NetworkException e) {
		        		Toast.makeText(getApplicationContext(), e.getMessage() + e.getStatus(), Toast.LENGTH_SHORT).show();
		        		Log.d("errorCode : ", "" + errorCode);   
		        	}
		        	
		        });
				
				
			}
			
		});
        

        //sync
        btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

		        String name = et.getText().toString();
		        
				new AsyncTask<String, Void, SummonerDto>() {

					@Override
					protected SummonerDto doInBackground(String... params) {

						SummonerDto dto = null;
				        try {
							dto = summoner.getSyncSummonerInfo(params[0]);
						} catch (NetworkException e) {    
							e.printStackTrace();
						}
				        
						return dto;
					}
					
					protected void onPostExecute(SummonerDto result) {
		        		tvLevel.setText(""+result.getSummonerLevel());
		        		tvId.setText(""+result.getId());	
					}
					
				}.execute(name);
		        
			}
		});
        
        
        
        		
    }

}
