package egovframework.com.comm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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


import egovframework.com.ites.equipment.service.EquipmentVO;


@Controller
@RequestMapping("/comm")
public class FileExportController {
	
	private static final Object FILE_LOCK = new Object();
	private static final String FILE_PATH = "C:/dev_kwpi.or.kr/workspace/ites/files/";
	private static final String fileName = "equipment_list.csv";
	
	// 등록/수정
    @PostMapping("/saveToFile")
    @ResponseBody
    public Map<String, Object> saveToFile(@RequestBody EquipmentSaveVO request) throws IOException {
        
    	Map<String, Object> result = new HashMap<>();
    	
    	File dir = new File(FILE_PATH);
    	if (!dir.exists()) dir.mkdirs();
        
        File file = new File(FILE_PATH + fileName);

        synchronized (FILE_LOCK) {
        	boolean isNewFile = !file.exists();

            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {

                // 파일 최초 생성 시 헤더
                if (isNewFile) {
                    writer.write("부서명,사용자명,사용자ID,망구분,장비타입,시리얼번호,모델명,취득가액,취득시기,상태,비고");
                    writer.newLine();
                }

                for (EquipmentVO e : request.getEquipmentList()) {
                    writer.write(String.join(",",
                    	escape(request.getOrgName()),
                        escape(request.getUserName()),
                        escape(request.getUserId()),
                        escape(e.getNetworkType()),
                        escape(e.getEquipmentType()),
                        escape(e.getSerialNumber()),
                        escape(e.getModelName()),
                        escape(e.getAcquisitionCost()),
                        escape(e.getAcquisitionDate()),
                        escape(e.getStatus()),
                        escape(e.getRemark())
                    ));
                    writer.newLine();
                }
                
                result.put("result", "OK");
            } catch (Exception e) {
                result.put("result", "FAIL");
                result.put("message", e.getMessage());
            }
        }
        return result;
    }
    
    private String escape(String value) {
        if (value == null) return "";
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
    
    // 사용자 정보 가져오기
    @PostMapping("/getUser")
    @ResponseBody
    public List<EquipmentCsvVO> getUser(@RequestBody EquipmentSaveVO request) throws IOException {
        
    	 List<EquipmentCsvVO> list = new ArrayList<>();
    	
    	String orgName = request.getOrgName();
    	String userName = request.getUserName();
    	String userId = request.getUserId();
    	
        File file = new File(FILE_PATH + fileName);
        
        // CSV 읽어서 사용자 찾기
        synchronized (FILE_LOCK) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF-8"))) {

                String line;
                boolean headerSkip = true;

                while ((line = br.readLine()) != null) {

                    if (headerSkip) {
                        headerSkip = false;
                        continue;
                    }
                    
                    
                    String[] cols = line.split(",", -1);
                    System.out.println(cols[0]);
                    System.out.println(cols[1]);
                    System.out.println(cols[2]);
                    System.out.println(cols[3]);
                    // CSV 값 정규화 (따옴표 제거 + trim)
                    String csvDept = normalize(cols[0]);
                    String csvUser = normalize(cols[1]);
                    String csvId   = normalize(cols[2]);

                    if (orgName.equals(csvDept)
                            && userName.equals(csvUser)
                            && userId.equals(csvId)) {

                        list.add(new EquipmentCsvVO(
                                csvDept,
                                csvUser,
                                csvId,
                                normalize(cols[3]),
                                normalize(cols[4]),
                                normalize(cols[5]),
                                normalize(cols[6]),
                                normalize(cols[7]),
                                normalize(cols[8]),
                                normalize(cols[9]),
                                normalize(cols[10])
                        ));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(list);
        return list;
    }
    
    private String normalize(String v) {
        if (v == null) return "";
        return v.trim().replaceAll("^\"|\"$", "");
    }
    
    // 엑셀 다운로드
    @GetMapping("/file/excel/download")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        
    	Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("정보화장비현황");

        int rowNo = 0;

        // 헤더
        Row header = sheet.createRow(rowNo++);
        String[] headers = {
            "부서명", "사용자명", "사용자ID", "망구분", "장비타입",
            "시리얼번호", "모델명", "취득가액", "취득시기", "상태", "비고"
        };

        for (int i = 0; i < headers.length; i++) {
            header.createCell(i).setCellValue(headers[i]);
            sheet.autoSizeColumn(i);
        }
        
        File file = new File(FILE_PATH + fileName);

        // CSV 읽어서 엑셀에 쓰기
        synchronized (FILE_LOCK) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF-8"))) {

                String line;
                boolean headerSkip = true;

                while ((line = br.readLine()) != null) {

                    if (headerSkip) {
                        headerSkip = false;
                        continue;
                    }

                    String[] cols = line.split(",", -1);

                    Row row = sheet.createRow(rowNo++);
                    String gubun = "";
                    String value = "";
                    for (int i = 0; i < cols.length; i++) {
                    	if(i == 3) { // 망구분
                    		gubun = cols[i].equals("INT") ? "내부망" : "외부망";
                    		row.createCell(i).setCellValue(gubun);
                    		
                    	} else if (i == 4) { // 장비타입
                    		if(cols[i].equals("DESKTOP")) {
                    			gubun = "데스크탑";
                        	} else if(cols[i].equals("LAPTOP")) {
                        		gubun = "노트북";
                        	} else if(cols[i].equals("KEYBOARD")) {
                        		gubun = "키보드";
                        	} else if(cols[i].equals("LAPTOP")) {
                        		gubun = "모니터";
                        	} else { 
                        		gubun = "기타장비";
                        	}
                    		
                    		row.createCell(i).setCellValue(gubun);
                    	} else if (i == 9) { // 상태
                    		if(cols[i].equals("USE")) {
                    			gubun = "사용중";
                        	} else if(cols[i].equals("BRK")) {
                        		gubun = "반납";
                        	} else { 
                        		gubun = "고장";
                        	}
                    		
                    		row.createCell(i).setCellValue(gubun);
                    	} else { // 그외 모든 항목
                    		value = cols[i];
                    		if (value != null) {
                    		    value = value.replaceAll("^\"|\"$", "");
                    		}
                    		row.createCell(i).setCellValue(value);
                    	}
                    }
                }
            }
        }

        // 응답 설정
        response.setContentType(
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(
            "Content-Disposition", "attachment; filename=equipment.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
