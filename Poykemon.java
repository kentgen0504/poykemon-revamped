import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Poykemon {
    private String name;
    private String description;
    private Image image;
    
    private String nickname;
    private float height;
    private float weight;
    private float[] captureChanceRange;

    public Poykemon(String name, Image image, String description, float[] captureChanceRange, float height, float weight){
        this.name = name;
        this.nickname = null;
        this.image = image;
        this.description = description;
        this.captureChanceRange = captureChanceRange;

        this.height = height;
        this.weight = weight;
    }

    public String getName(){
        return this.name;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public String getNickname(){
        return this.nickname;
    }

    public String getDescription(){
        return this.description;
    }

    public float[] getCaptureChanceRange(){
        return this.captureChanceRange;
    }

    public Image getImage(){
        return this.image;
    }

    public float getHeight(){
        return this.height;
    }

    public float getWeight(){
        return this.weight;
    }
}

class Carapau extends Poykemon{
    private static final float[] heightRange = {1,2};
    private static final float[] weightRange = {3,5};
    private static final float[] captureChanceRange = {10,40};

    public Carapau() {
        super("Carapau", 
                new ImageIcon("Assets/Poykemon/carabao200.png").getImage(), 
                "The Grassland Bulldozer: A hardworking Carabao with a dangerous love for goring its enemies.",
                captureChanceRange,
                Randomizer.generateRandomValue(heightRange),
                Randomizer.generateRandomValue(weightRange));
    }
    
}

class Capou extends Poykemon{
    private static final float[] heightRange = {1,2};
    private static final float[] weightRange = {3,5};
    private static final float[] captureChanceRange = {10,40};

    public Capou() {
        super("Capou", 
                new ImageIcon("Assets/Poykemon/Capybara.png").getImage(), 
                "The Recycling Champion: Thrives on eating its own poop. The eco-friendliest Poykemon.",
                captureChanceRange,
                Randomizer.generateRandomValue(heightRange),
                Randomizer.generateRandomValue(weightRange));
    }
}

class Narda extends Poykemon{
    private static final float[] heightRange = {1,2};
    private static final float[] weightRange = {3,5};
    private static final float[] captureChanceRange = {10,40};

    public Narda(){
        super("Narda", 
                new ImageIcon("Assets/Poykemon/Maya Bird.png").getImage(), 
                "The Grass Trampler: A noisy bird that travels in flocks and ruins everything green.",
                captureChanceRange,
                Randomizer.generateRandomValue(heightRange),
                Randomizer.generateRandomValue(weightRange));
    }
}

class Mozz extends Poykemon{
    private static final float[] heightRange = {1,2};
    private static final float[] weightRange = {3,5};
    private static final float[] captureChanceRange = {10,40};

    public Mozz(){
        super("Mozz", 
                new ImageIcon("Assets/Poykemon/Mozz.png").getImage(), 
                "The Fiery Fox. Slow-moving but leaves blazing trails behind when angry.",
                captureChanceRange,
                Randomizer.generateRandomValue(heightRange),
                Randomizer.generateRandomValue(weightRange));
    }
}

class Java extends Poykemon{
    private static final float[] heightRange = {1,2};
    private static final float[] weightRange = {3,5};
    private static final float[] captureChanceRange = {10,40};

    public Java(){
        super("Java", 
                new ImageIcon("Assets/Poykemon/Java.png").getImage(), 
                "The Caffeinated Cat: Obsessed with coffee beans and expels them in the weirdest ways.",
                captureChanceRange,
                Randomizer.generateRandomValue(heightRange),
                Randomizer.generateRandomValue(weightRange));
    }
}

class MooDeng extends Poykemon{
    private static final float[] heightRange = {1,2};
    private static final float[] weightRange = {3,5};
    private static final float[] captureChanceRange = {10,40};

    public MooDeng(){
        super("Moo Deng", 
                new ImageIcon("Assets/Poykemon/Moo Deng.png").getImage(), 
                "The Biter Fish: Loves to bite people for fun.",
                captureChanceRange,
                Randomizer.generateRandomValue(heightRange),
                Randomizer.generateRandomValue(weightRange));
    }
}

class Bangs extends Poykemon{
    private static final float[] heightRange = {1,2};
    private static final float[] weightRange = {3,5};
    private static final float[] captureChanceRange = {10,40};

    public Bangs(){
        super("Bangs", 
                new ImageIcon("Assets/Poykemon/Bangs.png").getImage(), 
                "The Emo Milkfish: A mutant Bangus that cuts bangs when upset and drinks alcohol to cope.",
                captureChanceRange,
                Randomizer.generateRandomValue(heightRange),
                Randomizer.generateRandomValue(weightRange));
    }
}

class Nino extends Poykemon{
    private static final float[] heightRange = {1,2};
    private static final float[] weightRange = {3,5};
    private static final float[] captureChanceRange = {10,40};

    public Nino(){
        super("Niño", 
                new ImageIcon("Assets/Poykemon/Nino.png").getImage(), 
                "The Stickup Specialist: Infamous assassin who holds people at gunpoint.",
                captureChanceRange,
                Randomizer.generateRandomValue(heightRange),
                Randomizer.generateRandomValue(weightRange));
    }
}

class Knoll extends Poykemon{
    private static final float[] heightRange = {1,2};
    private static final float[] weightRange = {3,5};
    private static final float[] captureChanceRange = {10,40};

    public Knoll(){
        super("Knoll", 
                new ImageIcon("Assets/Poykemon/NEW Null Dragon.png").getImage(), 
                "The Glitch King: This creature accidentally caused the glitch. Can summon creatures from its domain by glitching reality.",
                captureChanceRange,
                Randomizer.generateRandomValue(heightRange),
                Randomizer.generateRandomValue(weightRange));
    }
}

class Polyhps extends Poykemon{
    private static final float[] heightRange = {1,2};
    private static final float[] weightRange = {3,5};
    private static final float[] captureChanceRange = {10,40};

    public Polyhps(){
        super("Polyhps", 
                new ImageIcon("Assets/Poykemon/Polymorphism.png").getImage(), 
                "The Mimic Monster. Can read minds and mimic those around it.",
                captureChanceRange,
                Randomizer.generateRandomValue(heightRange),
                Randomizer.generateRandomValue(weightRange));
    }
}

class RockPmon extends Poykemon{
    private static final float[] heightRange = {1,2};
    private static final float[] weightRange = {3,5};
    private static final float[] captureChanceRange = {10,40};

    public RockPmon(){
        super("Rock", 
                new ImageIcon("Assets/Poykemon/correct Stone.png").getImage(), 
                "The Living Block: Suddenly gained sentience and regrets it.",
                captureChanceRange,
                Randomizer.generateRandomValue(heightRange),
                Randomizer.generateRandomValue(weightRange));
    }
}

class Pythonite extends Poykemon{
    private static final float[] heightRange = {1,2};
    private static final float[] weightRange = {3,5};
    private static final float[] captureChanceRange = {10,40};

    public Pythonite(){
        super("Pythonite", 
                new ImageIcon("Assets/Poykemon/Python.png").getImage(), 
                "The Stone Serpent: A petrified statue that woke up after a millennium.",
                captureChanceRange,
                Randomizer.generateRandomValue(heightRange),
                Randomizer.generateRandomValue(weightRange));
    }
}

class Popalin extends Poykemon{
    private static final float[] heightRange = {1,2};
    private static final float[] weightRange = {3,5};
    private static final float[] captureChanceRange = {10,40};

    public Popalin(){
        super("Popalin", 
                new ImageIcon("Assets/Poykemon/Pangolin Stack.png").getImage(), 
                "The Stackable Protector: Stacks outer shells for defense and regenerates discarded clones.",
                captureChanceRange,
                Randomizer.generateRandomValue(heightRange),
                Randomizer.generateRandomValue(weightRange));
    }
}

class Uriblade extends Poykemon{
    private static final float[] heightRange = {1,2};
    private static final float[] weightRange = {3,5};
    private static final float[] captureChanceRange = {10,40};

    public Uriblade(){
        super("Uriblade", 
                new ImageIcon("Assets/Poykemon/Batcorpion.png").getImage(), 
                "The Toxic Beast: Releases corrosive urine to attack.",
                captureChanceRange,
                Randomizer.generateRandomValue(heightRange),
                Randomizer.generateRandomValue(weightRange));
    }
}




