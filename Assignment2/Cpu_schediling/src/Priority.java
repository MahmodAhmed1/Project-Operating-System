import java.util.ArrayList;
import java.util.List;


public class Priority {
    List<Process> Processes=new ArrayList<>();
    List<Integer> burst=new ArrayList<>();
    public void setBurst(List<Integer> burst) {
        this.burst = burst;
    }
    public void setProcesses(List<Process> processes) {
        Processes = processes;
    }
    public List<Process> getProcesses() {
        return Processes;
    }
    private List<Integer>waiting=new ArrayList<Integer>();
    private List<Integer>turnaround=new ArrayList<Integer>();
    int currentTime=0;

    public void run(){
        int index=0;  // Tells Who is Working "Has The turn to work"
        currentTime=Processes.get(0).getArrivalTime();
        int priority=Processes.get(0).getPriority();
        for(int i=1;i<Processes.size()-1;i++){  //loop till all the processes
            if(Processes.get(i).getArrivalTime()==currentTime){
                int counter=i;
                if(counter<Processes.size()-1){
                    while(Processes.get(counter).getArrivalTime()==currentTime&&counter<Processes.size()-1) {
                        if (Processes.get(counter).getPriority() < priority) {
                            priority = Processes.get(counter).getPriority();
                            index = counter;
                        }
                        counter++;
                    }
                }else{
                    if(Processes.get(i).getPriority()<priority){
                        priority=Processes.get(i).getPriority();
                        index=i;
                    }
                }
            }

        }
        while(!Processes.isEmpty()){
            System.out.print(Processes.get(index).getProcess_name()+" Start time :"+currentTime+" ");
            while(processIsHere(index,currentTime,Processes)==false){
                currentTime++;
                this.Processes.get(index).setBurstTime(Processes.get(index).getBurstTime()-1);
            }
            System.out.println("End time :"+currentTime);
            /////////////////////////////////
            //solve starvation every 6 times
            if (currentTime%6==0){
                for(int i=0;i<Processes.size();i++){
                    if(Processes.get(i).getPriority()>1)
                        Processes.get(i).setPriority(Processes.get(i).getPriority()-1);
                }
            }
            /////////////////////////////////
            if(Processes.get(index).getBurstTime()!=0){
                for(int i=0;i<Processes.size();i++){
                    if(Processes.get(i).getPriority()<Processes.get(index).getPriority()&&
                            Processes.get(i).getArrivalTime()<=currentTime){
                        index=i;
                    }
                }
            }
            if(Processes.get(index).getBurstTime()==0){
                System.out.println(Processes.get(index).getProcess_name()+" Waiting time is : "+ CalcWaiting(Processes.get(index),this.burst,index));
                System.out.println(Processes.get(index).getProcess_name()+" Turn Round time is : "+ CalcTurn(Processes.get(index)));
                System.out.println("---------------------------------------");
                this.Processes.remove(Processes.get(index));
                this.burst.remove(burst.get(index));
                if(!Processes.isEmpty()){
                    for(int i=0;i<Processes.size();i++){
                        int minArrival=Processes.get(0).getArrivalTime();
                        int minPriority=Processes.get(0).getPriority();
                        if(Processes.get(i).getArrivalTime()<=minArrival&&Processes.get(i).getPriority()<minPriority){
                            index=i;
                        }
                        else{
                            index=0;
                        }
                    }}
            }
        }
    }

    boolean processIsHere(int index, int curr, List<Process>m) {
        if (m.get(index).getBurstTime() == 0)
            return true;
        else {
            if (index < m.size() - 1) {
                if (curr < m.get(index + 1).getArrivalTime()) {
                    return false;
                } else if (curr == m.get(index + 1).getArrivalTime()) {
                    if (m.get(index + 1).getPriority() <= m.get(index).getPriority())
                        return true;
                    else
                        return false;
                } else if (curr > m.get(index + 1).getArrivalTime()) {

                    if (m.get(index + 1).getPriority() < m.get(index).getPriority()) {
                        return true;
                    } else {
                        return false;
                    }
                }else
                    return false;

            } else {
                if (m.get(index).getBurstTime() == 0)
                    return true;
                else
                    return false;
            }
        }
    }



    public int CalcTurn(Process p){
        int turn=currentTime-p.getArrivalTime();
        turnaround.add(turn);
        return(turn);
    }
    public int CalcWaiting(Process p,List<Integer>m,int index){
        int wait=currentTime-p.getArrivalTime()-m.get(index);
        waiting.add(wait);
        return (wait);
    }
    public double AvgWaiting(){
        int sum=0;
        for(int i=0;i<waiting.size();i++){
            sum+=waiting.get(i);
        }
        return((double) sum/waiting.size());
    }
    public double AvgTurntime(){
        int sum=0;
        for(int i=0;i<turnaround.size();i++){
            sum+=turnaround.get(i);
        }
        return((double) sum/turnaround.size());
    }

    public void print() {
        System.out.println("----------------------------------------");
        System.out.println("Avg Waiting time : "+AvgWaiting());
        System.out.println("Avg Turn around time : "+AvgTurntime());
    }


}
