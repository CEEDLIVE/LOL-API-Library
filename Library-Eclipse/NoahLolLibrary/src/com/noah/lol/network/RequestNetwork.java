/*
    Android Noah LOL API
    Copyright (c) 2014 Noah Hahm <dbgtdbz2@naver.com>
    http://globalbiz.tistory.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package com.noah.lol.network;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.noah.lol.config.EnvironmentConfig;
import com.noah.lol.exception.EnvironmentException;
import com.noah.lol.exception.NetworkException;
import com.noah.lol.listener.NetworkListener;

public class RequestNetwork {
	
	public final static int RATE_LIMIT_EXCEEDED = 429;
	public final static int BAD_REQUEST_ENVIRONMENT_CONFIG = -100;
	public final static int REQUEST_RESULT = 100;
	public final static int REQUEST_SUCCESS = 101;
	public final static int REQUEST_FAIL = 102;
	
	public final static String EXCEPTION_KEY = "NetworkException";
	public final static String RESPONSE_KEY = "ResponseBody";
				
	
	protected void asyncRequestGet(final String url, final NetworkListener<String> listener) {
		
		if (url == null) {
			return;
		}
		
		try {
			environmentCheck();
		} catch (EnvironmentException e) {
			if (listener != null) {
				e.setStatus(BAD_REQUEST_ENVIRONMENT_CONFIG);
				listener.onNetworkFail(BAD_REQUEST_ENVIRONMENT_CONFIG, e);
			}
			return;
		}
		
		final Handler handler = new Handler() {
			
			@Override
			public void handleMessage(Message msg) {	
				Bundle bundle = msg.getData();
				switch (msg.what) {
					case REQUEST_SUCCESS:
						String responseBody = bundle.getString(RESPONSE_KEY);
						
						if (listener != null && responseBody != null) {
							listener.onSuccess(responseBody);
						}
						break;
					case REQUEST_FAIL:
						NetworkException e = (NetworkException) bundle.getSerializable(EXCEPTION_KEY);
						if (listener != null) {
							listener.onNetworkFail(e.getStatus(), e);
						}
						break;
				}				
			}			
		};
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {

				String responseBody = null;
				Message message = new Message();
				
				try {
					responseBody = syncRequestGet(url);
					Bundle bundle = new Bundle();
					bundle.putString(RESPONSE_KEY, responseBody);
					message = Message.obtain(handler, REQUEST_SUCCESS);
					message.setData(bundle);					
					handler.sendMessage(message);						
				} catch (NetworkException e) {
					Bundle bundle = new Bundle();
					bundle.putSerializable(EXCEPTION_KEY, e);
					message = Message.obtain(handler, REQUEST_FAIL);
					message.setData(bundle);
					handler.sendMessage(message);					
					e.printStackTrace();
				}
				
			}
		}).start();
		
    }
	
	protected String syncRequestGet(String url) throws NetworkException {
		
		if (url == null) {
			return null;
		}
        
		String responseBody = null;
		HttpClient httpclient = new DefaultHttpClient();

		HttpGet httpget = null;
		try {
			httpget = new HttpGet(new URI(url));
			responseBody = httpclient.execute(httpget, responseHandler);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return responseBody;
    }
	
	private ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
		 
        @Override
        public String handleResponse(HttpResponse response) throws IOException {
 
            int status = response.getStatusLine().getStatusCode(); // HTTP Status Code        
            NetworkException e = null;    
            
            //Response Errors HTTP Status Code Check
            switch (status) {
            	case HttpStatus.SC_OK:
            		HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
            	case HttpStatus.SC_BAD_REQUEST: // 400 Bad request
            		e = new NetworkException("Bad request status code : " + status);
            		break;
            	case HttpStatus.SC_UNAUTHORIZED: // 401 Unauthorized   
                	e = new NetworkException("Unauthorized status code : " + status);  
            		break;
            	case HttpStatus.SC_NOT_FOUND: // 404 No summoner data found for any specified inputs
                	e = new NetworkException("No summoner data found for any specified inputs status code : " + status); 
            		break;
            	case RATE_LIMIT_EXCEEDED: // 429 Rate limit exceeded
                	e = new NetworkException("Rate limit exceeded status code : " + status); 
            		break;
            	case HttpStatus.SC_INTERNAL_SERVER_ERROR: // 500 Internal server error
                	e = new NetworkException("Internal server error status code : " + status); 
            		break;
            	case HttpStatus.SC_SERVICE_UNAVAILABLE: // 503 Service unavailable
                	e = new NetworkException("Service unavailable status code : " + status); 
            		break;
            }
            
            e.setStatus(status);
            throw e;
        }         
    };
    
    protected void environmentCheck() throws EnvironmentException  {
    	
		if (EnvironmentConfig.getInstance().getRegion() == null) {
			throw new EnvironmentException("Region Null Exception");
		}

		if (EnvironmentConfig.getInstance().getApiKey() == null) {
			throw new EnvironmentException("Api Key Null Exception");
		}
		
    }
    


}
