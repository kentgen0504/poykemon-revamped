import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameState {
    private final Player player;
    
    private ScreenState screenState;
    private EncounterHandler encounterHandler;
    private ArrayList<OptionNavigator> optionNavStack;
    private DialogueBox dialogueBox;
    private ProgressManager progressManager;

    private static GameState gameState;

    private GameState(){
        this.player = Player.getInstance();
        this.progressManager = ProgressManager.getInstance();
        this.optionNavStack = new ArrayList<>();
        this.dialogueBox = null;
        this.screenState = new OverworldScreen();
        this.encounterHandler = null;
    }

    public static GameState getInstance(){
        if (gameState == null){
            gameState = new GameState();
        }
        return gameState;
    }

    public Player getPlayer(){
        return this.player;
    }

    public void setDialogueBox(DialogueBox dialogueBox){
        this.dialogueBox = dialogueBox;
    }

    public DialogueBox getDialogueBox(){
        return this.dialogueBox;
    }

    public void clearDialogueBox(){
        progressManager.checkProgression(ProgressManager.ACTION_TALK+dialogueBox.getSource());

        this.dialogueBox = null;
    }

    public void setScreenState(ScreenState screenState){
        this.screenState = screenState;
    }

    public ScreenState getScreenState(){
        return this.screenState;
    }

    public ArrayList<OptionNavigator> getOptionNavStack(){
        return this.optionNavStack;
    }

    public OptionNavigator getActiveOptionNavigator(){
        return this.optionNavStack.get(optionNavStack.size()-1);
    }

    public void drawOptionNavigator(Graphics g){
        for (int i = 0; i < optionNavStack.size();i++){
            optionNavStack.get(i).drawOptionNavigator(g);
        }
    }

    public void removeLastFromOptionNavStack(){
        this.optionNavStack.remove(optionNavStack.size()-1);
    }

    public EncounterHandler getEncounterHandler(){
        return this.encounterHandler;
    }

    public void generateEncounter(String location){
        Poykemon poykemon = Randomizer.getRandomPokemon(location);

        System.out.println("You have encountered: " + poykemon.getName());

        this.screenState = new EncounterScreen(poykemon);
        this.encounterHandler = new EncounterHandler(poykemon);
        this.optionNavStack.add(new EncounterOptionNav());
    }

    public void exitEncounter(){
        this.encounterHandler = null;
        this.screenState = new OverworldScreen();
        removeLastFromOptionNavStack();
    }

    public void handleDialogueBoxInput(KeyEvent e){
        if (dialogueBox != null) {
            switch(e.getExtendedKeyCode()){
                case KeyEvent.VK_ENTER -> dialogueBox.getNextDialogue();
            }
        }
    }
}
