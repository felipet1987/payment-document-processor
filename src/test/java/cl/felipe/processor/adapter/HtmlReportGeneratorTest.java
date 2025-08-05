package cl.felipe.processor.generator;

import cl.felipe.processor.util.PaymentReportHtmlGenerator;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import cl.felipe.processor.dto.PaymentRecordDTO;

class PaymentReportHtmlGeneratorTest {
    @Test
    void generate_withMultipleMedioPago_outputsCorrectHtml() {
        List<PaymentRecordDTO> docs = List.of(
            new PaymentRecordDTO("1", "ClienteA", "F01", 100.0, "CREDITO"),
            new PaymentRecordDTO("2", "ClienteB", "F02", 200.0, "DEBITO"),
            new PaymentRecordDTO("3", "ClienteC", "F01", 150.0, "CREDITO")
        );
        String html = PaymentReportHtmlGenerator.generate(docs);
        assertTrue(html.contains("CREDITO"));
        assertTrue(html.contains("DEBITO"));
        assertTrue(html.contains("250") || html.contains("250.00"), "Should contain total for CREDITO. HTML: " + html);
        assertTrue(html.contains("200") || html.contains("200.00"), "Should contain total for DEBITO. HTML: " + html);
        assertTrue(html.contains(">3<"), "Should contain total docs. HTML: " + html);
        assertTrue(html.contains("450") || html.contains("450.00"), "Should contain total a pagar. HTML: " + html);
    }

    @Test
    void generate_withEmptyList_outputsTableWithTotalsZero() {
        String html = PaymentReportHtmlGenerator.generate(List.of());
        assertTrue(html.contains("Cantidad Docs"));
        assertTrue(html.contains(">0<"));
        assertTrue(html.contains("0.00"));
    }
}
