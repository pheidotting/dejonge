package nl.dias.dias_web.domein;

import org.joda.time.LocalDateTime;

public class LogObject {
	private String level;
	private String message;
	private LocalDateTime timestamp;
	private String url;

	public LogObject(String level, String message, String timestamp, String url) {
		super();
		this.level = level;
		this.message = message;
		this.timestamp = converteerTimestampNaarLocalDate(timestamp);
		this.url = url;
	}

	public String getLevel() {
		return level;
	}

	public String getMessage() {
		return message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getUrl() {
		return url;
	}

	public String toString() {

		String seperator = " - ";

		return level + seperator + message + seperator + timestamp + seperator + url;
	}

	private LocalDateTime converteerTimestampNaarLocalDate(String timestamp) {
		return new LocalDateTime(Long.parseLong(timestamp));
	}

}
