package cl.felipe.processor.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import cl.felipe.processor.dto.PaymentDocumentDTO;

@Service
public class PaymentJsonConverter {
    private final ObjectMapper mapper = new ObjectMapper();

    public List<PaymentDocumentDTO> convertJsonToDocumentDTOList(String json) throws Exception {
        // Try parsing as array first
        try {
            return mapper.readValue(json, new TypeReference<List<PaymentDocumentDTO>>() {});
        } catch (Exception e) {
            // Try parsing as object and wrap in list
            try {
                PaymentDocumentDTO single = mapper.readValue(json, PaymentDocumentDTO.class);
                return java.util.Collections.singletonList(single);
            } catch (Exception ex) {
                // Try parsing as { "documents": [...] }
                try {
                    java.util.Map<?,?> wrapper = mapper.readValue(json, java.util.Map.class);
                    Object docs = wrapper.get("documents");
                    String docsJson = mapper.writeValueAsString(docs);
                    return mapper.readValue(docsJson, new TypeReference<List<PaymentDocumentDTO>>() {});
                } catch (Exception ignored) {
                    throw e; // Throw original exception
                }
            }
        }
    }
}
