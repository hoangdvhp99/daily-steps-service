package vn.momo.dailysteps.controller;

import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.momo.dailysteps.service.ExcelService;
import vn.momo.dailysteps.service.WordService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/utils")
public class UtilController extends BaseController  {
    private final ExcelService excelService;
    private final WordService wordService;
    @PostMapping("/gen-invitations-from-household-list-file")
    public ResponseEntity<?> createFile(@RequestParam("householdListFile") MultipartFile householdListFile, @RequestParam("invitationTemplateFile") MultipartFile invitationTemplateFile) {
        try {
            if (householdListFile.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            Map<String, List<String>> mapHoDan = excelService.getMapHoDanTuFileExcel(householdListFile);

            byte[] zipBytes = genDocFile(invitationTemplateFile, mapHoDan);
            ByteArrayResource resource = new ByteArrayResource(zipBytes);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=docxFiles.zip");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(zipBytes.length)
                    .contentType(MediaType.parseMediaType("application/zip"))
                    .body(resource);

        } catch (Exception exception) {
            return response(error(exception));
        }
    }

    public byte[] genDocFile(MultipartFile file, Map<String, List<String>> mapHoDan) {
        ByteArrayOutputStream baosZip = new ByteArrayOutputStream();
        try {
            try (ZipOutputStream zipOut = new ZipOutputStream(baosZip)) {
                for (Map.Entry<String, List<String>> entry : mapHoDan.entrySet()) {
                    // WRITE DOCX FILE
                    ByteArrayOutputStream baosDocx = new ByteArrayOutputStream();
                    String key = entry.getKey();
                    List<String> listDan = entry.getValue();
                    InputStream fis = file.getInputStream();
                    XWPFDocument document = new XWPFDocument(fis);
                    wordService.replaceText(document, "Owner", key);
                    for (int i = 0; i < 5; i++) { // hard code 4 row
                        if (i < listDan.size()) {
                            wordService.replaceTableData(document, 1, i, 1, "MEM"+ (i+1), listDan.get(i));
                        } else {
                            wordService.removeRowFromTable(document, 1, i);
                        }
                    }
                    document.write(baosDocx);

                    // Add the DOCX data to the ZIP file
                    ZipEntry zipEntry = new ZipEntry("GiayMoi_" + key + ".docx");
                    zipOut.putNextEntry(zipEntry);
                    ByteArrayInputStream baisDocx = new ByteArrayInputStream(baosDocx.toByteArray());
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = baisDocx.read(buffer)) >= 0) {
                        zipOut.write(buffer, 0, length);
                    }
                    zipOut.closeEntry();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baosZip.toByteArray();
    }

}
