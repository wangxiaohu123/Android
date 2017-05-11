package com.ykx.baselibs.libs.sortlistview;

import com.ykx.baselibs.vo.AreaCode;

import java.io.Serializable;
import java.util.List;

public class SortModel implements Serializable{

	private String name;
	private String sortLetters;
	private Integer code;

	private List<AreaCode> areaCodes;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public List<AreaCode> getAreaCodes() {
		return areaCodes;
	}

	public void setAreaCodes(List<AreaCode> areaCodes) {
		this.areaCodes = areaCodes;
	}
}
