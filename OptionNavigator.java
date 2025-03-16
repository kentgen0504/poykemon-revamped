import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public abstract class OptionNavigator{
    protected final int[] activeOptionRef = {0, 0};

    private String[][] availableOptions;
    private boolean isEscapable;

    public OptionNavigator(String[][] availableOptions, boolean isEscapable){
        this.availableOptions = availableOptions;
        this.isEscapable = isEscapable;
    }

    public String getReadyAction(){
        return this.availableOptions[activeOptionRef[0]][activeOptionRef[1]];
    }

    public void moveUp(){
        if (activeOptionRef[1] == 0){
            return;
        }

        activeOptionRef[1]--;
    }

    public void moveDown(){
        if (activeOptionRef[1] == availableOptions[0].length - 1){
            return;
        }

        activeOptionRef[1]++;
    }

    public void moveLeft(){
        if (activeOptionRef[0] == 0){
            return;
        }

        activeOptionRef[0]--;
    }

    public void moveRight(){
        if (activeOptionRef[0] == availableOptions.length - 1){
            return;
        }

        activeOptionRef[0]++;
    }

    public void handleKeyboardInput(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP -> moveUp();
            case KeyEvent.VK_DOWN -> moveDown();
            case KeyEvent.VK_LEFT -> moveLeft();
            case KeyEvent.VK_RIGHT -> moveRight();
            case KeyEvent.VK_ENTER -> activateReadyOption();
            case KeyEvent.VK_ESCAPE ->  {
                                            if (isEscapable){
                                                GameState.getInstance().removeLastFromOptionNavStack();
                                            }
                                        }
        };
    }

    public abstract void drawOptionNavigator(Graphics g);
    public abstract void activateReadyOption();
}

class EncounterOptionNav extends OptionNavigator{
    private static final String CATCH = "CATCH";
    private static final String THROW = "THROW ROCK";
    private static final String BAIT = "BAIT";
    private static final String RUN = "RUN";


    public EncounterOptionNav(){
        super(new String[][]{{CATCH, THROW}, {BAIT, RUN}},
                false);
    }

    @Override
    public void drawOptionNavigator(Graphics g){
        // actions
        
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        
        g.setColor(Color.WHITE);
        g.fillRect(35, 370, 200, 40);
        g.setColor(Color.DARK_GRAY);
        g.drawString(CATCH, 40, 400);

        g.setColor(Color.WHITE);
        g.fillRect(35, 420, 200, 40);
        g.setColor(Color.DARK_GRAY);
        g.drawString(THROW, 40, 450);
        
        g.setColor(Color.WHITE);
        g.fillRect(245, 370, 200, 40);
        g.setColor(Color.DARK_GRAY);
        g.drawString(BAIT, 250, 400);

        g.setColor(Color.WHITE);
        g.fillRect(245, 420, 200, 40);
        g.setColor(Color.DARK_GRAY);
        g.drawString(RUN, 250, 450);

        // highlight ready action
        switch (getReadyAction()){

            case CATCH -> {
                ((Graphics2D) g).setStroke(new BasicStroke(5));
                g.setColor(Color.DARK_GRAY);
                g.drawRect(35, 370, 200, 40);
            }

            case THROW -> {
                ((Graphics2D) g).setStroke(new BasicStroke(5));
                g.setColor(Color.DARK_GRAY);
                g.drawRect(35, 420, 200, 40);
            }

            case BAIT -> {
                ((Graphics2D) g).setStroke(new BasicStroke(5));
                g.setColor(Color.DARK_GRAY);
                g.drawRect(245, 370, 200, 40);
            }

            case RUN -> {
                ((Graphics2D) g).setStroke(new BasicStroke(5));
                g.setColor(Color.DARK_GRAY);
                g.drawRect(245, 420, 200, 40);
            }
            default -> throw new AssertionError();
        }
    }

    @Override
    public void activateReadyOption(){
        switch (getReadyAction()){
            case CATCH -> GameState.getInstance().getOptionNavStack().add(new CatchOptionNav());
            case THROW -> GameState.getInstance().getEncounterHandler().throwRock();
            case BAIT -> GameState.getInstance().getOptionNavStack().add(new BaitOptionNav());
            case RUN -> GameState.getInstance().exitEncounter();
            default -> throw new AssertionError();
        }
    }
}

class CatchOptionNav extends OptionNavigator{
    private static final int MAX_VISIBLE_ITEM = 3;

    private static Poykeball[] ballPouch = Player.getInstance().getBallPouch();
    private static String[] ballOptions = getPoykeballNames();

    private int topOptionRef;

