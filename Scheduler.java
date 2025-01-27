import java.util.*;

public abstract class Scheduler {
    protected List<Process> processes;

    public Scheduler(List<Process> processes) {
        this.processes = processes;
    }

    public abstract void schedule();

    public abstract List<String[]> getGanttChart(); // Start, End, Process ID
}
