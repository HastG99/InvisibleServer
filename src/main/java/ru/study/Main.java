package ru.study;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.study.server.Server;

public class Main {

    public static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Server server = new Server(args.length > 0 ? Integer.parseInt(args[0]) : 1337);

        new Thread(server).start();

        LOGGER.info("Server started at 0.0.0.0:{}",  server.getPort());
    }
}