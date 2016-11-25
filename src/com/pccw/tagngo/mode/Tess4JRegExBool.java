package com.pccw.tagngo.mode;

public class Tess4JRegExBool {

	private boolean isFindCommercial = false;
	private boolean isFindName = false;
	private boolean isFindId = false;
	private boolean isFindBirth = false;
	
	
	public Tess4JRegExBool(boolean isFindCommercial, boolean isFindName, boolean isFindId, boolean isFindBirth) {
		super();
		this.isFindCommercial = isFindCommercial;
		this.isFindName = isFindName;
		this.isFindId = isFindId;
		this.isFindBirth = isFindBirth;
	}
	
	public boolean isFindCommercial() {
		return isFindCommercial;
	}
	public void setFindCommercial(boolean isFindCommercial) {
		this.isFindCommercial = isFindCommercial;
	}
	public boolean isFindName() {
		return isFindName;
	}
	public void setFindName(boolean isFindName) {
		this.isFindName = isFindName;
	}
	public boolean isFindId() {
		return isFindId;
	}
	public void setFindId(boolean isFindId) {
		this.isFindId = isFindId;
	}
	public boolean isFindBirth() {
		return isFindBirth;
	}
	public void setFindBirth(boolean isFindBirth) {
		this.isFindBirth = isFindBirth;
	}
	@Override
	public String toString() {
		return "Tess4JRegExBool [isFindCommercial=" + isFindCommercial + ", isFindName=" + isFindName + ", isFindId="
				+ isFindId + ", isFindBirth=" + isFindBirth + "]";
	}
	
	
}
