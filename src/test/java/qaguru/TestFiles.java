package qaguru;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class TestFiles {

    ClassLoader classLoader = TestFiles.class.getClassLoader();


    @Test
    void testCsvReader() throws Exception {
        InputStream is = classLoader.getResourceAsStream("GetFiles.zip");
        ZipInputStream zip = new ZipInputStream(is);
        ZipEntry entry;
        while ((entry = zip.getNextEntry()) != null) {
            if (entry.getName().contains("readers.csv")) {
                try {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zip, UTF_8));
                    List<String[]> csv = csvReader.readAll();
                    assertThat(csv).contains(
                            new String[]{"Reader","OS","version"},
                            new String[]{"Mercury","android","1.2"},
                            new String[]{"Azure","android","1.02"},
                            new String[]{"Atoll","android","1.2"}
                    );
                } finally {
                    is.close();
                }
            }
        }
    }

    @Test
    void testXLSXReader() throws Exception {
        InputStream is = classLoader.getResourceAsStream("GetFiles.zip");
        ZipInputStream zis = new ZipInputStream(is);
        ZipFile zipFile = new ZipFile(new File("src/test/resources/" + "GetFiles.zip"));
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.getName().contains("export_product_2022.xlsx")) {
                try (InputStream stream = zipFile.getInputStream(entry)) {
                    XLS xls = new XLS(stream);
                    assertThat(
                            xls.excel.getSheetAt(0)
                                    .getRow(20)
                                    .getCell(2)
                                    .getStringCellValue()
                    ).contains("Рик");
                }
            }
        }

    }


    @Test
    void testPdfReader() throws Exception {
        InputStream is = classLoader.getResourceAsStream("GetFiles.zip");
        ZipInputStream zip = new ZipInputStream(is);
        ZipEntry entry;
        ZipFile zipFile = new ZipFile(new File("src/test/resources/" + "GetFiles.zip"));
        while ((entry = zip.getNextEntry()) != null) {
            if (entry.getName().contains("Basics Java 2017.pdf")) {
                try (InputStream stream = zipFile.getInputStream(entry)) {
                    PDF pdf = new PDF(stream);
                    assertThat(pdf.text).contains("try...catch...finally");

                }
            }
        }

    }
}
