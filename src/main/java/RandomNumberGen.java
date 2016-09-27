import java.util.Random;

public class RandomNumberGen {


    private static Random rand = new Random();

    public static int getRandIntBetween(int lowerBound, int upperBound){
        return rand.nextInt(upperBound - lowerBound) + lowerBound;
    }

    public static int getRandint(int upperBound){

        return rand.nextInt(upperBound);
    }

}
