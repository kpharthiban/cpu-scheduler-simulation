import java.util.*;

public class FCFSScheduler extends Scheduler {

    private List<String[]> ganttChart;

    public FCFSScheduler(List<Process> processes, List<PProcess> p_processes, List<RRProcess> rr_processes) {
        super(processes, p_processes, rr_processes);
        this.ganttChart = new ArrayList<>();
    }

    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        int currentTime = 0;

        for (Process process : processes) {
            int start = Math.max(currentTime, process.getArrivalTime());
            int end = start + process.getBurstTime();
            String process_start = String.valueOf(start);
            String process_end = String.valueOf(end);
            ganttChart.add(new String[]{process_start, process_end, process.getId()});
            currentTime = end;
        }
    }

    @Override
    public List<String[]> getGanttChart() {
        return ganttChart;
    }
}
