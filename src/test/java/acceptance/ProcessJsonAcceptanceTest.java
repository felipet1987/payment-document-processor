package acceptance;

import cl.felipe.processor.PaymentProcessorApplication;
import cl.felipe.processor.service.PaymentProcessingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PaymentProcessorApplication.class)
class ProcessJsonAcceptanceTest {

    @Autowired
    private PaymentProcessingService processJsonService;

    @Test
    @DisplayName("Acceptance: valid JSON produces full HTML report")
    void acceptance_validJson_fullReport() {
        String json = "[{'DocType':'F01','NroDocInterno':'321','ContentBase64':'eyJpZCI6IjMyMSIsIm5hbWUiOiJUZXN0MiIsInR5cGUiOiJGMDEiLCJ0b3RhbEFQYWdhciI6MjAwLjAsIm1lZGlvUGFnbyI6IkRFU0NVRU5UQSBERSBDUkVESVRPIn0='}]".replace("'", "\"");
        String result = processJsonService.execute(json);
        assertNotNull(result);
        assertTrue(result.contains("<html>"));
        assertTrue(result.contains("Reporte de procesamiento"));
        assertTrue(result.contains("CREDITO") || result.contains("CREDITO".toLowerCase()));
        assertTrue(result.contains("200.00"));
        assertTrue(result.contains("Init time"));
        assertTrue(result.contains("End time"));
        assertTrue(result.contains("Duration"));
    }

    @Test
    @DisplayName("Acceptance: invalid JSON throws RuntimeException")
    void acceptance_invalidJson_throws() {
        String invalidJson = "{";
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> processJsonService.execute(invalidJson));
        assertTrue(thrown.getMessage().toLowerCase().contains("error"));
    }

    @Test
    @DisplayName("Acceptance: multiple documents in JSON array")
    void acceptance_multipleDocuments() {
        String json = "[{'DocType':'F01','NroDocInterno':'1','ContentBase64':'eyJpZCI6IjEiLCJuYW1lIjoiRG9jMSIsInR5cGUiOiJGMDEiLCJ0b3RhbEFQYWdhciI6MTAuMCwibWVkaW9QYWdvIjoiQ1JFRElUTyJ9'},{'DocType':'F01','NroDocInterno':'2','ContentBase64':'eyJpZCI6IjIiLCJuYW1lIjoiRG9jMiIsInR5cGUiOiJGMDEiLCJ0b3RhbEFQYWdhciI6MjAuMCwibWVkaW9QYWdvIjoiQ1JFRElUTyJ9'}]".replace("'", "\"");
        String result = processJsonService.execute(json);
        assertNotNull(result);
        // Now check for both summary and per-document details
        assertTrue(result.contains("Doc1") || result.contains(">1<"));
        assertTrue(result.contains("Doc2") || result.contains(">2<"));
        assertTrue(result.contains("10.00"));
        assertTrue(result.contains("20.00"));
        assertTrue(result.contains("Detalles de Documentos Procesados"));
        assertTrue(result.contains("Cantidad Docs"));
        assertTrue(result.contains("Total a Pagar"));
    }

    @Test
    @DisplayName("Functional: process real example JSON file")
    void functional_processRealExampleJson() throws Exception {
        java.nio.file.Path path = java.nio.file.Paths.get("src/test/Ejemplo entrega.json");
        String fileContent = new String(java.nio.file.Files.readAllBytes(path), java.nio.charset.StandardCharsets.UTF_8);
        // If the JSON is an object with a 'documents' array, extract the array for processing
        String toProcess = fileContent.trim().startsWith("{") && fileContent.contains("\"documents\"")
                ? new com.fasterxml.jackson.databind.ObjectMapper().readTree(fileContent).get("documents").toString()
                : fileContent;
        String result = processJsonService.execute(toProcess);
        assertNotNull(result);
        assertTrue(result.contains("Reporte de procesamiento"));
        assertTrue(result.contains("Detalles de Documentos Procesados"));
        assertTrue(result.contains("Cantidad Docs"));
        assertTrue(result.contains("Total a Pagar"));
        // Check for at least one known document number from the sample
        assertTrue(result.contains("30000000") || result.contains("30000001") || result.contains("30000002"));
    }
}
