public class EncounterHandler {
    private static final int[] rockEffectiveness = {10,60};

    private final Poykemon poykemon;
    private int tolerance;
    private float currentCaptureChance;

    public EncounterHandler(Poykemon poykemon){
        this.poykemon = poykemon;
        this.tolerance = 100;
        this.currentCaptureChance = Randomizer.generateRandomValue(poykemon.getCaptureChanceRange());
    }

    public void throwBall(Poykeball ball){
        ball.useItem();

        if (Randomizer.isSuccessful(currentCaptureChance + ball.getCapturePower())){
            Player player = Player.getInstance();

            System.out.println("Poykemon successfully captured.");

            player.addPoykemonToCollection(poykemon);
            player.getLocation().incrementNumPoykemonCaught();
            ProgressManager.getInstance().checkProgression(ProgressManager.ACTION_CATCH + player.getLocation() + player.getLocation().getNumPoykemonCaught());

            GameState.getInstance().exitEncounter();
            return;
        }

        System.out.println("Captured Failed");
        System.out.println("Chance: " + currentCaptureChance + "   Tolerance: " + tolerance);

        this.tolerance -= 10;
        if (tolerance <= 0){
            GameState.getInstance().exitEncounter();
        }
    }

    public void throwBait(Bait bait){
        bait.useItem();

        currentCaptureChance += bait.getBaitPower();
    }

    public void throwRock(){
        int effectiveness = Randomizer.generateRandomValue(rockEffectiveness);

        tolerance -= effectiveness;
        currentCaptureChance += effectiveness/2;

        System.out.println("Chance: " + currentCaptureChance + "   Tolerance: " + tolerance);
        checkTolerance();   
    }

    public void checkTolerance(){
        if (tolerance <= 0){
            GameState.getInstance().exitEncounter();
        }
    }
}
