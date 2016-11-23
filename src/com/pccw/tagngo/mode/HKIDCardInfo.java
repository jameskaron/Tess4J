package com.pccw.tagngo.mode;

public class HKIDCardInfo {
	
	private String name;
	private String seqNo;
	private String birthDate;
	private String hkid;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getHkid() {
		return hkid;
	}
	public void setHkid(String hkid) {
		this.hkid = hkid;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Name: ").append(getName()).append("\r\n");
		sb.append("SeqNo: ").append(getSeqNo()).append("\r\n");
		sb.append("BirthDay: ").append(getBirthDate()).append("\r\n");
		sb.append("HKID: ").append(getHkid()).append("\r\n");
		return sb.toString();
	}

}
