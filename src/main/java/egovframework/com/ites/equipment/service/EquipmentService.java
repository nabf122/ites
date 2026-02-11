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

    public List<EquipmentVO> getUserEquipment(String userId) {
        return equipmentMapper.selectEquipment(userId);
    }

	public void saveAll(List<EquipmentVO> list) {
		// TODO Auto-generated method stub
		
		String newEquipmentId = "";
		int i = 1; // 순번
		for (EquipmentVO vo : list) {
	        if (vo.getEquipmentId().equals("")) {
	        	newEquipmentId = UUID.randomUUID().toString();
	        	vo.setEquipmentId(newEquipmentId);
	        	vo.setSeq(i);
	        	equipmentMapper.insertEquipment(vo);
	        } else {
	        	equipmentMapper.updateEquipment(vo);
	        }
	        i++;
	    }
	}
    
    public void removeEquipment(String equipmentId) {
        equipmentMapper.deleteEquipment(equipmentId);
    }

}

