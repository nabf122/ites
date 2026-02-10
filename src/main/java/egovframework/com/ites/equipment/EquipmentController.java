package egovframework.com.ites.equipment;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.ites.equipment.service.EquipmentService;
import egovframework.com.ites.equipment.service.EquipmentVO;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {

    EquipmentService equipmentService;
    
    // 사용자 장비현황 조회
    @GetMapping("/list")
    public List<EquipmentVO> equipmentList(String userId) {
        return equipmentService.getUserEquipment(userId);
    }

    // 등록/수정
    @PostMapping("/save")
    public void save(@RequestBody List<EquipmentVO> list) {
        equipmentService.saveAll(list);
    }

    // 삭제
    @PostMapping("/delete")
    public void delete(String equipmentId) {
        equipmentService.removeEquipment(equipmentId);
    }
}
