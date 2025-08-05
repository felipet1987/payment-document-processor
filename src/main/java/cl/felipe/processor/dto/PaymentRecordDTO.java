package cl.felipe.processor.dto;

public record PaymentRecordDTO(
    String id,
    String name,
    String type,
    double totalAPagar,
    String medioPago
) {}
