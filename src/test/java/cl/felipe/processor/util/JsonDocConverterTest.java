package cl.felipe.processor.converter;

import cl.felipe.processor.util.PaymentJsonConverter;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import cl.felipe.processor.dto.PaymentDocumentDTO;

class PaymentJsonConverterTest {
    @Test
    void convert_validJson_returnsListOfPaymentDocumentDTO() throws Exception {
        String json = "[" +
                "{\"DocType\":\"F01\"," +
                "\"NroDocInterno\":\"123\"," +
                "\"ContentBase64\":\"eyJpZCI6IjEyMyIsIm5hbWUiOiJUZXN0IiwidHlwZSI6IkYwMSIsInRvdGFsQVBhZ2FyIjoxMjMuNCwibWVkaW9QYWdvIjoiQ1JFRElUTyJ9\"}" +
                "]";
        PaymentJsonConverter converter = new PaymentJsonConverter();
        List<PaymentDocumentDTO> result = converter.convertJsonToDocumentDTOList(json);
        assertEquals(1, result.size());
        PaymentDocumentDTO dto = result.get(0);
        assertEquals("F01", dto.getDocType());
        assertEquals("123", dto.getNroDocInterno());
        assertEquals("eyJpZCI6IjEyMyIsIm5hbWUiOiJUZXN0IiwidHlwZSI6IkYwMSIsInRvdGFsQVBhZ2FyIjoxMjMuNCwibWVkaW9QYWdvIjoiQ1JFRElUTyJ9", dto.getContentBase64());
    }
}
