import javax.swing.JFrame;

public class GameWindow extends JFrame{
    private static final GamePanel gamePanel = GamePanel.getInstance();

    public GameWindow(){
        this.add(gamePanel);
        this.setTitle("Poykemon");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}