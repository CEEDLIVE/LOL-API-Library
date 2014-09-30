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

import com.noah.lol.exception.NetworkException;
import com.noah.lol.listener.NetworkListener;
import com.noah.lol.listener.SimpleNetworkListener;

public class Summoner extends SummonerNetwork {
		
			
	private void sendAsyncSummonerInfo(String url, final String summonerName, final SimpleNetworkListener<SummonerDto> listener) {
				
		asyncRequestGet(url, new NetworkListener<String>(){

			@Override
			public void onSuccess(String json) {
				
				if (json == null) {
					return;						
				}
				
				SummonerDto summonerDto = parserSummonerInfoJson(summonerName, json);
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

	public SummonerDto getSyncSummonerInfo(String summonerName) throws NetworkException {	
		String json = syncRequestGet(buildSummonerInfoUrl(summonerName, null));				
		return parserSummonerInfoJson(summonerName, json);	       
	}

	public void getAsyncSummonerInfo(String summonerName, SimpleNetworkListener<SummonerDto> listener) {
		sendAsyncSummonerInfo(buildSummonerInfoUrl(summonerName, listener), summonerName, listener);
	}
	
	public void getAsyncSummonerInfo(int summonerId, SimpleNetworkListener<SummonerDto> listener) {
		String summonerName = Integer.toString(summonerId);
		sendAsyncSummonerInfo(buildSummonerInfoUrl(summonerId, listener), summonerName, listener);
	}
	
	public SummonerDto getSyncSummonerInfo(int summonerId) throws NetworkException {
		String summonerName = Integer.toString(summonerId);
		return getSyncSummonerInfo(summonerName);	       
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
