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


//        int seatNumber = GetSeat();
//        if(seatNumber < seatsPerRow){
//            //0,1,2 as is
//            return seatNumber;
//        }
//        else {
//            //TODO: Try and think of a general way of doing this
//            if(seatsPerRow == 3){
//                switch (seatNumber) {
//                    case 3:
//                        return 5;
//                    case 4:
//                        return 4;
//                    case 5:
//                        return 3;
//                }
//            }
//            else if(seatsPerRow == 2){
//                switch (seatNumber) {
//                    case 2:
//                        return 3;
//                    case 3:
//                        return 2;
//                }
//            }
//            else {
//                throw new ExecutionControl.NotImplementedException("This row size is not implemented");
//            }
//        }
//        return 0;

    }

    public Passenger(BoardingPass _boardingPass){
        boardingPass = _boardingPass;
    }

    @Override
    public int compareTo(Passenger o) {
        return Integer.compare(GetReseatingOrder(), o.GetReseatingOrder());
    }
}
