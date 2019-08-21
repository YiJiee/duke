package main.task;

public class Event extends Task {
    String date;

    public Event(String description, String date) {
        super(description);
        this.date = date;
    }

    @Override
    public String toString() {
        String displayDate = this.date.substring(0, 2) + ": " + this.date.substring(3);
        return "[E][" + super.getStatusIcon() + "] "
                + super.description + " (" + displayDate + ")";
    }
}