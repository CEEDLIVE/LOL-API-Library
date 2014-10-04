package com.noah.lol.summoner.dto;

import java.util.Set;

public class MasteryPagesDto {
	
	private Set<MasteryPageDto> pages;
	private long summonerId;
	
	
	public Set<MasteryPageDto> getPages() {
		return pages;
	}
	
	public void setPages(Set<MasteryPageDto> pages) {
		this.pages = pages;
	}
	
	public long getSummonerId() {
		return summonerId;
	}
	
	public void setSummonerId(long summonerId) {
		this.summonerId = summonerId;
	}
	
	
}
