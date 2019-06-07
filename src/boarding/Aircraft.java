package boarding;

public class Aircraft {

    private Passenger[][] leftSeats;
    private Passenger[][] rightSeats;

    private int rows;
    public int getRows(){ return rows; }

    private int seatsInRow;
    public int getSeatsInRow(){ return seatsInRow; }

    public Aircraft(int _rows, int _seatsInRow){
        rows = _rows;
        seatsInRow = _seatsInRow;
        leftSeats = new Passenger[rows][seatsInRow];
        rightSeats = new Passenger[rows][seatsInRow];
    }

}
