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
package com.noah.lol.summoner;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;

import com.noah.lol.config.EnvironmentConfig;
import com.noah.lol.listener.NetworkListener;
import com.noah.lol.listener.SimpleNetworkListener;
import com.noah.lol.network.NetworkException;
import com.noah.lol.network.RequestNetwork;

public class Summoner extends RequestNetwork {
	
	
	private String buildSummonerNameApiUrl(String summonerName, String region) {
		
		Uri.Builder urlBuilder = new Uri.Builder();
		urlBuilder.scheme("https")
		.appendEncodedPath("/" + region + ".api.pvp.net")
		.appendEncodedPath("api/lol")
		.appendEncodedPath(region)
		.appendEncodedPath("v1.4/summoner/by-name")
		.appendEncodedPath(summonerName)
		.appendQueryParameter("api_key", EnvironmentConfig.getInstance().getApiKey());
		
		return urlBuilder.toString();
	}
	
	public void getAsyncSummonerInfo(final String summonerName, String region, final SimpleNetworkListener<SummonerDto> listener) {
						
		String url = buildSummonerNameApiUrl(summonerName, region);
				
		asyncRequestGet(url, new NetworkListener<String>(){

			@Override
			public void onSuccess(String data) {
				SummonerDto summonerDto = parserSummonerInfoJson(summonerName, data);
				if (listener != null) {
					listener.onSuccess(summonerDto);
				}				
			}

			@Override
			public void onNetworkFail(int errorCode, NetworkException e) {
				if (listener != null) {
					listener.onNetworkFail(errorCode, e);
				}				
			}
						
		});
        
	}

	public SummonerDto getSyncSummonerInfo(String summonerName, String region) throws NetworkException {	
		String url = buildSummonerNameApiUrl(summonerName, region);		
		String json = syncRequestGet(url);				
		return parserSummonerInfoJson(summonerName, json);	       
	}
	
	private SummonerDto parserSummonerInfoJson(String summonerName, String json) {
		JSONObject rootObject;
		try {
			rootObject = new JSONObject(json);

			JSONObject obj = rootObject.getJSONObject(summonerName);
			
			SummonerDto summonerDto = new SummonerDto();
			summonerDto.setId(obj.optLong("id"));
			summonerDto.setName(obj.optString("name"));
			summonerDto.setProfileIconId(obj.optInt("profileIconId"));
			summonerDto.setRevisionDate(obj.optLong("revisionDate"));
			summonerDto.setSummonerLevel(obj.optLong("summonerLevel"));
			
			return summonerDto;
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	
}
