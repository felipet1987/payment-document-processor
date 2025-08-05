package cl.felipe.processor.service;

import cl.felipe.processor.util.PaymentDocumentMapper;
import cl.felipe.processor.util.PaymentJsonConverter;
import cl.felipe.processor.util.PaymentReportHtmlGenerator;
import cl.felipe.processor.util.PaymentXmlWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import cl.felipe.processor.dto.PaymentDocumentDTO;
import cl.felipe.processor.dto.PaymentRecordDTO;

@Service
public class PaymentProcessingService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentProcessingService.class);

    private final PaymentJsonConverter paymentJsonConverter;
    private final PaymentDocumentMapper paymentDocumentMapper;
    private final PaymentXmlWriter paymentXmlWriter;

    @Autowired
    public PaymentProcessingService(final PaymentJsonConverter paymentJsonConverter, final PaymentDocumentMapper paymentDocumentMapper, final PaymentXmlWriter paymentXmlWriter) {
        this.paymentJsonConverter = paymentJsonConverter;
        this.paymentDocumentMapper = paymentDocumentMapper;
        this.paymentXmlWriter = paymentXmlWriter;
    }

    /**
     * Processes the input JSON, generates payment documents, writes XML, and returns an HTML report.
     * @param json the input JSON string
     * @return HTML report as string
     */
    public String execute(final String json) {
        logger.info("[PaymentProcessingService] Starting JSON processing");
        final long initTime = System.currentTimeMillis();
        final List<PaymentRecordDTO> paymentRecordDTOS;
        try {
            final List<PaymentDocumentDTO> documentDTOList = paymentJsonConverter.convertJsonToDocumentDTOList(json);
            logger.info("[PaymentProcessingService] Parsed {} DocumentDTO(s)", documentDTOList.size());
            paymentRecordDTOS = paymentDocumentMapper.mapDocumentDTOsToPaymentDocuments(documentDTOList);
            logger.info("[PaymentProcessingService] Mapped to {} PaymentDocument(s)", paymentRecordDTOS.size());
            paymentXmlWriter.writePaymentDocumentsToXml(paymentRecordDTOS);
            logger.info("[PaymentProcessingService] XML writing completed");
        } catch (Exception e) {
            logger.error("[PaymentProcessingService] Error processing JSON: {}", e.getMessage(), e);
            throw new RuntimeException("Error processing JSON: " + e.getMessage(), e);
        }
        final long endTime = System.currentTimeMillis();
        logger.info("[PaymentProcessingService] Finished processing. Duration: {} ms", (endTime - initTime));
        return buildHtmlReport(paymentRecordDTOS, initTime, endTime);
    }

    private String buildHtmlReport(final List<PaymentRecordDTO> paymentRecordDTOS, final long initTime, final long endTime) {
        final String htmlReport = PaymentReportHtmlGenerator.generate(paymentRecordDTOS);
        return "<html><body>" +
                "<h3>Reporte de procesamiento</h3>" +
                htmlReport +
                "<br>Init time: " + initTime +
                "<br>End time: " + endTime +
                "<br>Duration (ms): " + (endTime - initTime) +
                "</body></html>";
    }
}
