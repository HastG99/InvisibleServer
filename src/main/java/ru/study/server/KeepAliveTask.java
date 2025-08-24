package ru.study.server;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.study.objects.Session;

@AllArgsConstructor
public class KeepAliveTask implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(KeepAliveTask.class);

    private final Server server;

    @Override
    public void run() {
        while (true) {
            server.getSessions().forEach(Session::sendKeepAlive);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
        }
    }
}
