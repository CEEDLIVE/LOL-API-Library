package com.noah.lol.summoner;

import android.net.Uri;

import com.noah.lol.config.EnvironmentConfig;
import com.noah.lol.exception.EnvironmentException;
import com.noah.lol.listener.SimpleNetworkListener;
import com.noah.lol.network.RequestNetwork;

public class SummonerNetwork extends RequestNetwork {


	protected String buildSummonerInfoUrl(String summonerName, SimpleNetworkListener<SummonerDto> listener) {
		
		try {
			environmentCheck();
		} catch (EnvironmentException e) {
			if (listener != null) {
				e.setStatus(BAD_REQUEST_ENVIRONMENT_CONFIG);
				listener.onNetworkFail(BAD_REQUEST_ENVIRONMENT_CONFIG, e);
			}
			return null;
		}		
		
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
	
	protected String buildSummonerInfoUrl(int summonerId, SimpleNetworkListener<SummonerDto> listener) {

		try {
			environmentCheck();
		} catch (EnvironmentException e) {
			if (listener != null) {
				e.setStatus(BAD_REQUEST_ENVIRONMENT_CONFIG);
				listener.onNetworkFail(BAD_REQUEST_ENVIRONMENT_CONFIG, e);
			}
			return null;
		}
		
		Uri.Builder urlBuilder = new Uri.Builder();
		urlBuilder.scheme("https")
		.appendEncodedPath("/" + EnvironmentConfig.getInstance().getRegion() + ".api.pvp.net")
		.appendEncodedPath("api/lol")
		.appendEncodedPath(EnvironmentConfig.getInstance().getRegion())
		.appendEncodedPath("v1.4/summoner/")
		.appendEncodedPath("" + summonerId)
		.appendQueryParameter("api_key", EnvironmentConfig.getInstance().getApiKey());
		
		return urlBuilder.toString();
	}
	
}
