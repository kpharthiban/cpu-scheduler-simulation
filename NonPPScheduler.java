import java.util.*;

public class NonPPScheduler extends Scheduler {

    private List<String[]> ganttChart;

    public NonPPScheduler(List<Process> processes) {
        super(processes);
        this.ganttChart = new ArrayList<>();
    }

    @Override
    public void schedule() {
        List<Process> remainingProcesses = new ArrayList<>(processes);
        int currentTime = 0;
        
        while (!remainingProcesses.isEmpty()) {
            // Find available processes at current time
            List<Process> availableProcesses = new ArrayList<>();
            for (Process p : remainingProcesses) {
                if (p.getArrivalTime() <= currentTime) {
                    availableProcesses.add(p);
                }
            }
            
            if (availableProcesses.isEmpty()) {
                // No process available, move to next arrival time
                Process nextProcess = remainingProcesses.stream()
                    .min(Comparator.comparingInt(Process::getArrivalTime))
                    .get();
                currentTime = nextProcess.getArrivalTime();
                continue;
            }
            
            // Get highest priority process (lowest priority number)
            Process selectedProcess = availableProcesses.stream()
                .min(Comparator.comparingInt(Process::getPriority))
                .get();
            
            int start = currentTime;
            int end = start + selectedProcess.getBurstTime();
            selectedProcess.setCompletionTime(end); // Set completion time
            System.out.println("Setting completion time for process " + selectedProcess.getId() + " to " + end);
            
            ganttChart.add(new String[]{String.valueOf(start), String.valueOf(end), selectedProcess.getId()});
            
            currentTime = end;
            remainingProcesses.remove(selectedProcess);
        }
    }

    @Override
    public List<String[]> getGanttChart() {
        return ganttChart;
    }
}