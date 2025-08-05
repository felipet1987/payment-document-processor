package cl.felipe.processor.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentProcessingServiceTest {
    @Autowired
    private PaymentProcessingService processJsonService;

    @Test
    void testExecute() {
        String json = "[{\"DocType\":\"F01\",\"NroDocInterno\":\"123\",\"ContentBase64\":\"eyJpZCI6IjEyMyIsIm5hbWUiOiJUZXN0MSIsInR5cGUiOiJGMDEiLCJ0b3RhbEFQYWdhciI6MTAwLjAsIm1lZGlvUGFnbyI6IkNSRURJVE8ifQ==\"}]";
        String result = processJsonService.execute(json);
        assertNotNull(result);
        assertTrue(result.contains("Reporte de procesamiento"));
        assertTrue(result.contains("CREDITO"));
        assertTrue(result.contains("100.00"));
        assertTrue(result.contains("Init time"));
        assertTrue(result.contains("End time"));
        assertTrue(result.contains("Duration"));
    }

    @Test
    void testExecute_withInvalidJson_throwsException() {
        String invalidJson = "not-a-json";
        assertThrows(RuntimeException.class, () -> processJsonService.execute(invalidJson));
    }
}
