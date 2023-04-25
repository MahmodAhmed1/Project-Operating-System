package SJF;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


public class SJF {
    private ArrayList<ProcessSJF> processArrayList = new ArrayList<ProcessSJF>();
    private int contextSwitching = 0;
    int waitingTime = 0;

    public SJF(){}
    public void SJFSwitching(int ctxSwitching){
        contextSwitching = ctxSwitching;
    }
    public void addProcesses(ProcessSJF p) {
        processArrayList.add(p);
    }
    public void shortestJobFirst() {
        int time = 0;

        while(true) {
            int elapsedTime;
            ProcessSJF runningProcess;
            int nextArrivalTime = getNextArrivalTime(time);

            final int constTime = time;
            ArrayList<ProcessSJF> activeProccess = processArrayList.stream()
                    .filter(p -> p.arrivalTime <= constTime && p.timeLeft > 0 )
                    .sorted((p1, p2) -> p1.burstTime - p2.burstTime)
                    .collect(Collectors.toCollection(ArrayList::new));

            if (activeProccess.size() == 0 && nextArrivalTime == -1)
                break;

            if (activeProccess.size() == 0) {
                time = nextArrivalTime;
                continue;
            }

            runningProcess = activeProccess.get(0);
            if (time + runningProcess.timeLeft > nextArrivalTime && nextArrivalTime != -1) {
                elapsedTime = nextArrivalTime - time;
            } else {
                elapsedTime = runningProcess.timeLeft;
                runningProcess.finishTime = time + elapsedTime + contextSwitching;
            }

            runningProcess.timeLeft -= elapsedTime;
            time = time + elapsedTime + contextSwitching;

        }

        for (ProcessSJF p: processArrayList) {
            int estimatedFinishTime = p.arrivalTime + p.burstTime;
            p.waitingTime = p.finishTime - estimatedFinishTime;
        }

        printProccess();
    }
    void printProccess() {

        double avgWaiting = 0;
        double avgTurnAroundTime = 0;
        
        System.out.println("Name: " + " Arrival Time " + " Burst Time " + " Waiting Time " + " Turnaround Time ");
        for (ProcessSJF p: processArrayList) {
            int TurnaroundTime = p.finishTime - p.arrivalTime;
            avgWaiting += p.waitingTime;
            avgTurnAroundTime += TurnaroundTime;
            
            System.out.println(p.name + "\t" + "    " + p.arrivalTime + " \t\t" + p.burstTime  + "\t\t" + p.waitingTime + " \t\t " + TurnaroundTime );
        }

        avgWaiting /= processArrayList.size();
        avgTurnAroundTime /= processArrayList.size();
        System.out.println("average Turnround : " + avgTurnAroundTime);
        System.out.println("average Waiting : " + avgWaiting);
        
    }

    int getNextArrivalTime(int currentTime) {
        final int constTime = currentTime;
        int nextArrivalTime = -1;
        try {
            nextArrivalTime = processArrayList.stream()
                    .filter(p -> p.arrivalTime > constTime)
                    .min((p1, p2) -> p1.arrivalTime - p2.arrivalTime)
                    .get().arrivalTime;
        } catch (NoSuchElementException ignore) {}
        return nextArrivalTime;
    }
    

}