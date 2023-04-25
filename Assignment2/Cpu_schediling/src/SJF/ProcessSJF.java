package SJF;
public class ProcessSJF {
    public String name;
    public int arrivalTime;
    public int burstTime;
    public int timeLeft;
    public int waitingTime;
    public int finishTime;

    public ProcessSJF(){}
    public ProcessSJF(String name, int arriveTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arriveTime;
        this.burstTime = burstTime;
        this.timeLeft = burstTime;
        waitingTime = 0;
    }
}