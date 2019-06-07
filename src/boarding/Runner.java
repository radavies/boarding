package boarding;

import java.util.ArrayList;

public class Runner {

    private Aircraft aircraft;
    private ArrayList<Passenger> passengers;

    public Runner()
    {
        aircraft = new Aircraft(28,3);
        passengers = new ArrayList<>();
        GeneratePassengers();

    }

    public void Run(){
        System.out.println("Boarding Starting...");
    }

    private void GeneratePassengers()
    {
        for(int rowCounter = 0; rowCounter < aircraft.getRows(); rowCounter++){
            for(int seatCounter = 0; seatCounter < aircraft.getSeatsInRow() * 2; seatCounter++){
                passengers.add(new Passenger(rowCounter, seatCounter));
            }
        }
    }
}
