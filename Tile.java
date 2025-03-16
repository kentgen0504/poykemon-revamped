import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Tile {
    public static final String TALL_GRASS = "Tall Grass";
    public static final String ROCK = "Rock";
    public static final String TRANSPORT_TILE = "Transport Tile";
    public static final String RESTRICT_TILE = "Restrict Tile";
    public static final String DOCK = "Dock";

    private final String name;
    private final Position position;
    private final Image image;

    public Tile(String name, Position position, Image image){
        this.name = name;
        this.position = position;
        this.image = image;
    }

    public String getName(){
        return this.name;
    }

    public Position getPosition(){
        return this.position;
    }

    public Image getImage(){
        return this.image;
    }

    public boolean isPlayerOnTile(){
        return  Player.getInstance().getPosition().equals(this.position);
    }
}

interface IPoykemonEncounter{
    public static void triggerPoykemonEncounter(String location, float encounterChance){
        if (!Randomizer.isSuccessful(encounterChance)){
            return;
        }

        GameState.getInstance().generateEncounter(location);
        System.out.println("PokemonEncounter");
    }
}

interface ITravel{
    public static void transferLocation(String location, Position entry){

        BGMPlayer.stopBGM();
        
        switch(location){
            case Location.GRASSLAND:
                Player.getInstance().setLocation(Grassland.getInstance());
                GamePanel.getInstance().setBackground(Color.WHITE);
                break;
            case Location.LAKE:
                Player.getInstance().setLocation(Lake.getInstance());
                GamePanel.getInstance().setBackground(new Color(172,183, 236));
                break;
            case Location.CAVE:
                Player.getInstance().setLocation(Cave.getInstance());
                GamePanel.getInstance().setBackground(Color.BLACK);
                break;
            case Location.STORE:
                Player.getInstance().setLocation(Store.getInstance());
                GamePanel.getInstance().setBackground(Color.BLACK);
                break;
            default:
                break;
        }

                                        

        Player.getInstance().setPosition(entry);
    }
}

abstract class WalkableTile extends Tile{
    public WalkableTile(String name, Position position, Image image){
        super(name, position, image);
    }

    public abstract void activateEventOnStep();
}

class TallGrass extends WalkableTile{
    private final static float ENCOUNTER_CHANCE = 20.0F;

    TallGrass(Position position){
        super(TALL_GRASS, position, new ImageIcon("Assets/Textures/TallGrass.png").getImage());
    }

    @Override
    public void activateEventOnStep(){
        IPoykemonEncounter.triggerPoykemonEncounter(Location.GRASSLAND, ENCOUNTER_CHANCE);
    }
}

class TransportTile extends WalkableTile{
    private String newLocation;
    private Position entry;

    public TransportTile(Position position, String newLocation, Position entry) {
        super(TRANSPORT_TILE, position, new ImageIcon("").getImage());
        this.newLocation = newLocation;
        this.entry = entry;
    }

    @Override
    public void activateEventOnStep() {
        ITravel.transferLocation(newLocation, entry);
    }
}

class Dock extends WalkableTile{

    public Dock(Position position) {
        super(DOCK, position, new ImageIcon("").getImage());
    }

    @Override
    public void activateEventOnStep() {
        Player.getInstance().setOnBoatStatus(false);
    }
}

abstract class UnwalkableTile extends Tile{
    public UnwalkableTile(String name, Position position, Image image){
        super(name, position, image);
    }
}

class RestrictTile extends UnwalkableTile{
    public RestrictTile(Position position) {
        super(ROCK, position, new ImageIcon("").getImage());
    }
}

class Rock extends UnwalkableTile{
    public Rock(Position position) {
        super(ROCK, position, new ImageIcon("Assets/Textures/Rock.png").getImage());
    }
}

