package cl.felipe.processor.service;

import cl.felipe.processor.util.PaymentXmlWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import cl.felipe.processor.dto.PaymentRecordDTO;

class PaymentXmlWriterServiceTest {
    private final PaymentXmlWriter service = new PaymentXmlWriter();
    private final File outputDir = new File("output");

    @AfterEach
    void cleanUp() {
        if (outputDir.exists()) {
            for (File file : outputDir.listFiles()) {
                file.delete();
            }
            outputDir.delete();
        }
    }

    @Test
    void writePaymentDocumentsToXml_createsExpectedXmlFiles() throws Exception {
        List<PaymentRecordDTO> docs = List.of(
            new PaymentRecordDTO("10", "ClienteX", "F01", 123.45, "CREDITO"),
            new PaymentRecordDTO("20", "ClienteY", "F02", 67.89, "DEBITO")
        );
        service.writePaymentDocumentsToXml(docs);
        File file1 = new File(outputDir, "10.xml");
        File file2 = new File(outputDir, "20.xml");
        assertTrue(file1.exists());
        assertTrue(file2.exists());
        String content1 = Files.readString(file1.toPath(), StandardCharsets.ISO_8859_1);
        assertTrue(content1.contains("<Cliente>ClienteX</Cliente>"));
        assertTrue(content1.contains("<Tipo>F01</Tipo>"));
        assertTrue(content1.contains("<TotalAPagar>123.45</TotalAPagar>"));
        assertTrue(content1.contains("<MedioPago>CREDITO</MedioPago>"));
    }
}
