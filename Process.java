public class Process {
    private String id;
    private int arrivalTime;
    private int burstTime;
    private int remainingTime; // New field
    private int completionTime; // To track when the process finishes

    public Process(String id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime; // Initially, remaining time equals burst time
    }

    public String getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
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
