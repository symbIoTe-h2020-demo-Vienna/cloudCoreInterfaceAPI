package eu.h2020.symbiote.messaging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by mateuszl on 22.09.2016.
 */

public class RegistrationPublisher {

    private static String PLATFORM_REGISTER_REQUEST_RECEIVED = "PlatformReceived";
    private static String SENSOR_REGISTER_REQUEST_RECEIVED = "ResourceReceived";

    private static Log log = LogFactory.getLog(RegistrationPublisher.class);

    private static RegistrationPublisher singleton;

    static {
        singleton = new RegistrationPublisher();
    }

    private RegistrationPublisher() {
    }

    public static RegistrationPublisher getInstance() {
        return singleton;
    }

    public void sendPlatformReceivedMessage(String platform) {
        try {
            RabbitMessager.sendMessage(PLATFORM_REGISTER_REQUEST_RECEIVED, platform);
            log.info("Platform:\n" + platform + "\nregistration request message send successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendSensorReceivedMessage(String sensor) {
        try {
            RabbitMessager.sendMessage(SENSOR_REGISTER_REQUEST_RECEIVED, sensor);
            log.info("Sensor:\n" + sensor + "\nregistration request message send successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
