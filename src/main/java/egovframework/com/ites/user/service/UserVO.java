package egovframework.com.ites.user.service;

/**
 * @Class Name : UserVO.java
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
public class UserVO {
    
	/**
     * 아이디
     */
	private String userId = "";
    /**
     * 사용자명
     */
	private String userName = "";
    /**
     * 부서명
     */
	private String orgName = "";
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

}
