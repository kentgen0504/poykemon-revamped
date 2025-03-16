import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public abstract class Location {
    public static final String GRASSLAND = "Grassland";
    public static final String LAKE = "Lake";
    public static final String CAVE = "Cave";
    public static final String STORE = "Store";

    public static final Image CAVE_LIGHT = new ImageIcon("Assets/Textures/Cave-Light.png").getImage();

    protected static final Tile[] GRASSLAND_TILES = getMapTilesFromDB(GRASSLAND);
    protected static final Tile[] LAKE_TILES = getMapTilesFromDB(LAKE);
    protected static final Tile[] CAVE_TILES = getMapTilesFromDB(CAVE);
    protected static final Tile[] STORE_TILES = getMapTilesFromDB(STORE);

    private static final String GRASSLAND_BGM = "Assets/BGM/grasslandBGM.wav";
    private static final String LAKE_BGM = "Assets/BGM/lakeBGM.wav";
    private static final String CAVE_BGM = "Assets/BGM/caveBGM.wav";
    private static final String STORE_BGM = "Assets/BGM/storeBGM.wav";

    private final String name;
    private final Image mapImage;
    private final Tile[] mapTiles;
    private final ArrayList<NPC> NPCs;
    private final Image encounterImage;

    private int numPoykemonCaught;

    public Location(String name, Image mapImage, Image encounterImage, Tile[] mapTiles){
        this.name = name;
        this.mapImage = mapImage;
        this.encounterImage = encounterImage;
        this.mapTiles = mapTiles;
        this.NPCs = new ArrayList<>();

        this.numPoykemonCaught = 0;

        //BGMPlayer.playBGM(getBGMFilePath());
    }

    public String getName(){
        return this.name;
    }

    public Image getMapImage(){
        return this.mapImage;
    }

    public Tile[] getMapTiles(){
        return this.mapTiles;
    }

    public ArrayList<NPC> getNPCs() {
        return NPCs;
    }

    public Image getEncounterImage(){
        return this.encounterImage;
    }

    public void incrementNumPoykemonCaught(){
        this.numPoykemonCaught++;
    }

    public int getNumPoykemonCaught(){
        return this.numPoykemonCaught;
    }

    public void activateAllTileEvents(){
        for(Tile tile: mapTiles){
            if (tile instanceof WalkableTile walkableTile){
                if (walkableTile.isPlayerOnTile()){
                    walkableTile.activateEventOnStep();
                } 
            }
        }
    }

    private static Tile[] getMapTilesFromDB(String locationName){
        String filePath =   switch(locationName){
                                case GRASSLAND -> "DataBank/grassland-tiles.csv";
                                case LAKE -> "DataBank/lake-tiles.csv";
                                case CAVE -> "DataBank/cave-tiles.csv";
                                case STORE -> "DataBank/store-tiles.csv";
                                default -> null;
                            };

        String[] retrievedLines = DatabaseManager.retrieveLinesFromDB(filePath);
        Tile[] locationTiles = new Tile[retrievedLines.length];
        String tileType;
        Position tilePosition;

        for (int i = 0; i < retrievedLines.length; i++){
            String[] bufferArray = retrievedLines[i].split(",");

            tileType = bufferArray[0];
            tilePosition = new Position(Integer.parseInt(bufferArray[1]), Integer.parseInt(bufferArray[2]));

            locationTiles[i] =  switch (tileType) {
                                case Tile.TALL_GRASS -> new TallGrass(tilePosition);
                                case Tile.TRANSPORT_TILE -> new TransportTile(tilePosition, bufferArray[3], new Position(Integer.parseInt(bufferArray[4]), Integer.parseInt(bufferArray[5])));
                                case Tile.RESTRICT_TILE -> new RestrictTile(tilePosition);
                                case Tile.DOCK -> new Dock(tilePosition);
                                default -> throw new AssertionError();
                            };
        }

        return locationTiles;
    }

    public abstract void processGameEvents();

    private String getBGMFilePath() {               //gets the bgm file path for each location
        return switch (name) {
            case GRASSLAND -> GRASSLAND_BGM;
            case LAKE -> LAKE_BGM;
            case CAVE -> CAVE_BGM;
            case STORE -> STORE_BGM;
            default -> null;
        };
    }

    public static void stopBGM() {                  //stopBGM method
        BGMPlayer.stopBGM();
    }
}

class Grassland extends Location {
    private static Grassland grassland;

    private Grassland() {
        super(GRASSLAND, 
                new ImageIcon("Assets/Maps/Grassland.png").getImage(), 
                new ImageIcon("Assets/Maps/Encounter-Grass.png").getImage(), 
                GRASSLAND_TILES);
    }

    public static Grassland getInstance(){
        if (grassland == null){
            grassland = new Grassland();
        }
        return grassland;
    }

    @Override
    public void processGameEvents() {
        activateAllTileEvents();
    }
}

class Lake extends Location {
    private static Lake lake;

    private Lake() {
        super(LAKE, 
                new ImageIcon("Assets/Maps/Lake.png").getImage(), 
                new ImageIcon("Assets/Maps/Encounter-Lake.png").getImage(),
                LAKE_TILES);
    }

    public static Lake getInstance(){
        if (lake == null){
            lake = new Lake();
        }
        return lake;
    }

    @Override
    public void processGameEvents() {
        Player.getInstance().setOnBoatStatus(true);

        activateAllTileEvents();

        if (Player.getInstance().isOnBoat()){
            IPoykemonEncounter.triggerPoykemonEncounter(LAKE, 20.0F);
        }
    }

    
}

class Cave extends Location {
    private static Cave cave;

    private Cave() {
        super(CAVE, 
                new ImageIcon("Assets/Maps/Cave.png").getImage(), 
                new ImageIcon("Assets/Maps/Encounter-Cave.png").getImage(),
                CAVE_TILES);
    }

    public static Cave getInstance(){
        if (cave == null){
            cave = new Cave();
        }
        return cave;
    }

    @Override
    public void processGameEvents() {
        activateAllTileEvents();
        IPoykemonEncounter.triggerPoykemonEncounter(CAVE, 20.0F);
    }
}

class Store extends Location {
    private static Store store;

    private Store(){
        super(STORE, 
                new ImageIcon("Assets/Maps/Store.png").getImage(), 
                new ImageIcon("").getImage(),
                STORE_TILES);
    }

    public static Store getInstance(){
        if (store == null){
            store = new Store();
        }
        return store;
    }

    @Override
    public void processGameEvents() {
        activateAllTileEvents();
    }
}

