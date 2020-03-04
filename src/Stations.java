/*
Name: Fahim Zubaer
*/

import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;
import java.util.concurrent.locks.Lock;

public class Stations {

	private final Lock lock= new ReentrantLock();
	
	public int conv1;
	public int conv2;
	public int sID;
	public int wload;
	
	public Stations(int sID, int conv1, int wload){
		
	
		this.sID = sID;
		this.wload = wload;
		this.conv1 = conv1;
		this.conv2 = sID;
	}
	
	public boolean LockStat(Stations operation){
		
		boolean lock1 = false;
		boolean lock2 = false;
		
		try{
			
			if(lock1 = this.lock.tryLock()) {
				System.out.println("Routing Station "+sID+": granted access to conveyor "+this.conv1);
			}
			
			if(lock2 = operation.lock.tryLock()){
				System.out.println("Routing Station "+sID+": granted access to conveyor "+this.conv2);
			}
			
		}finally{
			
			if(!(lock1 && lock2)){
				if(lock1){
					this.lock.unlock();
					System.out.println("Routing Station "+sID+": released access to conveyor "+this.conv1);
				}
				
				if(lock2){
					operation.lock.unlock();
					System.out.println("Routing Station "+sID+": released access to conveyor "+this.conv2);
				}
			}
		}
		return lock1 && lock2;
	}
	
	
     public void resolve(Stations operation){
		
		if(LockStat(operation)){
			
			try{
				System.out.println("Routing Station "+sID+": successfully moves packages on conveyor "+wload);
				
				Random var = new Random();
				
				try {
					Thread.sleep(var.nextInt(10));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				wload -= 1;
				
			}finally{
				
				this.lock.unlock();
				operation.lock.unlock();
				System.out.println("Routing Station "+sID+": released access to conveyor "+this.conv1);
				System.out.println("Routing Station "+sID+": released access to conveyor "+this.conv2);
				
			}			
		}
	}
}
