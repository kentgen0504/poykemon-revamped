import java.awt.Graphics;

public abstract class ScreenState {
    public static final String OVERWORLD = "Overworld";
    public static final String ENCOUNTER = "Encounter";

    private final String name;

    public ScreenState(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public abstract void updateScreenState();
    public abstract void drawScreenState(Graphics g);
}

class OverworldScreen extends ScreenState{
    private static Player player;
    private static Position mapPosition;

    public OverworldScreen(){
        super(OVERWORLD);

        player = Player.getInstance();
        mapPosition = new Position(GamePanel.SRCEEN_WIDTH/2 - player.getPosition().getX() - GamePanel.TILE_SIZE/2, 
                                    GamePanel.SRCEEN_HEIGHT/2 - player.getPosition().getY() - GamePanel.TILE_SIZE/2);
    }

    @Override
    public void updateScreenState(){
        player.updatePlayerPosition();
        updateMapPosition();
    }

    public void updateMapPosition(){
        mapPosition = new Position(GamePanel.SRCEEN_WIDTH/2 - player.getPosition().getX() - GamePanel.TILE_SIZE/2, 
                                    GamePanel.SRCEEN_HEIGHT/2 - player.getPosition().getY() - GamePanel.TILE_SIZE/2);
    }

    @Override
    public void drawScreenState(Graphics g){
        int mapX = mapPosition.getX();
        int mapY = mapPosition.getY();

        g.drawImage(player.getLocation().getMapImage(), mapX, mapY, GamePanel.getInstance());

        // npc
        for (NPC npc: player.getLocation().getNPCs()){
            g.drawImage(npc.getSprite(), mapX + npc.getPosition().getX(), mapY + npc.getPosition().getY() - 16, GamePanel.getInstance());
            
            if (npc.getHasImportantDialogue()){
                g.drawImage(NPC.IMPORTANT_SIGN, mapX + npc.getPosition().getX() + 25, mapY + npc.getPosition().getY() - 30, GamePanel.getInstance());
            }
        }

        // player
        g.drawImage(player.getImage(), GamePanel.SRCEEN_WIDTH/2 - 20, GamePanel.SRCEEN_HEIGHT/2 - 36, GamePanel.getInstance());
        

        // tiles
        for (Tile tile: player.getLocation().getMapTiles()){
            g.drawImage(tile.getImage(), mapX + tile.getPosition().getX(), mapY + tile.getPosition().getY(), GamePanel.getInstance());
        }

        if (player.getLocation() == Cave.getInstance()){
            g.drawImage(Location.CAVE_LIGHT, 0, 0, GamePanel.getInstance());
        }
    }
}

class EncounterScreen extends ScreenState{
    private final Poykemon poykemon;

    public EncounterScreen(Poykemon poykemon){
        super(ENCOUNTER);
        this.poykemon = poykemon;
    }

    public Poykemon getPoykemon(){
        return this.poykemon;
    }

    @Override
    public void updateScreenState() {

    }

    @Override
    public void drawScreenState(Graphics g) {
        g.drawImage(Player.getInstance().getLocation().getEncounterImage(), 0, 0, GamePanel.getInstance());
        g.drawImage(poykemon.getImage(), GamePanel.SRCEEN_WIDTH/2 - 110, GamePanel.SRCEEN_HEIGHT/3 - 100, GamePanel.getInstance());
    }
}