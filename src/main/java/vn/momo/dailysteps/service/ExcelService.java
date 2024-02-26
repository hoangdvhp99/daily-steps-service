package vn.momo.dailysteps.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@Service
public class ExcelService {
    public Map<String, List<String>> getMapHoDanTuFileExcel(String filePath) {
        Map<String, List<String>> mapHoDan = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {
            String key = "";
            List<String> listDan = new ArrayList<>();

            Sheet sheet = workbook.getSheetAt(0); // Read the first sheet
            for (Row row : sheet) {
                Cell cell0 = row.getCell(0); // Get the first cell
                Cell cell1 = row.getCell(1); // Get the second cell

                // Nếu là chủ hộ => gán key, add người đầu tien
                if (getCellValue(cell1).equalsIgnoreCase("Chủ hộ")) {
                    key = getCellValue(cell0);
                    listDan = new ArrayList<>();
                } else {
                    // Khong phai chu ho =>  add vao list
                    listDan = mapHoDan.get(key);
                }
                listDan.add(getCellValue(cell0));
                mapHoDan.put(key, listDan);

//                // Print the value of the first cell
//                System.out.print(getCellValue(cell0) + "\t" + getCellValue(cell1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapHoDan;
    }
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "NULL";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return Double.toString(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            default:
                return "UNKNOWN";
//                return null;
        }
    }
}
