package command;
import task.*;
import main.*;

public class DeleteCommand implements Command {

    int taskNumber;

    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws InsufficientTaskArgumentException, InvalidTaskException{
        if (tasks.size() < taskNumber) {
            System.out.print("Error! Task cannot be found~!");
        } else {
            Task removed = tasks.removeTask(taskNumber - 1);
            storage.updateTasks(tasks);
            ui.nextLine("    ____________________________________________________________\n" +
                    "     Noted. I've removed this task: \n" +
                            "       " + removed.toString() + "\n" +
                            "     Now you have " + tasks.size() + " tasks in the list.\n" +
                            "    ____________________________________________________________");
        }
    }

    @Override
    public String toString() {
        return "Command: delete, Argument: " + taskNumber;
    }
}