    public CatchOptionNav(){
        super(new String[][]{ballOptions},
                true);

        this.topOptionRef = 0;
    }

    private static String[] getPoykeballNames(){
        String ballOptions[] = new String[ballPouch.length];
        
        for (int i = 0; i < ballPouch.length; i++){
            ballOptions[i] = Player.getInstance().getBallPouch()[i].getName();
        }

        return ballOptions;
    }

    @Override
    public void drawOptionNavigator(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        Font stringFont = new Font(Font.MONOSPACED, Font.BOLD, 20);

        int optionHeight = 50;
        int panelWidth = 250;
        int panelHeight = (ballPouch.length < MAX_VISIBLE_ITEM ? optionHeight * ballPouch.length : optionHeight * MAX_VISIBLE_ITEM);

        int arc = 30;

        int padding = 10;
        int textMargin = 5;

        int verChoiceRef = activeOptionRef[1];
        
        // panel
        g.setColor(Color.WHITE);
        g.fillRoundRect((GamePanel.SRCEEN_WIDTH - panelWidth)/2 - padding, (GamePanel.SRCEEN_HEIGHT - optionHeight)/2 - padding, panelWidth + padding*2, panelHeight + padding*2, arc, arc);

        // panel border 
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect((GamePanel.SRCEEN_WIDTH - panelWidth)/2 - padding, (GamePanel.SRCEEN_HEIGHT - optionHeight)/2 - padding, panelWidth + padding*2, panelHeight + padding*2, arc, arc);

        // editing visible stuff
        if (topOptionRef + MAX_VISIBLE_ITEM <= verChoiceRef) {
            topOptionRef++;
        }
        else if (topOptionRef > verChoiceRef) {
            topOptionRef--;
        }

        // visible item
        int start = topOptionRef;
        int end = (ballPouch.length < MAX_VISIBLE_ITEM ? ballPouch.length : start + MAX_VISIBLE_ITEM);

        g.setColor(Color.BLACK);
        g.setFont(stringFont);
        for (int i = start; i < end; i++){
            String quantityFormat = "x " + ballPouch[i].getQuantity();

            g.drawString(ballPouch[i].getName(), (GamePanel.SRCEEN_WIDTH - panelWidth)/2 + textMargin, (GamePanel.SRCEEN_HEIGHT - optionHeight)/2 + optionHeight*(i - start + 1) - 15);
            g.drawString(quantityFormat, (GamePanel.SRCEEN_WIDTH + panelWidth)/2 - g.getFontMetrics(stringFont).stringWidth(quantityFormat) - textMargin, (GamePanel.SRCEEN_HEIGHT - optionHeight)/2 + optionHeight*(i - start + 1) - 15);
        }

        // highlight
        g.setColor(Color.RED);
        g.drawRoundRect((GamePanel.SRCEEN_WIDTH - panelWidth)/2, (GamePanel.SRCEEN_HEIGHT - optionHeight)/2 + optionHeight*(verChoiceRef - topOptionRef), panelWidth, optionHeight, arc, arc);
    }

    @Override
    public void activateReadyOption(){
        GameState.getInstance().getEncounterHandler().throwBall(ballPouch[activeOptionRef[1]]);
        GameState.getInstance().removeLastFromOptionNavStack();
    }
}

class BaitOptionNav extends OptionNavigator{
    private static final int MAX_VISIBLE_ITEM = 3;
    private static Bait[] baitPouch = Player.getInstance().getBaitPouch();
    private static String[] baitOptions = getBaitNames();
    private static int baitNum = baitOptions.length;

    private int topOptionRef;


    public BaitOptionNav(){
        super(new String[][]{baitOptions},
                true);
    }

    private static String[] getBaitNames(){
        String ballOptions[] = new String[baitPouch.length];
        
        for (int i = 0; i < baitPouch.length; i++){
            ballOptions[i] = Player.getInstance().getBallPouch()[i].getName();
        }

        return ballOptions;
    }

