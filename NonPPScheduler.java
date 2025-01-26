import java.util.*;

public class NonPPScheduler extends Scheduler {

    private List<String[]> ganttChart;

    public NonPPScheduler(List<Process> processes, List<PProcess> p_processes, List<RRProcess> rr_processes) {
        super(processes, p_processes, rr_processes);
        this.ganttChart = new ArrayList<>();
    }

    @Override
    public void schedule() {
        List<PProcess> remainingProcesses = new ArrayList<>(p_processes);
        int currentTime = 0;
        
        while (!remainingProcesses.isEmpty()) {
            // Find available processes at current time
            List<PProcess> availableProcesses = new ArrayList<>();
            for (PProcess p : remainingProcesses) {
                if (p.getArrivalTime() <= currentTime) {
                    availableProcesses.add(p);
                }
            }
            
            if (availableProcesses.isEmpty()) {
                // No process available, move to next arrival time
                PProcess nextProcess = remainingProcesses.stream()
                    .min(Comparator.comparingInt(PProcess::getArrivalTime))
                    .get();
                currentTime = nextProcess.getArrivalTime();
                continue;
            }
            
            // Get highest priority process (lowest priority number)
            PProcess selectedProcess = availableProcesses.stream()
                .min(Comparator.comparingInt(PProcess::getPriority))
                .get();
            
            int start = currentTime;
            int end = start + selectedProcess.getBurstTime();
            String process_start = String.valueOf(start);
            String process_end = String.valueOf(end);
            ganttChart.add(new String[]{process_start, process_end, selectedProcess.getId()});
            
            currentTime = end;
            remainingProcesses.remove(selectedProcess);
        }
    }

    @Override
    public List<String[]> getGanttChart() {
        return ganttChart;
    }
}
