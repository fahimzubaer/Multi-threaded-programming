/*
Name: Fahim Zubaer
*/

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;



public class Conveyor {
	
    private final static String inputFile = "config.txt";

	private static ArrayList<Stations> StationList;
	private static int Stations;

	public static void main(String[] args){
        
        System.out.println("\n * * * SIMULATION BEGINS * * *\n\n" +  " * * * SIMULATION ENDS * * *\n\n" );
        StationList = new ArrayList<Stations>();
        readinput(inputFile);
        for(Stations s1: StationList){
	    new Thread(new Conveyor().new wload(s1)).start();
	    
	}
}

	private static void readinput(String inputFile) {
		
		int i = 0;
		int y;
		String x;
		
		FileReader file1 = null;
		BufferedReader reader = null;
		
		try {
			
			file1 = new FileReader(inputFile);
			reader = new BufferedReader(file1);
			
			Stations = Integer.valueOf(reader.readLine());;

			while((x = reader.readLine()) != null){
				if(i == 0)
					y = Stations - 1;
				else y = i - 1;
				Stations station1 = new Stations(i,y,Integer.valueOf(x));
				StationList.add(station1);
				++i;
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Could not open file");
			e.printStackTrace();
			
		} catch (IOException e) {
			System.out.println("Could not read from file");
			e.printStackTrace();
			
		}finally{
			try {
				file1.close();
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	class wload implements Runnable{
		
		Stations station1;
		Stations station2;
		
		public wload(Stations stationx){
			
			this.station1 = stationx;
			int var = StationList.indexOf(stationx);
			
			if(var != 0)
				station2 = StationList.get(var - 1);
			else station2 = StationList.get(StationList.size()-1);
		}
		@Override
		public void run() {
			
			System.out.println("Routing Station "+this.station1.sID+": In-Connection set to conveyor. "+this.station1.conv1);
			System.out.println("Routing Station "+this.station1.sID+": Out-Connection set to conveyor. "+this.station1.conv2);
			System.out.println("Routing Station "+this.station1.sID+": Workload set. Station " +this.station1.sID+" has " +this.station1.wload+ " package groups to move.");
			System.out.println("Routing Station "+this.station1.sID+": has " +this.station1.wload+ " package groups left to move.");
			
			while(station1.wload != 0){
				
				Random random = new Random();
				
				try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {}
				station1.resolve(station2);
			}
			
			if(station1.wload == 0){
				System.out.println("\n * * Station "+this.station1.sID+": Workload successfully completed. * *\n");
				
			}			
		}
	}
}
