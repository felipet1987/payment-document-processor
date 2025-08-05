package cl.felipe.processor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import java.util.List;
import cl.felipe.processor.dto.PaymentDocumentDTO;
import cl.felipe.processor.dto.PaymentRecordDTO;

@Service
public class PaymentDocumentMapper {
    private static final Logger logger = LoggerFactory.getLogger(PaymentDocumentMapper.class);
    public List<PaymentRecordDTO> mapDocumentDTOsToPaymentDocuments(List<PaymentDocumentDTO> documentDTOList) throws JsonProcessingException {
        logger.info("[DocumentMapper] Mapping {} DocumentDTO(s) to PaymentDocument(s)", documentDTOList.size());
        return documentDTOList.stream()
                .map(document -> PaymentDocumentDecoder.decodePaymentDocumentFromBase64Json(document.getContentBase64()))
                .toList();
    }
}
