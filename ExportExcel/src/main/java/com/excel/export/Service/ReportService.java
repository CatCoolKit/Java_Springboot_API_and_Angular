package com.excel.export.Service;

import com.excel.export.Repository.CourseRepository;
import com.excel.export.module.Course;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private CourseRepository courseRepository;

    public void generateExcel(HttpServletResponse response) throws IOException {
        List<Course> courses = courseRepository.findAll();

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet sheet = hssfWorkbook.createSheet("Courses Info");
        HSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Name");
        row.createCell(2).setCellValue("Price");

        int dataRowIndex = 1;

        for (Course course: courses) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(course.getId());
            dataRow.createCell(1).setCellValue(course.getName());
            dataRow.createCell(2).setCellValue(course.getPrice());
            dataRowIndex++;
        }

        ServletOutputStream ops = response.getOutputStream();
        hssfWorkbook.write(ops);
        hssfWorkbook.close();
        ops.close();

    }

    public void readXlsFile(InputStream inputStream) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = workbook.getSheetAt(0);

        for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            HSSFRow row = sheet.getRow(rowIndex);

            if (row != null) {
                Cell idCell = row.getCell(0);
                Cell nameCell = row.getCell(1);
                Cell priceCell = row.getCell(2);

                if (idCell != null && nameCell != null && priceCell != null) {
                    // Kiểm tra kiểu ô và lấy giá trị tương ứng
                    int id = (idCell.getCellType() == CellType.NUMERIC) ?
                            (int) idCell.getNumericCellValue() : 0; // Giá trị mặc định nếu không phải NUMERIC
                    String name = (nameCell.getCellType() == CellType.STRING) ?
                            nameCell.getStringCellValue() : ""; // Giá trị mặc định nếu không phải STRING
                    double price = (priceCell.getCellType() == CellType.NUMERIC) ?
                            priceCell.getNumericCellValue() : 0.0; // Giá trị mặc định nếu không phải NUMERIC

                    System.out.println("ID: " + id + ", Name: " + name + ", Price: " + price);
                }
            }
        }
        workbook.close();
    }

    public void readXlsxFile(InputStream inputStream) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

        for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            org.apache.poi.ss.usermodel.Row row = sheet.getRow(rowIndex);

            if (row != null) {
                Cell idCell = row.getCell(0);
                Cell nameCell = row.getCell(1);
                Cell priceCell = row.getCell(2);

                if (idCell != null && nameCell != null && priceCell != null) {
                    // Kiểm tra kiểu ô và lấy giá trị tương ứng
                    int id = (idCell.getCellType() == CellType.NUMERIC) ?
                            (int) idCell.getNumericCellValue() : 0; // Giá trị mặc định nếu không phải NUMERIC
                    String name = (nameCell.getCellType() == CellType.STRING) ?
                            nameCell.getStringCellValue() : ""; // Giá trị mặc định nếu không phải STRING
                    double price = (priceCell.getCellType() == CellType.NUMERIC) ?
                            priceCell.getNumericCellValue() : 0.0; // Giá trị mặc định nếu không phải NUMERIC

                    System.out.println("ID: " + id + ", Name: " + name + ", Price: " + price);
                }
            }
        }
        workbook.close();
    }

    public void generateDocx(HttpServletResponse response) throws IOException {
        List<Course> courses = courseRepository.findAll();

        // Thiết lập kiểu nội dung và tiêu đề
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=courses.docx");

        // Tạo một tài liệu Word mới
        XWPFDocument document = new XWPFDocument();

        // Tạo tiêu đề
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFRun titleRun = titleParagraph.createRun();
        titleRun.setText("Courses Info");
        titleRun.setBold(true);
        titleRun.setFontSize(20);

        // Thêm một đoạn trống
        document.createParagraph();

        // Tạo bảng cho dữ liệu
        for (Course course : courses) {
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText("ID: " + course.getId() + ", Name: " + course.getName() + ", Price: " + course.getPrice());
        }

        // Ghi tài liệu vào output stream
        ServletOutputStream ops = response.getOutputStream();
        document.write(ops);
        document.close();
        ops.close();
    }

    public void readDocxFile(InputStream inputStream) throws IOException {
        // Mở file Word
        XWPFDocument document = new XWPFDocument(inputStream);

        // Đọc các đoạn văn trong file
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            StringBuilder paragraphText = new StringBuilder();
            for (XWPFRun run : paragraph.getRuns()) {
                paragraphText.append(run.getText(0)); // Lấy nội dung của từng run
            }
            // In ra nội dung đoạn văn
            System.out.println("Paragraph: " + paragraphText.toString());
        }

        document.close(); // Đóng tài liệu sau khi xử lý xong
    }

}
