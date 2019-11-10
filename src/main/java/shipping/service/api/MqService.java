package shipping.service.api;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface MqService {

    void sendMsg(String msg) throws IOException, TimeoutException;

}
