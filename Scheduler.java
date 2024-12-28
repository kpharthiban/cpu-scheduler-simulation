import java.util.*;

public abstract class Scheduler {
    protected List<Process> processes;
    protected List<PProcess> p_processes;
    protected List<RRProcess> rr_processes;

    public Scheduler(List<Process> processes, List<PProcess> p_processes, List<RRProcess> rr_processes) {
        this.processes = processes;
        this.p_processes = p_processes;
        this.rr_processes = rr_processes;
    }

    public abstract void schedule();

    public abstract List<String[]> getGanttChart(); // Start, End, Process ID
}
