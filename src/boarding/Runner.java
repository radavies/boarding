package boarding;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Runner {

    private Aircraft aircraft;
    private LinkedList<Passenger> passengers;
    private BoardingPass[] boardingPasses;

    public Runner(){
        Reset();
    }

    public void Run() throws InterruptedException {


        GenerateBoardingPasses();

        System.out.println("In order boarding starting...");
        GenerateInOrderPassengers();
        int stepsTakenForInOrder = StartBoarding();
        
        Reset();

        System.out.println("Random boarding starting...");
        GenerateRandomPassengers();
        int stepsTakenForRandom = StartBoarding();

        if(stepsTakenForInOrder < stepsTakenForRandom){
            System.out.println(String.format("In order boarding was fastest by %d steps.", stepsTakenForRandom - stepsTakenForInOrder));
        }
        else if (stepsTakenForInOrder > stepsTakenForRandom){
            System.out.println(String.format("Random boarding was fastest by %d steps.", stepsTakenForInOrder - stepsTakenForRandom));
        }
        else{
            System.out.println(String.format("In order and random boarding were equal, taking %d steps.", stepsTakenForInOrder - stepsTakenForRandom));

        }
    }

    private void Reset(){
        aircraft = new Aircraft(28,3);
        passengers = new LinkedList<>();
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

    private int StartBoarding() throws InterruptedException{

        int stepCounter = 1;
        boolean boardingComplete = false;

        while (!boardingComplete) {

            if (!passengers.isEmpty() && aircraft.CanBoardNextPassenger()) {
                aircraft.BoardPassenger(passengers.pop());
                PrintUpdateMessage(false, stepCounter);
                PrintLines(aircraft.PrintAircraftState());
            }

            PrintUpdateMessage(true, stepCounter);
            aircraft.MovePassengers();

            PrintLines(aircraft.PrintAircraftState());
            stepCounter++;

            boardingComplete = passengers.isEmpty() && aircraft.IsIsleClear();
        }

        PrintBoardingEndMessages(stepCounter);
        return stepCounter;
    }

    private void PrintLines(ArrayList<String> toPrint){
        for (String line : toPrint) {
            System.out.println(line);
        }
    }

    private void PrintUpdateMessage(boolean isMovement, int stepCounter){
        String type = isMovement ? "Moving" : "Boarding";
        System.out.println();
        System.out.println(String.format("%s...turn %d", type, stepCounter));
        System.out.println();
    }

    private void PrintBoardingEndMessages(int stepCounter){
        System.out.println();
        System.out.println(String.format("All passengers boarded in %d turns", stepCounter));
        System.out.println();
    }
}
