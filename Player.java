import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Player{
    private static final char UP = 'U', DOWN = 'D', LEFT = 'L', RIGHT = 'R';
    private static final int ANIMATION_FRAMES = 4;
    private static final int FRAME_UPDATE_INTERVAL = 2; // Adjust for animation speed
    private static final Image[][] walkingSprites = new Image[4][4];
    private static final Image[][] sailingSprites = new Image[4][4]; 
    private static final int MOVEMENT_SPEED = 10;
    
    private static int frameCounter = 0;
    private static Player player;

    private String name;
    private Location location;
    private Position position;
    private Position destination;
    private char direction;
    private int spriteNum;
    private boolean onBoat;

    private final ArrayList<Poykeball> ballPouch;
    private final ArrayList<Bait> baitPouch;
    private final ArrayList<Poykemon> capturedPoykemon;

    public Player(){
        this.name = null;
        this.direction = 'D';
        this.spriteNum = 0;
        this.position = new Position(1120, 780);
        this.destination = null;
        this.location = Grassland.getInstance();

        this.ballPouch = new ArrayList<>();
        this.baitPouch = new ArrayList<>();
        this.capturedPoykemon = new ArrayList<>();

        this.onBoat = false;

        loadSprites();
        
        ballPouch.add(new Basicball(999));
        ballPouch.add(new Greatball(999));
        baitPouch.add(new ChipBerry(999));
    }

    public static Player getInstance(){
        if (player == null){
            player = new Player();
    }

        return player;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setDirection(char direction){
        this.direction = direction;
    }

    public char getDirection(){
        return this.direction;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public Position getPosition(){
        return this.position;
    }

    public void setDestination(Position destination){
        this.destination = destination;
    }

    public Position getDestination(){
        return this.destination;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public Location getLocation(){
        return this.location;
    }

    public Position getMapCoordinate(){
        return new Position(position.getX()/GamePanel.TILE_SIZE, position.getY()/GamePanel.TILE_SIZE);
    }

    public void setOnBoatStatus(boolean onBoat){
        this.onBoat = onBoat;
    }

    public boolean isOnBoat(){
        return this.onBoat;
    }

    private void loadSprites(){
        char[] directions = {'U', 'D', 'L', 'R'};

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                walkingSprites[i][j] = new ImageIcon("Assets/Player/" + directions[i] + "-" + j + ".png").getImage();
                sailingSprites[i][j] = new ImageIcon("Assets/Player/B" + directions[i] + "-" + j + ".png").getImage();
            }
        }
    }

    public Image getImage(){
        if (onBoat){
            return switch(direction){
                        case UP -> sailingSprites[0][spriteNum];
                        case DOWN -> sailingSprites[1][spriteNum];
                        case LEFT -> sailingSprites[2][spriteNum];
                        case RIGHT -> sailingSprites[3][spriteNum];
                        default -> null;
                    };
        }
        else {
            return switch(direction){
                        case UP -> walkingSprites[0][spriteNum];
                        case DOWN -> walkingSprites[1][spriteNum];
                        case LEFT -> walkingSprites[2][spriteNum];
                        case RIGHT -> walkingSprites[3][spriteNum];
                        default -> null;
                    };
        }
    }

    public Bait[] getBaitPouch(){
        for (Bait bait: baitPouch){
            if (bait.getQuantity() == 0){
                baitPouch.remove(bait);
            }
        }

        Bait[] baitArray = new Bait[baitPouch.size()];

        for (int i = 0; i < baitPouch.size(); i++){
            baitArray[i] = baitPouch.get(i);
        }

        return baitArray;
    }

    public Poykeball[] getBallPouch(){
        for (Poykeball ball: ballPouch){
            if (ball.getQuantity() == 0){
                ballPouch.remove(ball);
            }
        }

        Poykeball[] ballArray = new Poykeball[ballPouch.size()];

        for (int i = 0; i < ballPouch.size(); i++){
            ballArray[i] = ballPouch.get(i);
        }

        return ballArray;
    }

    public void addPoykemonToCollection(Poykemon poykemon){
        capturedPoykemon.add(poykemon);
    }

    public void processPlayerDestination(char direction){
        if (destination != null){
            return;
        }

        if (direction != this.direction){                                   // face same direction as keyboard input
            player.setDirection(direction);
            return;
        } 
        
        Position targetPosition = getPosition().createCopy();
        switch(direction){
            case UP -> targetPosition.moveY(-GamePanel.TILE_SIZE);
            case DOWN -> targetPosition.moveY(GamePanel.TILE_SIZE);
            case LEFT -> targetPosition.moveX(-GamePanel.TILE_SIZE);
            case RIGHT -> targetPosition.moveX(GamePanel.TILE_SIZE);
        };

        for (NPC npc: location.getNPCs()){
            if (npc.getPosition().equals(targetPosition)){
                return;
            }
        }

        for (Tile tile: location.getMapTiles()){
            if (tile instanceof UnwalkableTile && tile.getPosition().equals(targetPosition)){
                return;
            }
        }

        setDestination(targetPosition);
    }

    public void updatePlayerPosition(){
        if (destination != null){
            frameCounter = (frameCounter + 1) % FRAME_UPDATE_INTERVAL;
            if (frameCounter != 0) {
                return;
            }

            spriteNum = (spriteNum + 1) % ANIMATION_FRAMES;

            switch (this.direction){                                            // movement
                case UP -> position.moveY(-MOVEMENT_SPEED);
                case DOWN -> position.moveY(MOVEMENT_SPEED);
                case LEFT -> position.moveX(-MOVEMENT_SPEED);
                case RIGHT -> position.moveX(MOVEMENT_SPEED);
            }

            

            if (position.equals(destination)){
                spriteNum = 0;
                destination = null;                                             // player has reached destination;
                location.processGameEvents();
            }
        }
    }

    public void interact(){
        System.out.println(position.getX() + " " + position.getY());

        Position targetCoordinate = getPosition().createCopy();
        switch(direction){
            case UP -> targetCoordinate.moveY(-GamePanel.TILE_SIZE);
            case DOWN -> targetCoordinate.moveY(GamePanel.TILE_SIZE);
            case LEFT -> targetCoordinate.moveX(-GamePanel.TILE_SIZE);
            case RIGHT -> targetCoordinate.moveX(GamePanel.TILE_SIZE);
        };

        for (NPC npc: location.getNPCs()){
            if (npc.getPosition().equals(targetCoordinate)){
                npc.interact();
                return;
            }
        }
    }

    public void handleMovementInput(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_W, KeyEvent.VK_UP -> processPlayerDestination(UP);
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> processPlayerDestination(DOWN);
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> processPlayerDestination(LEFT);
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> processPlayerDestination(RIGHT);
            case KeyEvent.VK_ESCAPE -> GameState.getInstance().getOptionNavStack().add(new MenuOptionNav());
            case KeyEvent.VK_ENTER -> interact();
            default -> {
            }
        };
    }
}