import java.util.ArrayList;
import java.util.Scanner;

import SJF.ProcessSJF;
import SJF.SJF;


public class Main {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        AG a = new AG();
        SJF s = new SJF();
        SJF s2 = new SJF();
        Priority pr = new Priority();
        RoundRobin roundRobin = new RoundRobin();


        ArrayList<Process> processes = new ArrayList<Process>();
        ArrayList<Process> processes2 = new ArrayList<Process>();
        ArrayList<Process> p = new ArrayList<>();
        ArrayList<Process> prs = new ArrayList<>();
        


        //Shortest Job First Example
        s.addProcesses(new ProcessSJF("p1", 0, 8));
        s.addProcesses(new ProcessSJF("p2", 1, 4));
        s.addProcesses(new ProcessSJF("p3", 2, 9));
        s.addProcesses(new ProcessSJF("p4", 3, 5));

        //Round Robin Section Example
        p.add(new Process("P1", 4, 0));
        p.add(new Process("P2", 8, 1));
        p.add(new Process("P3", 2, 3));
        p.add(new Process("P4", 6, 10));
        p.add(new Process("P5", 5, 12));

        //Priority Example
        prs.add(new Process("p1", 0, 10, 3));
        prs.add(new Process("p2", 0, 1, 1));
        prs.add(new Process("p3", 0, 2, 4));
        prs.add(new Process("p4", 0, 1, 5));
        prs.add(new Process("p5", 0, 5, 2));

        //AG Scheduling Example
        Process p1 = new Process("p1", 0, 17, 4, 7);
        Process p2 = new Process("p2", 2, 6, 7, 9);
        Process p3 = new Process("p3", 5, 11, 3, 4);
        Process p4 = new Process("p4", 15, 4, 6, 6);

        processes.add(p1);
        processes.add(p2);
        processes.add(p3);
        processes.add(p4);


        System.out.println("Process Scheduling Program");
        System.out.println("---------------------------");

        while (true) {
            System.out.println("1-Enter New Examples");
            System.out.println("2-Enter Assignment Examples");
            System.out.println("3-Exit");  
            
            int opt = input.nextInt();

            if (opt == 1) {
                System.out.println("Enter your Choice");
                System.out.println("------------------");
                System.out.println("1-Shortest Job First");
                System.out.println("2-Round Robin");
                System.out.println("3-Preemptive Priority");
                System.out.println("4-AG Scheduling");
                System.out.println("Enter your Choice :");

                int choicee = input.nextInt();

                if(choicee==1){
                    System.out.println("Enter processes Number : ");
                    int num = input.nextInt();

                    for (int i = 1; i <= num; i++) {
                        System.out.println("Enter process Name : ");
                        String name = input.next();
                        System.out.println("Enter Burst Time : ");
                        int burst = input.nextInt();
                        System.out.println("Enter Arrival Time : ");
                        int arrival = input.nextInt();
                        ProcessSJF proc = new ProcessSJF(name ,arrival ,burst);
                        s2.addProcesses(proc);
                    }
                    
                    System.out.println("Enter Context : ");
                    int context=input.nextInt();
                    s2.SJFSwitching(context);
                    s2.shortestJobFirst();
                    //s.printSJF(processes2, context);
                }

                else if(choicee==2){
                    System.out.println("Enter processes Number : ");
                    int num = input.nextInt();

                    for (int i = 1; i <= num; i++) {
                        System.out.println("Enter process Name : ");
                        String name = input.next();
                        System.out.println("Enter Burst Time : ");
                        int burst = input.nextInt();
                        System.out.println("Enter Arrival Time : ");
                        int arrival = input.nextInt();
                        Process proc = new Process(name,  burst,arrival);
                        processes2.add(proc);
                    }

                    System.out.println("Enter Context : ");
                    int context=input.nextInt();

                    System.out.println("Enter Quantum : ");
                    int quantum=input.nextInt();

                    roundRobin.printRR(processes2, quantum, context);
                }

                else if(choicee==3){
                    System.out.println("Enter processes Number : ");
                    int num = input.nextInt();

                    for (int i = 1; i <= num; i++) {
                        System.out.println("Enter process Name : ");
                        String name = input.next();
                        System.out.println("Enter Burst Time : ");
                        int burst = input.nextInt();
                        System.out.println("Enter Arrival Time : ");
                        int arrival = input.nextInt();
                        System.out.println("Enter Priority Time : ");
                        int priority = input.nextInt();
                        Process proc = new Process(name, arrival, burst, priority);
                        processes2.add(proc);
                    }

                    ArrayList<Integer> tempbusrt = new ArrayList<>();
                    for (int i = 0; i < processes2.size(); i++) {
                        tempbusrt.add(processes2.get(i).getBurstTime());
                    }
                    pr.setProcesses(processes2);
                    pr.setBurst(tempbusrt);
                    pr.run();
                    pr.print();
                }

                else if(choicee==4){
                    System.out.println("Enter processes Number : ");
                    int num = input.nextInt();
                    for (int i = 1; i <= num; i++) {
                        System.out.println("Enter process Name : ");
                        String name = input.next();
                        System.out.println("Enter Burst Time : ");
                        int burst = input.nextInt();
                        System.out.println("Enter Arrival Time : ");
                        int arrival = input.nextInt();
                        System.out.println("Enter Priority Time : ");
                        int priority = input.nextInt();
                        System.out.println("Enter Quantum Time : ");
                        double quantum = input.nextDouble();
                        Process proc = new Process(name, arrival, burst, priority, quantum);
                        processes2.add(proc);
                    }
                    a.AGschedule(processes2);
                }
                processes2.clear();
            }

            else if (opt == 2) {
                System.out.println("Enter your Choice");
                System.out.println("------------------");
                System.out.println("1-Shortest Job First\n" +
                        "2-Round Robin\n" +
                        "3-Preemptive Priority\n" +
                        "4-AG Scheduling");

                System.out.println("Enter your Choice :");
                int choice = input.nextInt();
                if (choice == 1) {
                    s.SJFSwitching(0);
                    s.shortestJobFirst();
                } 
                
                else if (choice == 2) {
                    roundRobin.printRR(p, 3, 1);
                } 

                else if (choice == 3) {
                    ArrayList<Integer> tempbusrt = new ArrayList<>();
                    for (int i = 0; i < prs.size(); i++) {
                        tempbusrt.add(prs.get(i).getBurstTime());
                    }
                    pr.setProcesses(prs);
                    pr.setBurst(tempbusrt);
                    pr.run();
                    pr.print();    
                } 

                else if (choice == 4) {
                    a.AGschedule(processes);
                }
            }

            else if (opt==3) {
                break;
            }
        }
        input.close();
    }
    
}