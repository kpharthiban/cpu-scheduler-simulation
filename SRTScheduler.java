import java.util.*;

public class SRTScheduler extends Scheduler {

    private List<String[]> ganttChart;

    public SRTScheduler(List<Process> processes, List<PProcess> p_processes, List<RRProcess> rr_processes) {
        super(processes, p_processes, rr_processes);
        this.ganttChart = new ArrayList<>();
    }

    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getRemainingTime));

        int currentTime = 0;
        int completedProcesses = 0;
        int n = processes.size();
        Process currentProcess = null;
        int prevTime = 0;

        while (completedProcesses < n) {
            // Add processes to the ready queue that have arrived by the current time
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime && process.getRemainingTime() > 0 && !readyQueue.contains(process)) {
                    readyQueue.add(process);
                }
            }

            if (currentProcess != null && currentProcess.getRemainingTime() > 0) {
                readyQueue.add(currentProcess);
            }

            if (!readyQueue.isEmpty()) {
                currentProcess = readyQueue.poll();

                if (currentProcess != null) {
                    if (prevTime < currentTime) {
                        ganttChart.add(new String[]{String.valueOf(prevTime), String.valueOf(currentTime), "Idle"});
                    }

                    currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);
                    currentTime++;

                    if (currentProcess.getRemainingTime() == 0) {
                        currentProcess.setCompletionTime(currentTime);
                        completedProcesses++;
                    }

                    ganttChart.add(new String[]{String.valueOf(prevTime), String.valueOf(currentTime), currentProcess.getId()});
                    prevTime = currentTime;
                }
            } else {
                // If no process is ready, increment the time
                ganttChart.add(new String[]{String.valueOf(currentTime), String.valueOf(currentTime + 1), "Idle"});
                currentTime++;
            }
        }
    }

    @Override
    public List<String[]> getGanttChart() {
        return ganttChart;
    }
}
