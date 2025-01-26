public class RRProcess {
    private String id;
    private int arrivalTime;
    private int burstTime;
    private int quantumTime;

    public RRProcess(String id, int arrivalTime, int burstTime, int quantumTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.quantumTime = quantumTime;
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

    public int getQuantumTime() { 
        return quantumTime; 
    }

    // // Add this method to update the burst time
    // public void setBurstTime(int burstTime) {
    //     this.burstTime = burstTime;
    // }
}