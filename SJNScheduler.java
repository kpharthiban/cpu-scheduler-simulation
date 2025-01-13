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

        for (int i = 0; i < processes.size();) { //continue loop til all process are scheduled
            // AMI
            List<Process> readyQueue = new ArrayList<>(); //list to hold processes that ready to execute-hve arrived
    
            //collect pcess tht hve arrived by the current time
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime) {
                    //add the process to the ready queue if it has arrived
                    readyQueue.add(process);
                }
            }
    
            if (!readyQueue.isEmpty()) {  //check if there any ready process
                //find the process with the shortest burst time
                Process shortestProcess = readyQueue.stream()
                    .min(Comparator.comparingInt(Process::getBurstTime))
                    .orElseThrow(); //if no process is found
    
                int start = Math.max(currentTime, shortestProcess.getArrivalTime());
                int end = start + shortestProcess.getBurstTime();
                ganttChart.add(new String[]{String.valueOf(start), String.valueOf(end), shortestProcess.getId()});
                currentTime = end;
                processes.remove(shortestProcess); //remove the scheduled process from the list
            } else {
                //if no process is ready, increment the current time
                currentTime++;
            }
        }
    }

    @Override
    public List<String[]> getGanttChart() {
        return ganttChart;
    }
}
