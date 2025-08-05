package cl.felipe.processor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import cl.felipe.processor.dto.PaymentRecordDTO;

@Service
public class PaymentXmlWriter {
    private static final Logger logger = LoggerFactory.getLogger(PaymentXmlWriter.class);
    public void writePaymentDocumentsToXml(List<PaymentRecordDTO> paymentRecordDTOS) throws Exception {
        logger.info("[PaymentXmlWriterService] Writing {} PaymentRecord(s) to XML", paymentRecordDTOS.size());
        File outputDir = new File("output");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        for (PaymentRecordDTO doc : paymentRecordDTOS) {
            String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"no\"?>\n" +
                    "<DTE version=\"1.0\">\n" +
                    "  <Documento ID=\"" + doc.id() + "\">\n" +
                    "    <Cliente>" + doc.name() + "</Cliente>\n" +
                    "    <Tipo>" + doc.type() + "</Tipo>\n" +
                    "    <TotalAPagar>" + doc.totalAPagar() + "</TotalAPagar>\n" +
                    "    <MedioPago>" + doc.medioPago() + "</MedioPago>\n" +
                    "  </Documento>\n" +
                    "</DTE>\n";
            File xmlFile = new File(outputDir, doc.id() + ".xml");
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(xmlFile), "ISO-8859-1")) {
                writer.write(xml);
            }
        }
    }
}
