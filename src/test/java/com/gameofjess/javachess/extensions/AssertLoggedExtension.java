package org.example.javachess.extensions;

import static org.awaitility.Awaitility.await;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.test.appender.ListAppender;
import org.awaitility.core.ConditionTimeoutException;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.opentest4j.AssertionFailedError;

public class AssertLoggedExtension
        implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private final ListAppender listAppender = new ListAppender("listAppender");

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) {
        Logger logger = (Logger) LogManager.getRootLogger();
        logger.removeAppender(listAppender);
        listAppender.clear();
    }

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) {
        Logger logger = (Logger) LogManager.getRootLogger();
        logger.addAppender(listAppender);
    }

    /**
     * This method can be used to test if a message has been logged.
     * 
     * @param message Message, that should have been logged.
     * @param level Logging level (e.g. Level.WARN, Level.INFO, ...)
     */
    public void assertLogged(String message, Level level) {
        try {
            await().atMost(5, TimeUnit.SECONDS)
                    .until(() -> listAppender.getEvents().stream().filter(e -> e.getLevel().equals(level)).anyMatch(e -> e.getMessage().getFormattedMessage().equals(message)));
        } catch (ConditionTimeoutException e) {
            throw new AssertionFailedError("Log message '" + message + "' with level '" + level + "' was expected, but not logged.");
        }
    }


}
