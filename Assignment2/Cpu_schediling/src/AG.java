import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;


public class AG {
    private ArrayList<Process> processes = new ArrayList<Process>();
    private LinkedList<Process> readyQueue = new LinkedList<Process>();
    private ArrayList<String> ExecutionOrder = new ArrayList<String>();
    private int time = 0;
    Process current ;

    double WaitingTime;
    double TurnTime;


    void AGschedule(ArrayList<Process> ALL){
        Process a;
        //make a copy of the given array in parameter
        processes = ALL;
        current = null;
        //next calculate the last arrival
        addTheNewProcess();
        //get the first on in the queue (FCFS)
        current = readyQueue.getFirst();
        readyQueue.removeLast();
        double quantum;
        //Start General Time
        System.out.println("Time :" +time);
        //till end of processes
        int size = processes.size();

        while(size>0) {
        quantum = current.getQuantum();
        double quantum25= Math.ceil(quantum/4);  //Var for Calculations
        double quantum50= Math.ceil(quantum/2);
        double quantumcalc50= quantum50;    //Static Var for conditions
        double quantumcalc25=quantum25;
        quantum50 = quantum50-quantum25;
        boolean flag =false;   //for running process to the second 25% "50% of the quantum"
        boolean isFinished =false;
            //check 25% of Quantum
            while(quantum25 >= 0){
                operate(current);
                addTheNewProcess();
                System.out.println("\nTime : "+time);
                listTable(); //print Ready Queue in Tabular format
                quantum25--;
                if(current.getBurstTime() == 0){
                    isFinished=true;
                    current.setQuantum(0);
                    current.setExitTime(time); //for Calculation of Round Robin
                    try {
                        current = readyQueue.getFirst(); //if the process is the last one in the ready "out of index error"
                        readyQueue.remove(current);
                    }
                    catch (NoSuchElementException e){
                        break;
                    }
                    break;
                }
                else if(quantum25==0 && current==checkPriority(current)){
                    flag = true;
                    break;
                }
                else if(quantum25 ==0&&current!=checkPriority(current)){
                    current.setQuantum(quantum+ Math.ceil((quantum - quantumcalc25)/2) );
                    moveQueueC(current,checkPriority(current));
                    readyQueue.remove(current);
                    flag = false;
                    break;
                }
            }

            //check 50%
            while(flag && quantum50 >= 0 && !isFinished){
                operate(current);
                addTheNewProcess();
                System.out.println("\nTime : "+time);
                listTable();
                quantum50--;
                if(current.getBurstTime() == 0){
                    isFinished=true;
                    current.setQuantum(0);
                    current.setExitTime(time);
                    try {
                        current = readyQueue.getFirst();
                        readyQueue.remove(current);
                    }
                    catch (NoSuchElementException e){
                        break;
                    }
                    break;
                }
                else if (quantum50 == 0 && current==checkMinBrust(current)){
                    flag = true;   //to complete till finishing all his quantum
                    break;
                }
                else if(quantum50 == 0 && current!=checkMinBrust(current)){
                    current.setQuantum(quantum + (quantum - quantumcalc50)); //adding the Remaining to quantum
                    moveQueueC(current,checkMinBrust(current));
                    readyQueue.remove(current);
                    flag=false;
                    break;
                }
            }
            while (flag && quantum >=0 && !isFinished){
                operate(current);
                addTheNewProcess();
                System.out.println("\nTime : "+time);
                listTable();
                quantum--;
                if(current.getBurstTime() == 0){
                    current.setQuantum(0);
                    current.setExitTime(time);
                    try {
                        current = readyQueue.getFirst();
                        readyQueue.remove(current);
                    }
                    catch (NoSuchElementException e){
                        break;
                    }
                    isFinished=true;
                    break;
                } else if (current.getBurstTime()>0 && quantum ==0) {  //First Scenario Condition
                    current.setQuantum(quantum + 2);
                    moveQueueF(current);
                    break;
                }

            }
            if(isFinished){
                size--;
            }
        }
        ListExecution();
        System.out.println(" ");
        getalltimes();
    }


    void addTheNewProcess(){
        for (int i = 0; i < processes.size(); i++) {
            if(processes.get(i).getArrivalTime() == time){
                readyQueue.add(processes.get(i));
                System.out.println(processes.get(i).getProcess_name() + " has been added.");
            }
        }
    }


    void moveQueueF(Process c){
        readyQueue.addLast(c);
        current=readyQueue.getFirst();
        readyQueue.removeFirst();
    }
    void moveQueueC(Process c,Process Cond){
        readyQueue.addLast(c);
        current=Cond;
    }


    void operate(Process running){
        System.out.println("--->proceded: " + running.getProcess_name());
        ExecutionOrder.add(running.getProcess_name());

        //time counter
        time++;
        running.setBurstTime(running.getBurstTime()-1);
            System.out.println(running.getProcess_name() + " Quantum: " + running.getQuantum());
            System.out.println(running.getProcess_name() + " Burst Time: " + running.getBurstTime());
    }

    Process checkPriority(Process running){
        Process high;
        high = running;
            for (Process item: readyQueue) {
                if(high.getPriority() > item.getPriority()){
                    high = item;
                }
            }
        return high;
    }

    Process checkMinBrust(Process running){
        Process min;
        min = running;
        for (Process item: readyQueue) {
            if(min.getBurstTime() > item.getBurstTime()){
                min = item;
            }
        }
        return min;
    }

    void listTable() {
        //prints the list objects in tabular format
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tReady Queue  ");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%5s %25s %25s %25s %20s", "Process", "Burst Time", "Arrival Time", "Priority", "Quantum");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");
//iterates over the list

        for (Process i : readyQueue) {
            if (i != null) {
                System.out.format("%5s %15s %25s %25s %25s", i.process_name, i.getBurstTime(), i.getArrivalTime(), i.getPriority(), i.getQuantum());
                System.out.println();
                System.out.println("-------------------------------------------------------------------------------------------------------------------------");
            }

        }
    }
    void ListExecution(){
        System.out.println("Execution Order :");
        for (String i : ExecutionOrder) {
            if (i != null) {
                System.out.print(i + "-->");
            }

        }
    }

    void getalltimes(){
        System.out.println("Turn Around Time for Each Process :");
        double AvgTurnRound = 0.0;
        double AvgWaitTime = 0.0;
        for(Process i :processes)
        {
            TurnTime =(i.getExitTime()-i.getArrivalTime());
            WaitingTime =(TurnTime-(i.burstTimecalc));
            System.out.println(i.getProcess_name()+" Turn round Time  :"+ TurnTime);
            System.out.println(i.getProcess_name()+" waiting Time  :"+ WaitingTime);
            AvgTurnRound+=TurnTime;
            AvgWaitTime+= WaitingTime;
        }

        System.out.println("Average Turn Around Time : ");
        System.out.println(AvgTurnRound/processes.size());
        System.out.println("Average Waiting Time : ");
        System.out.println(AvgWaitTime/processes.size());
    }

}