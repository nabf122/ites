package egovframework.com.ites.equipment;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;

import egovframework.com.ites.equipment.service.EquipmentService;
import egovframework.com.ites.equipment.service.EquipmentVO;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {

	@Autowired
    EquipmentService equipmentService;
    
    // 사용자 장비현황 조회
    @GetMapping("/list")
    @ResponseBody
    public List<EquipmentVO> getEquipmentListByUserId(String userId) {
    	return equipmentService.getEquipmentListByUserId(userId);
    }

    // 등록/수정/삭제
    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveAll(@RequestBody List<EquipmentVO> list) {
    	Map<String, Object> result = new HashMap<>();
    	
    	try {
    		equipmentService.saveAll(list);
    		
    		result.put("success", true);
            result.put("message", "저장되었습니다.");

            return ResponseEntity.ok(result);
    		
    	} catch (Exception e) {

            result.put("success", false);
            result.put("message", "저장 중 오류가 발생했습니다.");
            //result.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(result);
        }
    }

    @GetMapping("/excel")
    public void downloadExcel(HttpServletResponse response) throws Exception {

    	// 데이터 조회
        List<EquipmentVO> list = equipmentService.getAllEquipmentList();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("정보화장비현황");
        
        int rowNo = 0;
        
        // 헤더 생성
        Row header = sheet.createRow(rowNo++);
        String[] headers = {
            "순번", "부서명", "사용자명", "아이디(사번)", "망구분", "장비타입",
            "시리얼번호", "모델명", "취득가액", "취득시기", "상태", "비고"
        };

        for (int i = 0; i < headers.length; i++) {
            header.createCell(i).setCellValue(headers[i]);
            sheet.autoSizeColumn(i);
        }
        
        // 데이터 생성
        int rowNum = 1;
        for (EquipmentVO vo : list) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum - 1); 		// 순번
            row.createCell(1).setCellValue(vo.getOrgName());	// 부서명
            row.createCell(2).setCellValue(vo.getUserName());	// 사용자명
            row.createCell(3).setCellValue(vo.getUserId());		// 사용자아이디(사번)
            row.createCell(4).setCellValue(vo.getNetworkType().equals("INT") ? "내부망" : "외부망" );  // 망구분
            
            if(vo.getEquipmentType().equals("DESKTOP")) {		// 장비타입
            	row.createCell(5).setCellValue("데스크탑");
            } else if(vo.getEquipmentType().equals("LAPTOP")) {
            	row.createCell(5).setCellValue("노트북");
            } else if(vo.getEquipmentType().equals("KEYBOARD")) {
            	row.createCell(5).setCellValue("키보드");
            } else if(vo.getEquipmentType().equals("MONITOR")) {
            	row.createCell(5).setCellValue("모니터");
            } else {
            	row.createCell(5).setCellValue("기타장비");
            }
            	
            row.createCell(6).setCellValue(vo.getSerialNumber());		// 시리얼번호
            row.createCell(7).setCellValue(vo.getModelName());			// 모델명
            row.createCell(8).setCellValue(vo.getAcquisitionCost());	// 취득가액
            row.createCell(9).setCellValue(vo.getAcquisitionDate());	// 취득일자
            
            if(vo.getEquipmentType().equals("USE")) {	// 상태
            	row.createCell(10).setCellValue("사용중");
            } else if(vo.getEquipmentType().equals("BRK")) {
            	row.createCell(10).setCellValue("반납");
            } else {
            	row.createCell(10).setCellValue("고장");
            }
            
            row.createCell(11).setCellValue(vo.getRemark());	// 비고
        }

        // 응답 설정
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=equipment.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
        
     // 응답 설정
        response.setContentType(
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(
            "Content-Disposition", "attachment; filename=AllEquipmentList.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
