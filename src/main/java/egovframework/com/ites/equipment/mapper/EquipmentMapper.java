package egovframework.com.ites.equipment.mapper;

import java.util.List;

import egovframework.com.ites.equipment.service.EquipmentVO;

public interface EquipmentMapper {

    List<EquipmentVO> selectEquipment(String userId);

    void insertEquipment(EquipmentVO vo);

    void updateEquipment(EquipmentVO vo);

    void deleteEquipment(String equipmentNo);
}
