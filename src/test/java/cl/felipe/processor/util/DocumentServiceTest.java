package cl.felipe.processor.service;

import cl.felipe.processor.util.PaymentDocumentDecoder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import cl.felipe.processor.dto.PaymentRecordDTO;

class PaymentDocumentDecoderTest {
    @Test
    void decodePaymentDocumentFromBase64Json_validBase64Json_returnsPaymentRecord() {
        String json = "{\"id\":\"1\",\"name\":\"Test\",\"type\":\"F01\",\"totalAPagar\":100.0,\"medioPago\":\"CREDITO\"}";
        String base64 = java.util.Base64.getEncoder().encodeToString(json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        PaymentRecordDTO doc = PaymentDocumentDecoder.decodePaymentDocumentFromBase64Json(base64);
        assertEquals("1", doc.id());
        assertEquals("Test", doc.name());
        assertEquals("F01", doc.type());
        assertEquals(100.0, doc.totalAPagar());
        assertEquals("CREDITO", doc.medioPago());
    }

    @Test
    void decodePaymentDocumentFromBase64Json_invalidBase64_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            PaymentDocumentDecoder.decodePaymentDocumentFromBase64Json("invalid-base64");
        });
    }
}
