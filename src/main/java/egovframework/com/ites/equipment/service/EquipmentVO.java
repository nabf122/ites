package egovframework.com.ites.equipment.service;

/**
 * @Class Name : EquipmentVO.java
 * @Description : 사용자 정보 처리를 위한 VO 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2026. 2. 5.     송영진		최초작성
 *
 * @author 
 * @since 2026. 2. 5.
 * @version
 * @see
 *
 */
public class EquipmentVO {
    
	/**
     * 장비번호(자산관리번호)
     */
    private String equipmentNo = "";
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
    
	public String getEquipmentNo() {
		return equipmentNo;
	}
	public void setEquipmentNo(String equipmentNo) {
		this.equipmentNo = equipmentNo;
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
