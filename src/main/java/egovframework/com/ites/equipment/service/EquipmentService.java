package egovframework.com.ites.equipment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import egovframework.com.ites.equipment.mapper.EquipmentMapper;

@Service
public class EquipmentService {

    private final EquipmentMapper equipmentMapper = null;

    public List<EquipmentVO> getUserEquipment(String userId) {
        return equipmentMapper.selectEquipment(userId);
    }

	public void saveAll(List<EquipmentVO> list) {
		// TODO Auto-generated method stub
		for (EquipmentVO vo : list) {
	        if (vo.getEquipmentId() == null) {
	        	equipmentMapper.insertEquipment(vo);
	        } else {
	        	equipmentMapper.updateEquipment(vo);
	        }
	    }
	}
    
    public void removeEquipment(String equipmentId) {
        equipmentMapper.deleteEquipment(equipmentId);
    }

}

