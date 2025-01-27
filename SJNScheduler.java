import java.util.*;

public class SJNScheduler extends Scheduler {

    private List<String[]> ganttChart;

    public SJNScheduler(List<Process> processes) {
        super(processes);
        this.ganttChart = new ArrayList<>();
    }

    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        int currentTime = 0;

        while (!processes.isEmpty()) {
            // Find processes that have arrived by the current time
            List<Process> readyQueue = new ArrayList<>();
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime) {
                    readyQueue.add(process);
                }
            }

            if (!readyQueue.isEmpty()) {
                // Find the process with the shortest burst time
                Process shortestProcess = readyQueue.stream()
                    .min(Comparator.comparingInt(Process::getBurstTime))
                    .orElseThrow();

                int start = currentTime;
                int end = start + shortestProcess.getBurstTime();
                shortestProcess.setCompletionTime(end); // Set completion time
                ganttChart.add(new String[]{String.valueOf(start), String.valueOf(end), shortestProcess.getId()});

                currentTime = end;
                processes.remove(shortestProcess);
            } else {
                // If no process is ready, increment the current time
                currentTime++;
            }
        }
    }

    @Override
    public List<String[]> getGanttChart() {
        return ganttChart;
    }
}