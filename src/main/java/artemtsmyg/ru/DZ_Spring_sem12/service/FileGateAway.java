package artemtsmyg.ru.DZ_Spring_sem12.service;


import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "TextInputChannel")
public interface FileGateAway {
    void writeToFile(@Header(FileHeaders.FILENAME) String fileName, String data);
}
