package cl.felipe.processor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentDocumentDTO {
    @JsonProperty("NroDocInterno")
    private String nroDocInterno;

    @JsonProperty("DocType")
    private String docType;

    @JsonProperty("ContentBase64")
    private String contentBase64;
}
