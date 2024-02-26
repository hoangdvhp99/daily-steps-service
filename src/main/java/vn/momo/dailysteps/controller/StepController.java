package vn.momo.dailysteps.controller;

import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.momo.dailysteps.dto.ResultDTO;
import vn.momo.dailysteps.dto.step.StepRecordRQ;
import vn.momo.dailysteps.service.ExcelService;
import vn.momo.dailysteps.service.StepService;
import vn.momo.dailysteps.service.WordService;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/steps")
public class StepController extends BaseController {
    private final StepService stepService;
    private final ExcelService excelService;
    private final WordService wordService;

    @PostMapping("/record-steps")
    public ResponseEntity<?> recordSteps(@RequestBody StepRecordRQ request) {
        try {
            stepService.recordStep(request);
            return ResponseEntity.ok(new ResultDTO(200, "SUCCESS"));
        } catch (Exception exception) {
            return response(error(exception));
        }
    }

    @GetMapping("/leaderboard/daily")
    public ResponseEntity<?> leaderboard(
            @RequestParam(value = "date", defaultValue = "") String date,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        try {
            return ResponseEntity.ok(new ResultDTO(200, "SUCCESS", stepService.getLeaderboard(date, limit)));
        } catch (Exception exception) {
            return response(error(exception));
        }
    }

    @GetMapping("/total-steps/weekly/{userId}")
    public ResponseEntity<?> getWeeklyTotalSteps(@PathVariable long userId) {
        try {
            return ResponseEntity.ok(new ResultDTO(200, "SUCCESS", stepService.getWeeklyTotalSteps(userId)));
        } catch (Exception exception) {
            return response(error(exception));
        }
    }

    @GetMapping("/total-steps/monthly/{userId}")
    public ResponseEntity<?> getMonthlyTotalSteps(@PathVariable long userId) {
        try {
            return ResponseEntity.ok(new ResultDTO(200, "SUCCESS", stepService.getMonthlyTotalSteps(userId)));
        } catch (Exception exception) {
            return response(error(exception));
        }
    }

    @GetMapping("/createFile")
    public ResponseEntity<?> createFile() {
        try {
            Map<String, List<String>> mapHoDan = excelService.getMapHoDanTuFileExcel("D:/BacDoan/DsThon.xlsx");
            genDocFile("D:/BacDoan/GiayMoi.docx", mapHoDan);

            return ResponseEntity.ok(new ResultDTO(200, "SUCCESS", null));
        } catch (Exception exception) {
            return response(error(exception));
        }
    }

    public void genDocFile(String filePath, Map<String, List<String>> mapHoDan) {
        try {

            for (Map.Entry<String, List<String>> entry : mapHoDan.entrySet()) {
                String key = entry.getKey();
                List<String> listDan = entry.getValue();
                FileInputStream fis = new FileInputStream(filePath);
                XWPFDocument document = new XWPFDocument(fis);
                wordService.replaceText(document, "Owner", key);
                for (int i = 0; i < 5; i++) { // hard code 4 row
                    if (i < listDan.size()) {
                        wordService.replaceTableData(document, 1, i, 1, "MEM"+ (i+1), listDan.get(i));
                    } else {
//                        wordService.replaceTableData(document, 1, i, 1, "MEM"+ (i+1),"");
                        wordService.removeRowFromTable(document, 1, i);
                    }
                }

                // Save the changes to a new file
                try (FileOutputStream out = new FileOutputStream(filePath.replace(".docx", "_" + key + ".docx"))) {
                    document.write(out);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
