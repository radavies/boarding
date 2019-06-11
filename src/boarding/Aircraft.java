package boarding;

import jdk.jshell.spi.ExecutionControl;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Aircraft {

    private Passenger[][] leftSeats;
    private Passenger[][] rightSeats;
    private HashMap<Integer, ArrayList<Passenger>> isle;

    private int rows;

    public int GetRows() {
        return rows;
    }

    private int seatsInRow;

    public int GetSeatsInRow() {
        return seatsInRow;
    }

    public Aircraft(int _rows, int _seatsInRow) {
        rows = _rows;
        seatsInRow = _seatsInRow;
        leftSeats = new Passenger[rows][seatsInRow];
        rightSeats = new Passenger[rows][seatsInRow];
        isle = new HashMap<>();
        InitIsle();
    }

    public boolean CanBoardNextPassenger() {
        return isle.get(0).isEmpty();
    }

    public boolean IsIsleClear(){
        for (int counter = 0; counter < rows; counter++) {
            if(!isle.get(counter).isEmpty()){
                return false;
            }
        }
        return true;
    }

    public void BoardPassenger(Passenger next) {
        isle.get(0).add(next);
    }

    public void MovePassengers() throws ExecutionControl.NotImplementedException {

        for (int rowCounter = rows - 1; rowCounter >= 0; rowCounter--) {
            if (!isle.get(rowCounter).isEmpty()) {
                ArrayList<Passenger> passengersInIsle = isle.get(rowCounter);
                ArrayList<Passenger> toRemove = new ArrayList<>();
                ArrayList<Passenger> unseatedPassengers = new ArrayList<>();

                //TODO: sort passengersInIsle here
                if(passengersInIsle.size() > 1) {
                    passengersInIsle.sort((Passenger x, Passenger y) -> {
                        try {
                            return x.GetReseatingOrder(seatsInRow) < y.GetReseatingOrder(seatsInRow) ? 0 : 1;
                        } catch (ExecutionControl.NotImplementedException e) {
                            return 0;
                        }
                    });
                }

                for (Passenger pax : passengersInIsle) {
                    if (pax.GetRow() == rowCounter) {
                        //Seat passenger
                        //TODO: what happens if the unseated people keep getting stuck due to putting them back in the wrong order?
                        Passenger unseatedPassenger = SeatPassenger(pax, rowCounter);
                        //Remove the current passenger from the isle if nobody moved out
                        //If someone moved out add them to the unseated passengers
                        if (unseatedPassenger == null) {
                            toRemove.add(pax);
                        }else{
                            unseatedPassengers.add(unseatedPassenger);
                        }
                    } else {
                        //Move passenger to next isle position
                        toRemove.add(pax);
                        isle.get(rowCounter + 1).add(pax);
                    }
                }
                //Remove passengers from isle (seating)
                for (Passenger pax : toRemove) {
                    isle.get(rowCounter).remove(pax);
                }
                //Add passengers to isle (unseated)
                for (Passenger pax : unseatedPassengers) {
                    isle.get(rowCounter).add(pax);
                }
            }
        }
    }

    public ArrayList<String> PrintAircraftState() {
        ArrayList<String> output = new ArrayList<>();
        for (int rowCounter = 0; rowCounter < rows; rowCounter++) {
            StringBuilder line = new StringBuilder();
            line.append("[");
            // Build left string
            for (int seatCounter = 0; seatCounter < seatsInRow; seatCounter++) {
                line.append(leftSeats[rowCounter][seatCounter] == null ? "0" : "X");
            }
            line.append("][");

            //Build isle string
            if (isle.get(rowCounter).isEmpty()) {
                line.append("0");
            } else {
                if (isle.get(rowCounter).size() > 1) {
                    line.append("M");
                } else {
                    line.append("X");
                }
            }
            line.append("][");

            //Build right string
            for (int seatCounter = 0; seatCounter < seatsInRow; seatCounter++) {
                line.append(rightSeats[rowCounter][seatCounter] == null ? "0" : "X");
            }
            line.append("]");
            output.add(line.toString());
        }
        return output;
    }

    private void InitIsle() {
        for (int counter = 0; counter < rows; counter++) {
            isle.put(counter, new ArrayList<>());
        }
    }

    private Passenger SeatPassenger(Passenger pax, int rowCounter) {
        Passenger backToIsle = null;
        if (pax.GetSeat() <= 2) {
            //Seat left
            for (int seatCounter = 0; seatCounter <= pax.GetSeat(); seatCounter++) {
                //Remove seated passengers in way
                backToIsle = leftSeats[rowCounter][seatCounter];
                if(backToIsle != null) {
                    leftSeats[rowCounter][seatCounter] = null;
                    //Beak here so it takes a turn per person that needs to go back
                    break;
                }
            }
            if (backToIsle == null) {
                //If nobody had to move out we can sit
                leftSeats[rowCounter][pax.GetSeat()] = pax;
            }

        } else {
            //Seat right
            for (int seatCounter = 0; seatCounter <= pax.GetSeat() - 3; seatCounter++) {
                //Remove seated passengers in way
                backToIsle = rightSeats[rowCounter][seatCounter];
                if(backToIsle != null) {
                    rightSeats[rowCounter][seatCounter] = null;
                    //Beak here so it takes a turn per person that needs to go back
                    break;
                }
            }
            if (backToIsle == null) {
                //If nobody had to move out we can sit
                rightSeats[rowCounter][pax.GetSeat() - 3] = pax;
            }
        }
        return backToIsle;
    }
}
