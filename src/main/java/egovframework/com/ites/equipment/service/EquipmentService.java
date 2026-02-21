package egovframework.com.ites.equipment.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.ites.equipment.mapper.EquipmentMapper;

@Service
public class EquipmentService {

	@Autowired
    private final EquipmentMapper equipmentMapper;
    
	public EquipmentService(EquipmentMapper equipmentMapper) {
        this.equipmentMapper = equipmentMapper;
    }

	// 사용자 ID로 장비 현황 조회 
    public List<EquipmentVO> getEquipmentListByUserId(String userId) {
        return equipmentMapper.selectEquipmentList(userId);
    }
    
    // 엑셀 출력을 위해 모든 현황 조회
    public List<EquipmentVO> getAllEquipmentList() {
        return equipmentMapper.selectAllEquipmentList();
    }

    // 장비
	public void saveAll(List<EquipmentVO> list) {
		// TODO Auto-generated method stub
		String newEquipmentId = "";
				
		for (EquipmentVO vo : list) {
			if (vo.getEquipmentId().equals("") && vo.getRowStatus().equals("")) { // 생성
				newEquipmentId = UUID.randomUUID().toString(); // 신규 장비아이디 생성
			    vo.setEquipmentId(newEquipmentId);
			        	
			    equipmentMapper.insertEquipment(vo);
			} else if(!vo.getEquipmentId().equals("") && vo.getRowStatus().equals("U")) { // 수정
				equipmentMapper.updateEquipment(vo);
				
			} else if(!vo.getEquipmentId().equals("") && vo.getRowStatus().equals("D")) { // 삭제
				equipmentMapper.deleteEquipment(vo.getEquipmentId());
				
			}
		}
	}
    
    public void removeEquipment(String equipmentId) {
    	
        equipmentMapper.deleteEquipment(equipmentId);
    }

}

