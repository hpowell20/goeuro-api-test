import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.DEBUG

appender("CONSOLE", ConsoleAppender) {
	encoder(PatternLayoutEncoder) {
	  pattern = "%-5level %logger{36} - %msg%n"
	}
}

root(DEBUG, ["CONSOLE"])