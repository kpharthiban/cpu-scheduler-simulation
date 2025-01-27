import java.util.*;

public class RRScheduler extends Scheduler {

    private List<String[]> ganttChart;
    private Map<String, Integer> remainingBurstTimes; // Track remaining burst times

    public RRScheduler(List<Process> processes) {
        super(processes);
        this.ganttChart = new ArrayList<>();
        this.remainingBurstTimes = new HashMap<>();
    }

    @Override
    public void schedule() {
        // Sort processes by arrival time
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        // Initialize remaining burst times
        for (Process process : processes) {
            remainingBurstTimes.put(process.getId(), process.getBurstTime());
        }

        // Create a queue to hold the processes
        Queue<Process> queue = new LinkedList<>();
        int currentTime = 0;
        int index = 0; // Index to track processes not yet added to the queue

        // Add processes that have arrived by the current time to the queue
        while (index < processes.size() || !queue.isEmpty()) {
            // Add all processes that have arrived by the current time to the queue
            while (index < processes.size() && processes.get(index).getArrivalTime() <= currentTime) {
                queue.add(processes.get(index));
                index++;
            }

            if (!queue.isEmpty()) {
                // Get the next process from the queue
                Process currentProcess = queue.poll();

                // Get the remaining burst time for the current process
                int remainingBurstTime = remainingBurstTimes.get(currentProcess.getId());

                // Determine the execution time (minimum of quantum and remaining burst time)
                int executionTime = Math.min(currentProcess.getQuantumTime(), remainingBurstTime);

                // Record the start and end time of the process execution
                int start = currentTime;
                int end = start + executionTime;
                ganttChart.add(new String[]{String.valueOf(start), String.valueOf(end), currentProcess.getId()});

                // Update the remaining burst time
                remainingBurstTimes.put(currentProcess.getId(), remainingBurstTime - executionTime);

                // Update the current time
                currentTime = end;

                // Add any new processes that have arrived by the current time
                while (index < processes.size() && processes.get(index).getArrivalTime() <= currentTime) {
                    queue.add(processes.get(index));
                    index++;
                }

                // If the process is not finished, add it back to the queue
                if (remainingBurstTimes.get(currentProcess.getId()) > 0) {
                    queue.add(currentProcess);
                } else {
                    // Process is finished, set completion time
                    currentProcess.setCompletionTime(end);
                }
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