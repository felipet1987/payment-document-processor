package cl.felipe.processor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentDocumentDTO {
    @JsonProperty("NroDocInterno")
    private String nroDocInterno;
    public String getNroDocInterno() { return nroDocInterno; }
    public void setNroDocInterno(String nroDocInterno) { this.nroDocInterno = nroDocInterno; }
    public String getContentBase64() { return contentBase64; }
    @JsonProperty("DocType")
    private String docType;

    @JsonProperty("ContentBase64")
    private String contentBase64;
}
