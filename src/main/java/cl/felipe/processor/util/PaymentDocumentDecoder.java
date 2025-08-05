package cl.felipe.processor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import cl.felipe.processor.dto.PaymentRecordDTO;

public class PaymentDocumentDecoder {
    private static final Logger logger = LoggerFactory.getLogger(PaymentDocumentDecoder.class);

    public static PaymentRecordDTO decodePaymentDocumentFromBase64Json(String contentBase64) {
        logger.info("[PaymentDocumentDecoder] Decoding PaymentRecord from Base64 JSON");
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(contentBase64);
            // Try decompressing as GZIP; fallback to plain JSON if not compressed
            String json;
            try (GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(decodedBytes));
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[256];
                int len;
                while ((len = gis.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                json = out.toString(StandardCharsets.UTF_8);
                logger.info("[PaymentDocumentDecoder] Decoded GZIP-compressed JSON");
            } catch (IOException ex) {
                // Not GZIP, treat as plain JSON
                logger.info("[PaymentDocumentDecoder] Input not GZIP, treating as plain JSON");
                json = new String(decodedBytes, StandardCharsets.UTF_8);
            }
            ObjectMapper mapper = new ObjectMapper();
            PaymentRecordDTO doc = mapper.readValue(json, PaymentRecordDTO.class);
            logger.info("[PaymentDocumentDecoder] Successfully parsed PaymentRecord");
            return doc;
        } catch (Exception e) {
            logger.error("[PaymentDocumentDecoder] Failed to parse PaymentRecord JSON: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to parse PaymentRecord JSON", e);
        }
    }
}
