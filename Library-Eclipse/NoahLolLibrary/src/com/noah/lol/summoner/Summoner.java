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

import android.net.Uri;

import com.noah.lol.config.EnvironmentConfig;
import com.noah.lol.exception.NetworkException;
import com.noah.lol.listener.NetworkListener;
import com.noah.lol.listener.SimpleNetworkListener;
import com.noah.lol.network.RequestNetwork;
import com.noah.lol.summoner.dto.MasteryPagesDto;
import com.noah.lol.summoner.dto.SummonerDto;
import com.noah.lol.summoner.parser.SummonerParser;

public class Summoner extends RequestNetwork {
		
	private SummonerParser parser;
	
	public Summoner() {
		parser = new SummonerParser();
	}
	
	private void sendAsyncSummonerInfo(String url, final String summonerName, final SimpleNetworkListener<SummonerDto> listener) {
		if (summonerName == null) {
			throw new NullPointerException("SummonerName NullPointerException");
		}
			
		asyncRequestGet(url, new NetworkListener<String>(){

			@Override
			public void onSuccess(String json) {
								
				SummonerDto summonerDto = parser.parserSummonerInfo(summonerName, json);
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
	
	private void sendAsyncMastery(String url, final int summonerId, final SimpleNetworkListener<MasteryPagesDto> listener) {

		asyncRequestGet(url, new NetworkListener<String>(){

			@Override
			public void onSuccess(String json) {
								
				MasteryPagesDto masteryDto = parser.parserSummonerMastery(summonerId, json);
				if (listener != null) {
					listener.onSuccess(masteryDto);
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
		String json = syncRequestGet(buildSummonerInfoUrl(summonerName));
		return json == null ? null : parser.parserSummonerInfo(summonerName, json);	       
	}
	
	public SummonerDto getSyncSummonerInfo(int summonerId) throws NetworkException {
		String summonerName = Integer.toString(summonerId);
		return getSyncSummonerInfo(summonerName);	       
	}	

	public void getAsyncSummonerInfo(String summonerName, SimpleNetworkListener<SummonerDto> listener) {
		sendAsyncSummonerInfo(buildSummonerInfoUrl(summonerName), summonerName, listener);
	}
	
	public void getAsyncSummonerInfo(int summonerId, SimpleNetworkListener<SummonerDto> listener) {
		String summonerName = Integer.toString(summonerId);
		sendAsyncSummonerInfo(buildSummonerInfoUrl(summonerId), summonerName, listener);
	}	
		
	public MasteryPagesDto getSyncMasteryInfo(int summonerId) throws NetworkException, NullPointerException {	
		String json = syncRequestGet(buildSummonerMasteryUrl(summonerId));				
		return json == null ? null : parser.parserSummonerMastery(summonerId, json);       
	}
	
	public void getAsyncMasteryInfo(int summonerId, SimpleNetworkListener<MasteryPagesDto> listener) {
		sendAsyncMastery(buildSummonerMasteryUrl(summonerId), summonerId, listener);
	}
	
	
	private String buildSummonerInfoUrl(String summonerName) {
		
		Uri.Builder urlBuilder = new Uri.Builder();
		urlBuilder.scheme("https")
		.appendEncodedPath("/" + EnvironmentConfig.getInstance().getRegion() + ".api.pvp.net")
		.appendEncodedPath("api/lol")
		.appendEncodedPath(EnvironmentConfig.getInstance().getRegion())
		.appendEncodedPath("v1.4/summoner/by-name")
		.appendEncodedPath(summonerName)
		.appendQueryParameter("api_key", EnvironmentConfig.getInstance().getApiKey());	

		return urlBuilder.toString();
	}
	
	private String buildSummonerInfoUrl(int summonerId) {

		Uri.Builder urlBuilder = new Uri.Builder();
		urlBuilder.scheme("https")
		.appendEncodedPath("/" + EnvironmentConfig.getInstance().getRegion() + ".api.pvp.net")
		.appendEncodedPath("api/lol")
		.appendEncodedPath(EnvironmentConfig.getInstance().getRegion())
		.appendEncodedPath("v1.4/summoner")
		.appendEncodedPath("" + summonerId)
		.appendQueryParameter("api_key", EnvironmentConfig.getInstance().getApiKey());
		
		return urlBuilder.toString();
	}

	private String buildSummonerMasteryUrl(int summonerId) {

		Uri.Builder urlBuilder = new Uri.Builder();
		urlBuilder.scheme("https")
		.appendEncodedPath("/" + EnvironmentConfig.getInstance().getRegion() + ".api.pvp.net")
		.appendEncodedPath("api/lol")
		.appendEncodedPath(EnvironmentConfig.getInstance().getRegion())
		.appendEncodedPath("v1.4/summoner")
		.appendEncodedPath("" + summonerId)
		.appendEncodedPath("masteries")
		.appendQueryParameter("api_key", EnvironmentConfig.getInstance().getApiKey());
		
		return urlBuilder.toString();
	}


	
}
