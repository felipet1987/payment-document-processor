package cl.felipe.processor.view;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.Div;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import cl.felipe.processor.service.PaymentProcessingService;

@Route("") // Main upload route
public class PaymentUploadView extends VerticalLayout {
    private final PaymentProcessingService paymentProcessingService;

    private final Div reportDiv = new Div();

    @Autowired
    public PaymentUploadView(final PaymentProcessingService paymentProcessingService) {
        this.paymentProcessingService = paymentProcessingService;
        setSpacing(true);
        setPadding(true);

        final MemoryBuffer buffer = new MemoryBuffer();
        final Upload upload = createUpload(buffer);
        // Removed jsonArea

        reportDiv.setWidthFull();
        reportDiv.getStyle().set("margin-top", "1em");
        reportDiv.getStyle().set("border", "1px solid #ccc");
        reportDiv.getStyle().set("padding", "1em");
        reportDiv.getStyle().set("background", "#fafafa");
        reportDiv.setVisible(false);

        upload.addSucceededListener(event -> handleUploadSuccess(buffer));

        add(upload, reportDiv);
    }

    private Upload createUpload(final MemoryBuffer buffer) {
        final Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("application/json", ".json");
        upload.setMaxFiles(1);
        // setDropAllowed(true) is true by default
        return upload;
    }

    // Removed createJsonArea

    private void handleUploadSuccess(MemoryBuffer buffer) {
        InputStream inputStream = buffer.getInputStream();
        String json = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A").next();
        String htmlReport = paymentProcessingService.execute(json);
        reportDiv.setVisible(true);
        reportDiv.getElement().setProperty("innerHTML", htmlReport);
        Notification.show("Reporte generado correctamente.");
    }
}
