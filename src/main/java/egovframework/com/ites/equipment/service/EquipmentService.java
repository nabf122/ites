package egovframework.com.ites.equipment.service;

import java.util.List;

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

    public List<EquipmentVO> getUserEquipment(String userId) {
        return equipmentMapper.selectEquipment(userId);
    }

	public void saveAll(List<EquipmentVO> list) {
		// TODO Auto-generated method stub
		for (EquipmentVO vo : list) {
	        if (vo.getEquipmentNo() == null) {
	        	equipmentMapper.insertEquipment(vo);
	        } else {
	        	equipmentMapper.updateEquipment(vo);
	        }
	    }
	}
    
    public void removeEquipment(String equipmentNo) {
        equipmentMapper.deleteEquipment(equipmentNo);
    }

}

