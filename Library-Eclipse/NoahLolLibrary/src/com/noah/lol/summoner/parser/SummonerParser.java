package com.noah.lol.summoner.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.noah.lol.summoner.dto.MasteryDto;
import com.noah.lol.summoner.dto.MasteryPageDto;
import com.noah.lol.summoner.dto.MasteryPagesDto;
import com.noah.lol.summoner.dto.SummonerDto;

public class SummonerParser {

	public SummonerDto parserSummonerInfo(String summonerName, String json) {

		try {
			JSONObject rootObject = new JSONObject(json);

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
	
	public MasteryPagesDto parserSummonerMastery(int summonerId, String json) {
		
		MasteryPagesDto masteryPagesDto = null;
		try {
			JSONObject rootObject = new JSONObject(json);
			JSONObject obj = rootObject.optJSONObject(Integer.toString(summonerId));
			
			//root parser
			masteryPagesDto = new MasteryPagesDto();
			Set<MasteryPageDto> pagesSet = new HashSet<MasteryPageDto>();	
			masteryPagesDto.setSummonerId(obj.optLong("summonerId"));
			JSONArray pages = obj.optJSONArray("pages");
			
			//pages parser
			for (int i=0;i<pages.length();i++) {
				JSONObject masteryPage = pages.optJSONObject(i);
				
				MasteryPageDto masteryPageDto = new MasteryPageDto();
				masteryPageDto.setCurrent(masteryPage.optBoolean("current"));
				masteryPageDto.setId(masteryPage.optLong("id"));
				masteryPageDto.setName(masteryPage.optString("name"));
				JSONArray masteries = masteryPage.optJSONArray("masteries");
				
				//masteryList parser
				List<MasteryDto> MasteryList = new ArrayList<MasteryDto>();
				for(int j=0;j<masteries.length();j++) {
					JSONObject mastery = masteries.optJSONObject(j);
					MasteryDto masteryDto = new MasteryDto();
					masteryDto.setId(mastery.optInt("id"));
					masteryDto.setRank(mastery.optInt("rank"));
					MasteryList.add(masteryDto);
				}
				
				masteryPageDto.setMasteries(MasteryList);
				pagesSet.add(masteryPageDto);
			}
			masteryPagesDto.setPages(pagesSet);		
			
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		return masteryPagesDto;
	}
	
}
