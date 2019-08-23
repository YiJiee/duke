package main.task;

public class Deadline extends Task {
    String date;

    public Deadline(String description, String date) {
        super(description);
        this.date = date;
    }

    @Override
    public String toString() {
        String displayDate = this.date.substring(0, 2) + ": " + this.date.substring(3);
        return "[D][" + super.getStatusIcon() + "] "
                + super.description + " (" + displayDate + ")";
    }
}
