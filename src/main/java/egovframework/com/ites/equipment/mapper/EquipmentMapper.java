package egovframework.com.ites.equipment.mapper;

import java.util.List;

import egovframework.com.ites.equipment.service.EquipmentVO;

public interface EquipmentMapper {

	List<EquipmentVO> selectEquipmentList(String userId);

    void insertEquipment(EquipmentVO vo);

    void updateEquipment(EquipmentVO vo);

    void deleteEquipment(String equipmentId);

	List<EquipmentVO> selectAllEquipmentList();
}
