package os2;

/**
 * @author Mahmoud ahmed abdo
**/

import java.awt.event.*;  
import javax.swing.*;

public class Main {
	
	 public static void main(String[] args) {

             JFrame f=new JFrame("Calculate Prime Number"); 
             JLabel l1, l2, l3;
             l1=new JLabel("Enter N : ");  
             l1.setBounds(30,20, 100,30);  
             
             final JTextField tf=new JTextField();
             tf.setBounds(30,50, 180,20);
             
             
             l2=new JLabel("Buffer Size : ");  
             l2.setBounds(30,70, 100,30); 
             
             final JTextField tf2=new JTextField();  
             tf2.setBounds(30,100, 180,20);
             
             
             l3=new JLabel("Enter The File Name : ");  
             l3.setBounds(30,125, 180,30); 
    
             final JTextField tf3=new JTextField();  
             tf3.setBounds(30,150, 180,20);
             
             JButton b=new JButton("Start Producer");  
             b.setBounds(30,200,150,20);
             
             b.addActionListener(new ActionListener(){	 
            	 public void actionPerformed(ActionEvent e){
            		 
            		 long startTime = System.currentTimeMillis();                
            		 int n = Integer.valueOf(tf.getText());
            		 int numOfBuf = Integer.valueOf(tf2.getText());
            		 String file= String.valueOf(tf3.getText());
            		 buffer buf = new buffer(numOfBuf);
            		 Thread producerThread = new Thread(new producer(buf ,n , startTime));
            		 Thread consumerThread = new Thread(new consumer(buf, file+".txt", startTime));
            		 
            		 producerThread.start();
            		 consumerThread.start();
            	 }  
             });  
                
                f.add(tf); f.add(tf2); f.add(tf3);
                f.add(l1); f.add(l2); f.add(l3);
                f.setSize(400,300); f.add(b); 
                f.setLayout(null);  
                f.setVisible(true); 

                
	}


}

