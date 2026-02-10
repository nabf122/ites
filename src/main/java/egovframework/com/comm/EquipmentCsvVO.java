package egovframework.com.comm;

public class EquipmentCsvVO {

	/**
     * 부서명
     */
    private String orgName = "";
	/**
     * 사용자명
     */
    private String userName = "";
    /**
     * 사용자 아이디
     */
    private String userId = "";
    /**
     * 망구분
     */
    private String networkType = "";
    /**
     * 장비타입
     */
    private String equipmentType = "";
    /**
     * 모델명
     */
    private String modelName = "";
    /**
     * 시리얼번호
     */
    private String serialNumber = "";
    /**
     * 상태
     */
    private String status = "";
    /**
     * 취득가액
     */
    private String acquisitionCost = "";
    /**
     * 취득시기
     */
    private String acquisitionDate = "";
    /**
     * 비고
     */
    private String remark = "";
    
	public EquipmentCsvVO(
			String orgName,
	        String userName,
	        String userId,
	        String networkType,
	        String equipmentType,
	        String serialNumber,
	        String modelName,
	        String acquisitionCost,
	        String acquisitionDate,
	        String status,
	        String remark) {
		// TODO Auto-generated constructor stub
		this.orgName = orgName;
	    this.userName = userName;
	    this.userId = userId;
	    this.networkType = networkType;
	    this.equipmentType = equipmentType;
	    this.serialNumber = serialNumber;
	    this.modelName = modelName;
	    this.acquisitionCost = acquisitionCost;
	    this.acquisitionDate = acquisitionDate;
	    this.status = status;
	    this.remark = remark;
	}
	
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
	public String getNetworkType() {
		return networkType;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	public String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAcquisitionCost() {
		return acquisitionCost;
	}
	public void setAcquisitionCost(String acquisitionCost) {
		this.acquisitionCost = acquisitionCost;
	}
	public String getAcquisitionDate() {
		return acquisitionDate;
	}
	public void setAcquisitionDate(String acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
    
    
}
