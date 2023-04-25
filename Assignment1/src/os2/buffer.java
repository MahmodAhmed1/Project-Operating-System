package os2;

/**
 * @author Mahmoud ahmed abdo
**/
/*  kdfdsfds */
import java.util.LinkedList;
import java.util.Queue;

public class buffer {

		private Queue<String> Q = new LinkedList<String>();
        private int size_q;
        int element = 0;
        Boolean check = false;
        String item = " ";
        
        buffer(int max_size)
        {
          size_q = max_size;
        }
        
        
        public void check(Boolean c)
        {
           this.check=c;
        }
        
        //Add item to stack
        public synchronized void add(String item)
        {
          
   
              while(Q.size()==size_q) {
                try {
                     wait();
                     
                } 
                catch (InterruptedException e) {
                  e.printStackTrace();
                }
              }
              
              this.Q.add(item);
              element++;
              notify();
        }
        
        //
        public synchronized String consume()
        {
             
             while(Q.isEmpty()) {
                 try {
                     wait();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }  
             item = Q.remove();
             element--;
             notify();
             return item;
        }
        
        

//        void large(int large_prime) {
//        	// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//        	throw new UnsupportedOperationException("Not supported yet.");
//        }
//        

   

}

