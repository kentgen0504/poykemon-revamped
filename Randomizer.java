import java.util.Random;

public class Randomizer {
    private static final Random random = new Random();

    public static Poykemon getRandomPokemon(String location){
        // advise: Breakdown to different methods
        
        Poykemon[] poykemonSelection =  switch(location){
                                            case Location.GRASSLAND -> new Poykemon[]{new Carapau(), new Capou(), new Narda(), new Mozz(), new Java(), new Knoll()};
                                            case Location.LAKE -> new Poykemon[]{new MooDeng(), new Bangs(), new Nino(), new Polyhps()};
                                            case Location.CAVE -> new Poykemon[]{new RockPmon(), new Popalin(), new Pythonite(), new Uriblade()};
                                            default -> throw new AssertionError();
                                        };

        return poykemonSelection[random.nextInt(poykemonSelection.length)];
    }

    public static boolean isSuccessful(float chance){
        return random.nextFloat(100) < chance;
    }

    public static int generateRandomValue(int[] interval){
        return random.nextInt(interval[0], interval[1]);
    }

    public static float generateRandomValue(float[] interval){
        return random.nextFloat(interval[0], interval[1]);
    }

    public static String getRandomElementFrom(String[] stringArray){
        return stringArray[random.nextInt(stringArray.length)];
    }
}