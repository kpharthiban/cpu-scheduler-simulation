public class Process {
    private String id;
    private int burstTime;
    private int arrivalTime;
    private int priority;
    private int quantumTime;
    private int remainingTime; // New field
    private int completionTime; // To track when the process finishes
    private int turnaroundTime;
    private int waitingTime;

    public Process(String id, int burstTime, int arrivalTime, int priority, int quantumTime) {
        this.id = id;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.quantumTime = quantumTime;
        this.remainingTime = burstTime; // Initially, remaining time equals burst time
    }

    public String getId() {
        return id;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getQuantumTime() {
        return quantumTime;
    }

    public int getRemainingTime() { 
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) { 
        this.remainingTime = remainingTime;
    }

    public int getCompletionTime() { 
        return completionTime;
    }

    public void setCompletionTime(int completionTime) { 
        System.out.println("Completion time set to " + completionTime + " for process " + id);
        this.completionTime = completionTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int getTurnaroundTime() { 
        return turnaroundTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getWaitingTime() { 
        return waitingTime;
    }

}