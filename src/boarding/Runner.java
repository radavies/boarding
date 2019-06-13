package boarding;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Runner {

    private Aircraft aircraft;
    private LinkedList<Passenger> passengers;
    private BoardingPass[] boardingPasses;
    private int stepCounter;
    private static final boolean doSleeps = false;

    public Runner(){
        stepCounter = 1;
        aircraft = new Aircraft(28,3);
        passengers = new LinkedList<>();
        GenerateBoardingPasses();
        //GenerateInOrderPassengers();
        GenerateRandomPassengers();
    }

    public void Run() throws InterruptedException, ExecutionControl.NotImplementedException {
        System.out.println("Boarding Starting...");
        StartBoarding();
    }

    private void GenerateBoardingPasses(){
        boardingPasses = new BoardingPass[aircraft.GetRows() * (aircraft.GetSeatsInRow() * 2)];
        int passCounter = 0;
        for(int rowCounter = 0; rowCounter < aircraft.GetRows(); rowCounter++){
            for(int seatCounter = 0; seatCounter < aircraft.GetSeatsInRow() * 2; seatCounter++){
                boardingPasses[passCounter] = new BoardingPass(rowCounter, seatCounter);
                passCounter++;
            }
        }
    }

    private void GenerateInOrderPassengers(){
        int passCounter = 0;
        for(int rowCounter = 0; rowCounter < aircraft.GetRows(); rowCounter++){
            for(int seatCounter = 0; seatCounter < aircraft.GetSeatsInRow() * 2; seatCounter++){
                passengers.push(new Passenger(boardingPasses[passCounter]));
                passCounter++;
            }
        }
    }

    private void GenerateRandomPassengers(){
        Random rand = new Random();
        for(int rowCounter = 0; rowCounter < aircraft.GetRows(); rowCounter++){
            for(int seatCounter = 0; seatCounter < aircraft.GetSeatsInRow() * 2; seatCounter++){

                BoardingPass pass = null;
                int passIndex = -1;
                while(pass == null){
                    passIndex = rand.nextInt(boardingPasses.length);
                    pass = boardingPasses[passIndex];
                }
                passengers.push(new Passenger(pass));
                boardingPasses[passIndex] = null;
            }
        }
    }

    private void StartBoarding() throws InterruptedException{

        boolean boardingComplete = false;

        while (!boardingComplete) {

            if (!passengers.isEmpty() && aircraft.CanBoardNextPassenger()) {
                aircraft.BoardPassenger(passengers.pop());
                PrintUpdateMessage(false);
                PrintLines(aircraft.PrintAircraftState());
                if(doSleeps) {
                    Thread.sleep(1000);
                }
            }

            PrintUpdateMessage(true);
            aircraft.MovePassengers();

            PrintLines(aircraft.PrintAircraftState());
            stepCounter++;
            if(doSleeps) {
                Thread.sleep(1000);
            }

            boardingComplete = passengers.isEmpty() && aircraft.IsIsleClear();
        }

        PrintBoardingEndMessages();
    }

    private void PrintLines(ArrayList<String> toPrint){
        for (String line : toPrint) {
            System.out.println(line);
        }
    }

    private void PrintUpdateMessage(boolean isMovement){
        String type = isMovement ? "Moving" : "Boarding";
        System.out.println();
        System.out.println(String.format("%s...turn %d", type, stepCounter));
        System.out.println();
    }

    private void PrintBoardingEndMessages(){
        System.out.println();
        System.out.println(String.format("All passengers boarded in %d turns", stepCounter));
        System.out.println();
    }
}
