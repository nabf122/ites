package egovframework.com.comm;

import java.util.List;

import egovframework.com.ites.equipment.service.EquipmentVO;

public class EquipmentSaveVO {

    private String orgName;
    private String userName;
    private String userId;

    private List<EquipmentVO> equipmentList;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<EquipmentVO> getEquipmentList() {
		return equipmentList;
	}

	public void setEquipmentList(List<EquipmentVO> equipmentList) {
		this.equipmentList = equipmentList;
	}

}

