package com.noah.lol.summoner.dto;

import java.util.List;

public class MasteryPageDto {
	private boolean current;
	private long id;
	private List<MasteryDto> masteries;
	private String name;
	public boolean isCurrent() {
		return current;
	}
	public void setCurrent(boolean current) {
		this.current = current;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<MasteryDto> getMasteries() {
		return masteries;
	}
	public void setMasteries(List<MasteryDto> masteries) {
		this.masteries = masteries;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
