package cl.felipe.processor.mapper;

import cl.felipe.processor.util.PaymentDocumentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import cl.felipe.processor.dto.PaymentDocumentDTO;
import cl.felipe.processor.dto.PaymentRecordDTO;

@SpringBootTest
public class PaymentDocumentMapperTest {
    @Autowired
    private PaymentDocumentMapper documentMapper;

    @Test
    void testParseAndMapDocuments() throws Exception {
        List<PaymentDocumentDTO> dtos = new ArrayList<>();
        PaymentDocumentDTO dto1 = new PaymentDocumentDTO();
        dto1.setDocType("F01");
        dto1.setNroDocInterno("123");
        dto1.setContentBase64("eyJpZCI6IjEyMyIsIm5hbWUiOiJUZXN0MSIsInR5cGUiOiJGMDEiLCJ0b3RhbEFQYWdhciI6MTAwLjAsIm1lZGlvUGFnbyI6IkNSRURJVE8ifQ==");
        PaymentDocumentDTO dto2 = new PaymentDocumentDTO();
        dto2.setDocType("F02");
        dto2.setNroDocInterno("456");
        dto2.setContentBase64("eyJpZCI6IjQ1NiIsIm5hbWUiOiJUZXN0MiIsInR5cGUiOiJGMDEiLCJ0b3RhbEFQYWdhciI6MjAwLjAsIm1lZGlvUGFnbyI6IkRFRklOSVRJVk8ifQ==");
        dtos.add(dto1);
        dtos.add(dto2);
        List<PaymentRecordDTO> result = documentMapper.mapDocumentDTOsToPaymentDocuments(dtos);
        assertEquals(2, result.size());
        PaymentRecordDTO doc = result.get(0);
        assertEquals("123", doc.id());
        assertEquals("Test1", doc.name());
        assertEquals("F01", doc.type());
        assertEquals(100.0, doc.totalAPagar());
        assertEquals("CREDITO", doc.medioPago());
    }
}
