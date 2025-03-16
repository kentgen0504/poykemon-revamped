import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener{
    public static final int SRCEEN_WIDTH = 500;
    public static final int SRCEEN_HEIGHT = 500;
    public static final int TILE_SIZE = 40;
    public static final int FPS = 60; 

    private final GameState gameState;
    private final Player player;
    private final Timer timer;

    public static GamePanel gamePanel;

    public GamePanel(){
        this.setPreferredSize(new Dimension(SRCEEN_WIDTH, SRCEEN_HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(this);

        this.gameState = GameState.getInstance();
        this.player = Player.getInstance();
        
        int delay = 1000 / FPS; 
        this.timer = new Timer(delay, this);
        this.timer.start();
    }

    public static GamePanel getInstance(){
        if (gamePanel == null){
            gamePanel = new GamePanel();
        }
        return gamePanel;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        gameState.getScreenState().drawScreenState(g);

        if (gameState.getDialogueBox() != null){
            gameState.getDialogueBox().drawDialogueBox(g);
        }
        
        gameState.drawOptionNavigator(g);
        repaint();
    }

    @Override
	public void actionPerformed(ActionEvent e) {
        gameState.getScreenState().updateScreenState();
	}

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameState.getOptionNavStack().isEmpty()){
            gameState.getActiveOptionNavigator().handleKeyboardInput(e);
        }
        else if (gameState.getDialogueBox() != null) {
            gameState.handleDialogueBoxInput(e);
        }
        else {
            player.handleMovementInput(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
