

public class ProgressManager {
    public static final String ACTION_TALK = "Talk: ";
    public static final String ACTION_CATCH = "Catch: ";

    private int phase;

    private static ProgressManager progressManager;

    private static final ProfAra PROF_ARA = ProfAra.getInstance();
    private static final Vendor VENDOR = Vendor.getInstance();
    private static final Butcher BUTCHER = Butcher.getInstance();
    private static final FisheryStudent FISHERY_STUDENT = FisheryStudent.getInstance();
    private static final CaveCrew CAVE_CREW = CaveCrew.getInstance();
    private static final Kid KID = Kid.getInstance();

    private static final Grassland GRASSLAND = Grassland.getInstance();
    private static final Lake LAKE = Lake.getInstance();
    private static final Cave CAVE = Cave.getInstance();
    private static final Store STORE = Store.getInstance();

    private ProgressManager(){
        this.phase = 1;
        
        setupCurrentPhase();
    }

    public static ProgressManager getInstance(){
        if (progressManager == null){
            progressManager = new ProgressManager();
        }
        return progressManager;
    }


    public void setupCurrentPhase(){
        switch(phase){
            case 1:
                STORE.getNPCs().add(BUTCHER);
                STORE.getNPCs().add(VENDOR);
                STORE.getNPCs().add(PROF_ARA);

                GRASSLAND.getNPCs().add(FISHERY_STUDENT);
                GRASSLAND.getNPCs().add(CAVE_CREW);

                PROF_ARA.setHasImportantDialogue(true);
                PROF_ARA.setDialogue(new String[]{"At last! That algorithm actually worked! Oh, hello there, young hero!", 
                                                    "You’re in my lab. I summoned you because I need help finding my partner, Prof. Poy." ,
                                                    "He’s missing after an experiment went... ahem wrong.",
                                                    "Uh... if I could, I wouldn’t need you! Anyway, if you want to get home, you’ll need to help me find him. Deal?",
                                                    "Great! First, take this Poykedex. Your mission is to catch all 11 Poykemons– creatures that appeared after the glitch. Each one might hold clues to what happened to Prof. Poy",
                                                    "I’ll guide you through the basics.",
                                                    "THROW ROCK: Aggravates the Poykemon, increasing capture chances but may scare them away.",
                                                    "CATCH: Throw a Poykeball to capture them. Poykeballs are unlimited but less effective.",
                                                    "BAIT: Use bait to increase capture chances slightly.",
                                                    "RUN: Escape if you’re too scared to fight.",

                                                    "Use arrow keys to navigate.",
                                                    "Press ESC to open your bag (Poykeballs and bait).",
                                                    "Use the Poykedex to track caught Poykemons and read their descriptions.",
                                                    "Press TAB to save/load progress.",
                                                    "You may press SHIFT to access this tutorial again.",
                                                    "That’s all you need to know. Now, get out there and catch some Poykemons!"
                                                });

                BUTCHER.setDialogue(new String[]{"Definitely nothing bloody going on here!"});
                FISHERY_STUDENT.setDialogue(new String[]{"ZZZZzz...", "(ID badge reads UPV Fishery Student)", "Ugh, it reeks of alcohol and vomit here."});
                CAVE_CREW.setDialogue(new String[]{"Sorry lad, boulder's in the way. We’ll clear it soon!"});
                break;
            case 2:
                PROF_ARA.setHasImportantDialogue(false);

                STORE.getNPCs().remove(PROF_ARA);

                GRASSLAND.getNPCs().add(PROF_ARA);
                    
                PROF_ARA.setPosition(new Position(1040, 700));

                PROF_ARA.setDialogue(new String[]{"There’s a drunk student blocking your way? Guess you’ll have to wait. Catch more Poykemons while you’re here."});
                break;
            case 3:
                GRASSLAND.getNPCs().remove(FISHERY_STUDENT);
                GRASSLAND.getNPCs().add(KID);


                PROF_ARA.setDialogue(new String[]{"The drunk student was able to go back on his feet. He left in a hurry.", "Wherever he's going, hopefully he's alright."});
                KID.setHasImportantDialogue(true);
                KID.setDialogue(new String[]{"Finally, that nuisance is out of here.", "Hey, I found this fishing rod here.", "I was just wondering where to throw it, but I guess you can have it."});
                break;
            case 4:
                PROF_ARA.setDialogue(new String[]{"The path to the lake is clear now, you can explore that area."});

                KID.setHasImportantDialogue(false);
                GRASSLAND.getNPCs().remove(KID);
                break;
            case 5:
                CAVE_CREW.setHasImportantDialogue(true);
                CAVE_CREW.setDialogue(new String[]{"Thanks for waiting! Here’s a flashlight. Sorry, no batteries!"});
                break;
            case 6:
                CAVE_CREW.setHasImportantDialogue(false);
                CAVE_CREW.setDialogue(new String[]{"I may lost the batteries in the cave.", "Try asking around."});

                PROF_ARA.setHasImportantDialogue(true);
                PROF_ARA.setDialogue(new String[]{"Need batteries? Here you go. Congrats—you’re ready for the cave now!"});
                break;
            case 7:
                PROF_ARA.setHasImportantDialogue(false);
                GRASSLAND.getNPCs().remove(CAVE_CREW);
                break;
            default:
                break;
        }
    }

    public void checkProgression(String action){
        System.out.println(action);

        boolean willAdvance = switch(phase){
                                    case 1 -> true||action.equals(ACTION_TALK+NPC.PROF_ARA);
                                    case 2 -> true||action.equals(ACTION_CATCH+Location.GRASSLAND+4);
                                    case 3 -> true||action.equals(ACTION_TALK+NPC.KID);
                                    case 4 -> true||action.equals(ACTION_CATCH+Location.LAKE+2);
                                    case 5 -> true||action.equals(ACTION_TALK+NPC.CAVE_CREW);
                                    case 6 -> true||action.equals(ACTION_TALK+NPC.PROF_ARA);
                                    default -> false;
                                };
        if (willAdvance){
            moveToNextPhase();
        }
    }

    public void moveToNextPhase(){
        phase++;
        setupCurrentPhase();

        System.out.println("Phase: " + phase + " - Start");
    }
}