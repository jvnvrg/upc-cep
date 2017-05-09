package upc.edu.cep.events;

public class EventA {

    private String hostname;

    private long timestamp;

    private String log;

    public EventA() {
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "LogEvent{" +
                "hostname='" + hostname + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", log='" + log + '\'' +
                '}';
    }
}
