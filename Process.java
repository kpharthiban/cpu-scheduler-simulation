//import java.util.*;

public class Process {
    private String id;
    private int arrivalTime;
    private int burstTime;

    public Process(String id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

    public String getId() { return id; }
    public int getArrivalTime() { return arrivalTime; }
    public int getBurstTime() { return burstTime; }
}
