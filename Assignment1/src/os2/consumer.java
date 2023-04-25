package os2;

/**
 * @author Mahmoud ahmed abdo
**/

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import java.awt.event.*;  
import javax.swing.*;
import java.util.concurrent.TimeUnit;


public class consumer implements Runnable{
    
    int count =0, check_consumer=0;
    String large_prime, fileName;
    buffer buffer;
    long sTime;
    
    public consumer(buffer buffer, String fileName, long s) {
       this.buffer = buffer;
       this.fileName = fileName;
       sTime = s;
    }
   
    @Override
    public void run() {
       String item;
        
    try {
        FileWriter fw= new FileWriter(fileName);
        while(true)
        {
            if(buffer.element==0 && buffer.check==true)
            {
                 fw.close();
                 long eTime = System.currentTimeMillis();
                 
                 ///First user interface for consumer 
                 JFrame f2=new JFrame("output");
                 JLabel o1,o2,o3,o4 ,o5,o6;
                 
                 o1=new JLabel("The Largest Prime Number : ");  
                 o1.setBounds(30, 20, 300, 30); 
                 
                 o2=new JLabel(large_prime); 
                 o2.setBounds(310, 20, 40, 30);
                 
                 o3=new JLabel("# of element (Prime Number) generated : ");  
                 o3.setBounds(30, 60, 300, 30);
                 
                 o4=new JLabel(Integer.toString(count));  
                 o4.setBounds(310, 60, 40, 30);

                 o5=new JLabel("Time elapsed since the start of processing :  "); 
                 o5.setBounds(30,100, 300,30);
                 
                 long ti = (eTime - sTime);
                 String tim =String.valueOf(ti)+ " ms" ;
       
                 o6=new JLabel(tim);  
                 o6.setBounds(310,100, 200,30);
                 
                 JButton b=new JButton("Finish program");  
                 b.setBounds(30,140,150,30);  
                 b.addActionListener(new ActionListener(){  
                 public void actionPerformed(ActionEvent e) {System.exit(0);}  
                 })
                 ;
                 f2.add(o1);f2.add(o2);f2.add(o3);f2.add(o4);
                 f2.add(o5);f2.add(o6);
                 f2.add(b);
                 f2.setSize(450,230); 
                 f2.setLayout(null);  
                 f2.setVisible(true);
                 TimeUnit.SECONDS.sleep(60);
                 System.exit(0);  
            }
            else{
               item = buffer.consume();
               fw.write("'" + String.valueOf(item) + "', ");
               large_prime=item;
               count++;
            }
        }
      }
      catch (IOException ex) {
                    Logger.getLogger(consumer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
            Logger.getLogger(consumer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
