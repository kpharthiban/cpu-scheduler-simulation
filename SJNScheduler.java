import java.util.*;

public class SJNScheduler extends Scheduler {

    private List<String[]> ganttChart;

    public SJNScheduler(List<Process> processes, List<PProcess> p_processes, List<RRProcess> rr_processes) {
        super(processes, p_processes, rr_processes);
        this.ganttChart = new ArrayList<>();
    }

    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        int currentTime = 0;

        for (Process process : processes) {
            // AMI
            // Add your logic here (refer to FCFSScheduler.java)
        }
    }

    @Override
    public List<String[]> getGanttChart() {
        return ganttChart;
    }
}
