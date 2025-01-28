import java.util.*;

public class SRTScheduler extends Scheduler {

    private List<String[]> ganttChart;
    private Map<String, Integer> remainingBurstTime;

    public SRTScheduler(List<Process> processes) {
        super(processes);
        this.ganttChart = new ArrayList<>();
        this.remainingBurstTime = new HashMap<>();
        initializeRemainingBurstTime();
    }
    
    private void initializeRemainingBurstTime() {
        for (Process process : processes) {
            remainingBurstTime.put(process.getId(), process.getBurstTime());
        }
    }

    public int getRemainingBurstTime(String processId) {
        return remainingBurstTime.getOrDefault(processId, 0);
    }

    public void updateRemainingBurstTime(String processId, int timeExecuted) {
        remainingBurstTime.put(processId, remainingBurstTime.get(processId) - timeExecuted);
    }

    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime)); // Sort by arrival time

        PriorityQueue<Process> readyQueue = new PriorityQueue<>(
            (p1, p2) -> {
                if (p1.getRemainingTime() != p2.getRemainingTime()) {
                    return Integer.compare(p1.getRemainingTime(), p2.getRemainingTime()); // Sort by remaining time
                }
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime()); // FCFS if tie
            }
        );

        int currentTime = 0;
        int completedProcesses = 0;
        int n = processes.size();
        Process currentProcess = null;
        Process lastExecutedProcess = null; // Track the last executed process

        while (completedProcesses < n) {
            // Add new processes that have arrived to the ready queue
            for (Process process : processes) {
                if (process.getArrivalTime() == currentTime) {
                    readyQueue.add(process);
                }
            }

            // If a new process with a shorter burst time arrives, preempt the current process
            if (currentProcess != null && !readyQueue.isEmpty() && readyQueue.peek().getRemainingTime() < currentProcess.getRemainingTime()) {
                readyQueue.add(currentProcess); // Put the current process back into the queue
                currentProcess = readyQueue.poll(); // Switch to the new process
            }

            // Pick the process with the shortest remaining time
            if (currentProcess == null && !readyQueue.isEmpty()) {
                currentProcess = readyQueue.poll();
            }

            if (currentProcess != null) {
                // ✅ FIX: Combine Gantt Chart blocks
                if (lastExecutedProcess != null && lastExecutedProcess.getId().equals(currentProcess.getId())) {
                    // If the same process continues, update the end time of the last Gantt chart entry
                    ganttChart.get(ganttChart.size() - 1)[1] = String.valueOf(currentTime + 1);
                } else {
                    // Otherwise, create a new Gantt chart entry
                    ganttChart.add(new String[]{String.valueOf(currentTime), String.valueOf(currentTime + 1), currentProcess.getId()});
                }
                
                lastExecutedProcess = currentProcess; // Update last executed process

                // Execute process for 1 unit
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);
                currentTime++;

                // If the process finishes, record its completion time
                if (currentProcess.getRemainingTime() == 0) {
                    currentProcess.setCompletionTime(currentTime);
                    completedProcesses++;
                    currentProcess = null; // Reset current process
                }
            } else {
                currentTime++; // No process is ready, increment time
            }
        }

        // Compute turnaround time & waiting time
        for (Process p : processes) {
            p.setTurnaroundTime(p.getCompletionTime() - p.getArrivalTime());
            p.setWaitingTime(p.getTurnaroundTime() - p.getBurstTime());
        }
    }

    @Override
    public List<String[]> getGanttChart() {
        return ganttChart;
    }
}