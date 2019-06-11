package boarding;

public class BoardingPass {
    private int row;
    public int GetRow(){
        return row;
    }
    private int seat;
    public int GetSeat(){
        return seat;
    }

    public BoardingPass(int _row, int _seat){
        row = _row;
        seat = _seat;
    }
}
