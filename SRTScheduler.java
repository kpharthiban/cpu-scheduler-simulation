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
        processes.sort(Comparator.comparingInt(Process::getArrivalTime)); // sort process by arrival time
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(
            (p1, p2) -> {
                int burstTimeComparison = Integer.compare(p1.getRemainingTime(), p2.getRemainingTime()); //compare shortest remaining time
                if (burstTimeComparison == 0) {
                    return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime()); // FCFS if burst times are equal
                }
                return burstTimeComparison;
            }
        );

        int currentTime = 0;
        int completedProcesses = 0;
        int n = processes.size();

        while (completedProcesses < n) {
            // Add processes to the ready queue if they have arrived
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime && process.getRemainingTime() > 0) {
                    readyQueue.add(process);
                }
            }

            if (readyQueue.isEmpty()) {
                currentTime++; // No process to execute, increment time
                continue;
            }

            Process currentProcess = readyQueue.poll(); // process with the shortest remaining time

            int timeToExecute = 1; 
            //update gantt chart
            ganttChart.add(new String[] {String.valueOf(currentTime), String.valueOf(currentTime + timeToExecute), currentProcess.getId()});
            currentTime += timeToExecute;

            currentProcess.setRemainingTime(currentProcess.getRemainingTime() - timeToExecute);//decrease remaining time for current process

            // Check if the process is completed
            if (currentProcess.getRemainingTime() == 0) {
                currentProcess.setCompletionTime(currentTime);
                completedProcesses++;
            }

            readyQueue.clear(); // Clear the ready queue to re-evaluate at the next time unit
        }
    }

    @Override
    public List<String[]> getGanttChart() {
        return ganttChart;
    }
}