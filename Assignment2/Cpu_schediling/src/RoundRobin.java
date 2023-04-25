import java.util.ArrayList;

public class RoundRobin {
    public void RRProcess(ArrayList<Process>list,int quantum,int context){  //Execution Order
        
        int time=list.get(0).getArrivalTime();
        int executed=0;
        ArrayList<String>seq=new ArrayList<>();   //For Printing Execution Order
        int n=list.size();

        while(executed<n){
            int indx=-1,mn=10000000;

            for (int i = 0; i < n; i++) {
                if (list.get(i).updateATime<=time && list.get(i).remaining>0) {
                    if (mn > list.get(i).updateATime) { //Set Arrival Time For Later
                        mn = list.get(i).updateATime;
                        indx = i;
                    }
                }
            }

            if(indx==-1)
            {
                time++;
                continue;
            }

            seq.add(list.get(indx).getProcess_name());  //Same as Ready Queue

            if (list.get(indx).remaining==list.get(indx).getBurstTime()){
                list.get(indx).setResponseTime(time);
            }

            time+=Math.min(quantum,list.get(indx).remaining)+context;
            list.get(indx).updateATime=time;
            list.get(indx).remaining-=Math.min(quantum,list.get(indx).remaining);

            if (list.get(indx).remaining==0){
                executed++;
                list.get(indx).compeletion=time;
                //Calculate turn and wait For Each Process
                list.get(indx).turn=list.get(indx).compeletion-list.get(indx).getArrivalTime();
                list.get(indx).wait=list.get(indx).turn-list.get(indx).getBurstTime();
            }
        }
//        Print Order Of Execution
        System.out.println("\nSequence of execution:");
        for (int i = 0; i < seq.size() ; i++) {
            System.out.print(seq.get(i) + " ");
        }
        System.out.println("\n");
    }



    public void printRR(ArrayList<Process>r, int q, int context){
        double AvgTurn=0,AvgWaiting=0;
        RRProcess(r,q,context);
        r.sort(new ProcessesCompare());    //Check Min Arrival Time
        System.out.println("Name ExecutionTime  ArrivalTime  Completion Time  Waiting Time  Turnaround Time  Response Time\n");
        for (int i = 0; i < r.size(); i++) {
            System.out.println(r.get(i).getProcess_name()+"\t"+ r.get(i).getBurstTime()+"\t\t"+r.get(i).getArrivalTime()+"\t\t"
                    +r.get(i).compeletion+"\t\t"+r.get(i).wait+"\t\t"+
                    r.get(i).turn+"\t\t"+r.get(i).getResponseTime());
            AvgTurn+=r.get(i).turn;
            AvgWaiting+=r.get(i).wait;
        }
        System.out.println("Average Turnround: "+ (AvgTurn/(double) r.size()));
        System.out.println("Average Waiting: "+ (AvgWaiting/(double) r.size()));

    }

}