    @Override
    public void drawOptionNavigator(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        Font stringFont = new Font(Font.MONOSPACED, Font.BOLD, 20);

        int optionHeight = 50;
        int panelWidth = 250;
        int panelHeight = (baitNum < MAX_VISIBLE_ITEM ? optionHeight * baitNum : optionHeight * MAX_VISIBLE_ITEM);

        int arc = 30;

        int padding = 10;
        int textMargin = 5;

        int verChoiceRef = activeOptionRef[1];
        
        // panel
        g.setColor(Color.WHITE);
        g.fillRoundRect((GamePanel.SRCEEN_WIDTH - panelWidth)/2 - padding, (GamePanel.SRCEEN_HEIGHT - optionHeight)/2 - padding, panelWidth + padding*2, panelHeight + padding*2, arc, arc);

        // panel border 
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect((GamePanel.SRCEEN_WIDTH - panelWidth)/2 - padding, (GamePanel.SRCEEN_HEIGHT - optionHeight)/2 - padding, panelWidth + padding*2, panelHeight + padding*2, arc, arc);

        // editing visible stuff
        if (topOptionRef + MAX_VISIBLE_ITEM <= verChoiceRef) {
            topOptionRef++;
        }
        else if (topOptionRef > verChoiceRef) {
            topOptionRef--;
        }

        // visible item
        int start = topOptionRef;
        int end = (baitNum < MAX_VISIBLE_ITEM ? baitNum : start + MAX_VISIBLE_ITEM);

        g.setColor(Color.BLACK);
        g.setFont(stringFont);
        for (int i = start; i < end; i++){
            String quantityFormat = "x " + baitPouch[i].getQuantity();

            g.drawString(baitPouch[i].getName(), (GamePanel.SRCEEN_WIDTH - panelWidth)/2 + textMargin, (GamePanel.SRCEEN_HEIGHT - optionHeight)/2 + optionHeight*(i - start + 1) - 15);
            g.drawString(quantityFormat, (GamePanel.SRCEEN_WIDTH + panelWidth)/2 - g.getFontMetrics(stringFont).stringWidth(quantityFormat) - textMargin, (GamePanel.SRCEEN_HEIGHT - optionHeight)/2 + optionHeight*(i - start + 1) - 15);
        }

        // highlight
        g.setColor(Color.RED);
        g.drawRoundRect((GamePanel.SRCEEN_WIDTH - panelWidth)/2, (GamePanel.SRCEEN_HEIGHT - optionHeight)/2 + optionHeight*(verChoiceRef - topOptionRef), panelWidth, optionHeight, arc, arc);
    }

    @Override
    public void activateReadyOption(){
        GameState.getInstance().getEncounterHandler().throwBait(baitPouch[activeOptionRef[1]]);
        GameState.getInstance().removeLastFromOptionNavStack();
    }
}

class MenuOptionNav extends OptionNavigator{
    private static final String POYKEDEX = "POYKEDEX";
    private static final String BAG = "BAG";
    private static final String SAVE = "SAVE";
    private static final String EXIT = "EXIT";

    private static final String[] menuOptions = {POYKEDEX, BAG, SAVE, EXIT};

    public MenuOptionNav(){
        super(new String[][]{menuOptions},
                true);
    }

    @Override
    public void drawOptionNavigator(Graphics g) {
        Graphics2D g2D = (Graphics2D)g;
        Font stringFont = new Font(Font.MONOSPACED, Font.BOLD, 20);

        int padding = 10;
        int panelMargin = 5;
        int textMargin = 5;

        int optionHeight = 50;
        int panelWidth = 110;
        int panelHeight = menuOptions.length*optionHeight;
        int arc = 30;

        int verChoiceRef = activeOptionRef[1];

        // panel
        g2D.setColor(Color.white);
        g2D.fillRoundRect(GamePanel.SRCEEN_WIDTH - panelWidth - padding*2 - panelMargin, GamePanel.SRCEEN_HEIGHT/2 - panelHeight/2 - padding, panelWidth + padding*2, panelHeight + padding*2, arc, arc);

        g2D.setColor(Color.BLACK);
        g2D.setStroke(new BasicStroke(3));
        g2D.drawRoundRect(GamePanel.SRCEEN_WIDTH - panelWidth - padding*2 - panelMargin, GamePanel.SRCEEN_HEIGHT/2 - panelHeight/2 - padding, panelWidth + padding*2, panelHeight + padding*2, arc, arc);

        g.setFont(stringFont);
        for (int i = 0; i < menuOptions.length; i++){
            g.drawString(menuOptions[i], GamePanel.SRCEEN_WIDTH - panelWidth - padding, GamePanel.SRCEEN_HEIGHT/2 - panelHeight/2 + optionHeight*i + 30);
        }
        
        g.setColor(Color.RED);
        g.drawRoundRect(GamePanel.SRCEEN_WIDTH - panelWidth - padding - panelMargin, GamePanel.SRCEEN_HEIGHT/2 - panelHeight/2 + optionHeight*verChoiceRef, panelWidth, optionHeight, arc, arc);
    }

    @Override
    public void activateReadyOption() {
        switch (getReadyAction()){
            // case POYKEDEX -> ;
            // case BAG -> ;
            // case SAVE -> ;
            case EXIT -> System.exit(0);
            default -> throw new AssertionError();
        }
    }
}