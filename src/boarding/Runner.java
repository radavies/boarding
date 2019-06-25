package boarding;

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

    public void Run(){

        System.out.println("In order back to front boarding starting...");
        GenerateOrderedBoardingPasses();
        GenerateInOrderPassengers();
        int stepsTakenForInOrder = StartBoarding();

        Reset();

        System.out.println("In order front to back boarding starting...");
        GenerateOrderedReverseBoardingPasses();
        GenerateInOrderPassengers();
        int stepsTakenForInOrderReverse = StartBoarding();

        Reset();


        System.out.println("Outside in boarding starting...");
        GenerateOutsideInBoardingPasses();
        GenerateInOrderPassengers();
        int stepsTakenForOutsideIn = StartBoarding();

        Reset();

        System.out.println("Zone boarding starting...");
        GenerateZoneBoardingPassesAndPassengers();
        int stepsTakenForZone = StartBoarding();

        Reset();

        System.out.println("Random boarding starting...");
        GenerateOrderedBoardingPasses();
        GenerateRandomPassengers();
        int stepsTakenForRandom = StartBoarding();

        System.out.println(String.format("Steps taken for in order: %d", stepsTakenForInOrder));
        System.out.println(String.format("Steps taken for in order reverse: %d", stepsTakenForInOrderReverse));
        System.out.println(String.format("Steps taken for outside in: %d", stepsTakenForOutsideIn));
        System.out.println(String.format("Steps taken for zone: %d", stepsTakenForZone));
        System.out.println(String.format("Steps taken for random: %d", stepsTakenForRandom));

    }

    private void Reset(){
        aircraft = new Aircraft(28,3);
        passengers = new LinkedList<>();
    }

    private void GenerateOrderedBoardingPasses(){
        boardingPasses = new BoardingPass[aircraft.GetRows() * (aircraft.GetSeatsInRow() * 2)];
        int passCounter = 0;
        for(int rowCounter = 0; rowCounter < aircraft.GetRows(); rowCounter++){
            for(int seatCounter = 0; seatCounter < aircraft.GetSeatsInRow() * 2; seatCounter++){
                boardingPasses[passCounter] = new BoardingPass(rowCounter, seatCounter);
                passCounter++;
            }
        }
    }

    private void GenerateOrderedReverseBoardingPasses(){
        boardingPasses = new BoardingPass[aircraft.GetRows() * (aircraft.GetSeatsInRow() * 2)];
        int passCounter = 0;
        for(int rowCounter = aircraft.GetRows() -1; rowCounter >= 0; rowCounter--){
            for(int seatCounter = 0; seatCounter < aircraft.GetSeatsInRow() * 2; seatCounter++){
                boardingPasses[passCounter] = new BoardingPass(rowCounter, seatCounter);
                passCounter++;
            }
        }
    }

    private void GenerateOutsideInBoardingPasses(){
        boardingPasses = new BoardingPass[aircraft.GetRows() * (aircraft.GetSeatsInRow() * 2)];
        int passCounter = 0;

        //order is backward as it is popped off a queue
        int[] seatOrder = new int[]{3,2,4,1,5,0};

        for(int rowCounter = 0; rowCounter < aircraft.GetRows(); rowCounter++){
            for(int seatOrderCounter = 0; seatOrderCounter < seatOrder.length; seatOrderCounter++) {
                int seat = seatOrder[seatOrderCounter];
                boardingPasses[passCounter] = new BoardingPass(rowCounter, seat);
                passCounter++;
            }
        }
    }

    private void GenerateZoneBoardingPassesAndPassengers(){
        //Generate Zone 1
        int passCounter = 0;
        BoardingPass[] zoneOne = new BoardingPass[(aircraft.GetRows() / 4) * (aircraft.GetSeatsInRow() * 2)];

        for(int rowCounter = 0; rowCounter < aircraft.GetRows() / 4; rowCounter++){
            for(int seatCounter = 0; seatCounter < aircraft.GetSeatsInRow() * 2; seatCounter++){
                zoneOne[passCounter] = new BoardingPass(rowCounter, seatCounter);
                passCounter++;
            }
        }

        //Generate Zone 2
        passCounter = 0;
        BoardingPass[] zoneTwo = new BoardingPass[(aircraft.GetRows() / 4) * (aircraft.GetSeatsInRow() * 2)];
        for(int rowCounter = aircraft.GetRows() / 4; rowCounter <= ((aircraft.GetRows() / 4) * 2) -1; rowCounter++){
            for(int seatCounter = 0; seatCounter < aircraft.GetSeatsInRow() * 2; seatCounter++){
                zoneTwo[passCounter] = new BoardingPass(rowCounter, seatCounter);
                passCounter++;
            }
        }

        //Generate Zone 3
        passCounter = 0;
        BoardingPass[] zoneThree = new BoardingPass[(aircraft.GetRows() / 4) * (aircraft.GetSeatsInRow() * 2)];
        for(int rowCounter = ((aircraft.GetRows() / 4) * 2); rowCounter <= ((aircraft.GetRows() / 4) * 3) - 1; rowCounter++){
            for(int seatCounter = 0; seatCounter < aircraft.GetSeatsInRow() * 2; seatCounter++){
                zoneThree[passCounter] = new BoardingPass(rowCounter, seatCounter);
                passCounter++;
            }
        }

        //Generate Zone 4
        passCounter = 0;
        BoardingPass[] zoneFour = new BoardingPass[(aircraft.GetRows() / 4) * (aircraft.GetSeatsInRow() * 2)];
        for(int rowCounter = ((aircraft.GetRows() / 4) * 3); rowCounter < aircraft.GetRows(); rowCounter++){
            for(int seatCounter = 0; seatCounter < aircraft.GetSeatsInRow() * 2; seatCounter++){
                zoneFour[passCounter] = new BoardingPass(rowCounter, seatCounter);
                passCounter++;
            }
        }

        for(int rowCounter = 0; rowCounter < aircraft.GetRows() / 4; rowCounter++){
            GivePassengersBoardingPasses(zoneOne);
        }

        for(int rowCounter = 0; rowCounter < aircraft.GetRows() / 4; rowCounter++){
            GivePassengersBoardingPasses(zoneTwo);
        }

        for(int rowCounter = 0; rowCounter < aircraft.GetRows() / 4; rowCounter++){
            GivePassengersBoardingPasses(zoneThree);
        }

        for(int rowCounter = 0; rowCounter < aircraft.GetRows() / 4; rowCounter++){
            GivePassengersBoardingPasses(zoneFour);
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
            GivePassengersBoardingPasses(boardingPasses);
        }
    }

    private void GivePassengersBoardingPasses(BoardingPass[] boardingPasses) {
        Random rand = new Random();
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

    private int StartBoarding(){

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
