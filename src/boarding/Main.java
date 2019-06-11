package boarding;

import jdk.jshell.spi.ExecutionControl;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionControl.NotImplementedException {
        Runner runner = new Runner();
        runner.Run();
    }
}