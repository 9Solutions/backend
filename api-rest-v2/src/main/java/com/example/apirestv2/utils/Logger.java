package com.example.apirestv2.utils;

public class Logger {
    private static Logger instance;
    private PilhaLogger pilhaLogger;

    private Logger() {
        this.pilhaLogger = new PilhaLogger();
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String requestURL, String ip, String httpMethod, String response, String exception) {
        String log = String.format("[%s] Endpoint: %s Método HTTP: %s Ip: %s Status: %s Exception: %s",
                java.time.LocalDateTime.now(), requestURL, httpMethod, ip, response, exception);
        this.pilhaLogger.push(log);
    }

    public void printLog() {
        this.pilhaLogger.print();
    }
}
