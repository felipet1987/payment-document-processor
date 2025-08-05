package cl.felipe.processor.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import cl.felipe.processor.dto.PaymentRecordDTO;

public class PaymentReportHtmlGenerator {
    public static String generate(List<PaymentRecordDTO> paymentRecordDTOS) {
        Map<String, Integer> countByMedioPago = new LinkedHashMap<>();
        Map<String, Double> totalByMedioPago = new LinkedHashMap<>();
        int totalDocs = 0;
        double totalAmount = 0.0;
        for (PaymentRecordDTO doc : paymentRecordDTOS) {
            countByMedioPago.put(doc.medioPago(), countByMedioPago.getOrDefault(doc.medioPago(), 0) + 1);
            totalByMedioPago.put(doc.medioPago(), totalByMedioPago.getOrDefault(doc.medioPago(), 0.0) + doc.totalAPagar());
            totalDocs++;
            totalAmount += doc.totalAPagar();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1'><tr><th>Medio Pago</th>");
        for (String medio : countByMedioPago.keySet()) {
            sb.append("<th>").append(medio).append("</th>");
        }
        sb.append("<th>TOTALES</th></tr>");
        sb.append("<tr><td>Cantidad Docs</td>");
        for (String medio : countByMedioPago.keySet()) {
            sb.append("<td>").append(countByMedioPago.get(medio)).append("</td>");
        }
        sb.append("<td>").append(totalDocs).append("</td></tr>");
        sb.append("<tr><td>Total a Pagar</td>");
        for (String medio : totalByMedioPago.keySet()) {
            sb.append("<td>").append(String.format("%.2f", totalByMedioPago.get(medio))).append("</td>");
        }
        sb.append("<td>").append(String.format("%.2f", totalAmount)).append("</td></tr>");
        sb.append("</table>");

        // Add per-document details
        sb.append("<h4>Detalles de Documentos Procesados</h4>");
        sb.append("<table border='1'><tr><th>ID</th><th>Nombre</th><th>Tipo</th><th>Total a Pagar</th><th>Medio Pago</th></tr>");
        for (PaymentRecordDTO doc : paymentRecordDTOS) {
            sb.append("<tr>")
              .append("<td>").append(doc.id()).append("</td>")
              .append("<td>").append(doc.name()).append("</td>")
              .append("<td>").append(doc.type()).append("</td>")
              .append("<td>").append(String.format("%.2f", doc.totalAPagar())).append("</td>")
              .append("<td>").append(doc.medioPago()).append("</td>")
              .append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }
}
