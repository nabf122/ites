package egovframework.com.ites.equipment.mapper;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.com.ites.equipment.service.EquipmentVO;

@Mapper
public interface EquipmentMapper {

    List<EquipmentVO> selectEquipment(String userId);

    void insertEquipment(EquipmentVO vo);

    void updateEquipment(EquipmentVO vo);

    void deleteEquipment(String equipmentId);
}
