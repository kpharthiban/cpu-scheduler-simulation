import java.util.*;

public class RRScheduler extends Scheduler {

    private List<String[]> ganttChart;

    public RRScheduler(List<Process> processes, List<PProcess> p_processes, List<RRProcess> rr_processes) {
        super(processes, p_processes, rr_processes);
        this.ganttChart = new ArrayList<>();
    }

    @Override
    public void schedule() {
        rr_processes.sort(Comparator.comparingInt(RRProcess::getArrivalTime));
        int currentTime = 0;

        for (PProcess process : p_processes) {
            // PHARTHIBAN
            // Add your logic here (refer to FCFSScheduler.java for example)
        }
    }

    @Override
    public List<String[]> getGanttChart() {
        return ganttChart;
    }
}
