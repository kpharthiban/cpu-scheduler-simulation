import java.util.*;

public class NonPPScheduler extends Scheduler {

    private List<String[]> ganttChart;

    public NonPPScheduler(List<Process> processes, List<PProcess> p_processes, List<RRProcess> rr_processes) {
        super(processes, p_processes, rr_processes);
        this.ganttChart = new ArrayList<>();
    }

    @Override
    public void schedule() {
        p_processes.sort(Comparator.comparingInt(PProcess::getArrivalTime));
        int currentTime = 0;

        for (PProcess process : p_processes) {
            // DERREL
            // Add your logic here (refer to FCFSScheduler.java) -> take note of the variable change
        }
    }

    @Override
    public List<String[]> getGanttChart() {
        return ganttChart;
    }
}
