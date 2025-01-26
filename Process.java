public class Process {
    private String id;
    private int burstTime;
    private int arrivalTime;
    private int priority;
    private int quantumTime;
    private int remainingTime; // New field
    private int completionTime; // To track when the process finishes

    public Process(String id, int burstTime, int arrivalTime, int priority, int quantumTime) {
        this.id = id;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
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
        this.completionTime = completionTime;
    }
}
