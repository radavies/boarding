package boarding;

public class Passenger implements Comparable<Passenger>{

    private BoardingPass boardingPass;
    public int GetRow(){
        return boardingPass.GetRow();
    }
    public int GetSeat(){
        return boardingPass.GetSeat();
    }

    public int GetReseatingOrder() {
        int seatNumber = GetSeat();
        if(seatNumber <= 2) {
            //0,1,2 as is
            return seatNumber;
        }
        else {
            switch (seatNumber) {
                case 3:
                    return 5;
                case 4:
                    return 4;
                case 5:
                    return 3;
            }
        }

        return 0;
    }

    public Passenger(BoardingPass _boardingPass){
        boardingPass = _boardingPass;
    }

    @Override
    public int compareTo(Passenger o) {
        return Integer.compare(GetReseatingOrder(), o.GetReseatingOrder());
    }
}
