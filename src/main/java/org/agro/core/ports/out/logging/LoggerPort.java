package org.agro.core.ports.out.logging;

public interface LoggerPort {

    void logInfo(String message);

    void logError(String message);

    void logWarn(String message);

    void logError(String message, Exception e);

    void logInfo(String message, Object... params);

    void logError(String message, Object... params);

    void logWarn(String message, Object... params);

}
