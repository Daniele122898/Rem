import java.util.Random;

public class RandomNumberGen {


    private static Random rand = new Random(System.currentTimeMillis());

    public static int getRandIntBetween(int lowerBound, int upperBound){
        return rand.nextInt(upperBound - lowerBound) + lowerBound;
    }

    public static int getRandint(int upperBound){

        return rand.nextInt(upperBound);
    }

}
