package vn.momo.dailysteps.service;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;

@Service
public class WordService {
    public void replaceTableData(XWPFDocument document, int tableIndex, int rowIndex, int cellIndex, String searchText, String replacementText) {
        try {
            // Get the table
            XWPFTable table = document.getTables().get(tableIndex);

            // Access the row and cell
            XWPFTableRow row = table.getRow(rowIndex + 2); // bỏ 2 dòng đầu
            XWPFTableCell cell = row.getCell(cellIndex);
//
////            // Clear existing cell text
////            cell.removeParagraph(0); // This removes the first paragraph, adjust as needed
//
//            // Set new text
//            XWPFParagraph newPara = cell.addParagraph();
//            XWPFRun run = newPara.createRun();
//            run.setText(replacementText);

            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                // Iterate through runs in the paragraph
                for (XWPFRun run : paragraph.getRuns()) {
                    String runText = run.getText(0);
                    if (runText != null && runText.contains(searchText)) {
                        // Replace text in the run
                        String newRunText = runText.replace(searchText, replacementText);
                        run.setText(newRunText, 0); // Set the text of the run, replacing the old text
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeRowFromTable(XWPFDocument document, int tableIndex, int startRowIndex) {
        try {
            // Get the table
            XWPFTable table = document.getTables().get(tableIndex);
            startRowIndex +=2; // ignore 2 first row

            // Remove rows from startRowIndex to the last row, iterating backward
            for(int i = table.getNumberOfRows() - 1; i >= startRowIndex; i--) {
                table.removeRow(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void replaceText(XWPFDocument document, String searchText, String replacementText) {
        // Iterate through paragraphs
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                String text = run.getText(0);
                if (text != null && text.contains(searchText)) {
                    text = text.replace(searchText, replacementText); // Replace text
                    run.setText(text, 0); // Set the run text to the updated value
                }
            }
        }
    }
}
