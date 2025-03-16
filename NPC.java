
import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class NPC {
    public static final String PROF_ARA = "Prof. Ara";
    public static final String BUTCHER = "Butcher";
    public static final String VENDOR = "Vendor";
    public static final String FISHERY = "Fishery Student";
    public static final String CAVE_CREW = "Cave Crew";
    public static final String KID = "Kid";

    public static final Image IMPORTANT_SIGN = new ImageIcon("Assets/NPC/Important.png").getImage();

    private final String name;
    private Position position;
    private final Image sprite;
    private boolean hasImportantDialogue;
    protected DialogueBox dialogue;

    public NPC(String name, Position position, Image sprite, boolean hasImportantDialogue){
        this.name = name;
        this.position = position;
        this.sprite = sprite;
        this.hasImportantDialogue = hasImportantDialogue;
        this.dialogue = new DialogueBox(name, new String[]{});
    }

    public String getName(){
        return this.name;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public Position getPosition(){
        return this.position;
    }

    public Image getSprite(){
        return this.sprite;
    }

    public void setHasImportantDialogue(Boolean hasImportantDialogue){
        this.hasImportantDialogue = hasImportantDialogue;
    }

    public boolean getHasImportantDialogue(){
        return this.hasImportantDialogue;
    }

    public void setDialogue(String[] dialogue){
        this.dialogue = new DialogueBox(name, dialogue);
    }

    public DialogueBox getDialogue(){
        return this.dialogue;
    }

    public abstract void interact();
}

class ProfAra extends NPC{
    private static ProfAra profAra;

    private ProfAra(){
        super(PROF_ARA,
                new Position(240,160), 
                new ImageIcon("Assets/NPC/Ara.png").getImage(),
                false);
    }

    public static ProfAra getInstance(){
        if (profAra == null){
            profAra = new ProfAra();
        }
        return profAra;
    }

    @Override
    public void interact(){
        GameState.getInstance().setDialogueBox(dialogue);
    }
}

class Vendor extends NPC{
    private static Vendor vendor;

    private  Vendor(){
        super(VENDOR,
                new Position(80, 120), 
                new ImageIcon("Assets/NPC/Shop.png").getImage(),
                false);
    }

    public static Vendor getInstance(){
        if (vendor == null){
            vendor = new Vendor();
        }
        return vendor;
    }

    @Override
    public void interact(){
        GameState.getInstance().setDialogueBox(null);
    }
}

class Butcher extends NPC{
    private static Butcher butcher;

    private Butcher(){
        super(BUTCHER,
                new Position(400, 120), 
                new ImageIcon("Assets/NPC/Butcher.png").getImage(),
                false);
    }

    @Override
    public void interact(){
        GameState.getInstance().setDialogueBox(dialogue);
    }

    public static Butcher getInstance(){
        if (butcher == null){
            butcher = new Butcher();
        }
        return butcher;
    }
}

class FisheryStudent extends NPC{
    private static FisheryStudent FISHERY_STUDENT;

    private FisheryStudent(){
        super(FISHERY,
                new Position(640,780), 
                new ImageIcon("Assets/NPC/Fish Hungover.png").getImage(),
                false);
    }

    public static FisheryStudent getInstance(){
        if (FISHERY_STUDENT == null){
            FISHERY_STUDENT = new FisheryStudent();
        }
        return FISHERY_STUDENT;
    }

    @Override
    public void interact(){
        GameState.getInstance().setDialogueBox(dialogue);
    }
}

class CaveCrew extends NPC{
    private static CaveCrew caveCrew;

    private CaveCrew(){
        super(CAVE_CREW,
            new Position(1240,780), 
            new ImageIcon("Assets/NPC/Guard Cave.png").getImage(),
            false);
    }

    public static CaveCrew getInstance(){
        if (caveCrew == null){
            caveCrew = new CaveCrew();
        }
        return caveCrew;
    }

    @Override
    public void interact(){
        GameState.getInstance().setDialogueBox(dialogue);
    }
}

class Kid extends NPC{
    private static Kid kid;

    private Kid(){
        super(KID,
            new Position(640,780), 
            new ImageIcon("Assets/NPC/Kid.png").getImage(),
            false);
    }

    public static Kid getInstance(){
        if (kid == null){
            kid = new Kid();
        }
        return kid;
    }

    @Override
    public void interact(){
        GameState.getInstance().setDialogueBox(dialogue);
    }
}