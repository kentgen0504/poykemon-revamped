
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class DialogueBox {
    private final String[] dialogueSequence;
    private int progress;

    private boolean lineProcessed;
    private boolean lineFinished;
    private String line1;
    private String line2;
    private String source;
    private int wordProgress;

    public DialogueBox(String source, String[] dialogueSequence){
        this.dialogueSequence = dialogueSequence;
        this.progress = 0;
        this.wordProgress = 0;
        this.source = source;

        this.lineProcessed = false;
        this.lineFinished = false;
    }

    public String getSource(){
        return this.source;
    }

    public void getNextDialogue(){
        if (lineFinished){
            this.wordProgress = 0;
            this.progress++;
        }
        
        this.lineProcessed = false;
        this.lineFinished = false;
        
        if (progress >= dialogueSequence.length){
            progress = 0;
            wordProgress = 0;
            GameState.getInstance().clearDialogueBox();
        }
    }

    public void processLines(Graphics g, Font stringFont, int lineSpace){
        String[] activeDialogue = dialogueSequence[progress].split(" ");
        boolean line1Full = false;
        boolean line2Full = false;
        int line1Width = 0;
        int line2Width = 0;

        line1 = new String();
        line2 = new String();

        while (wordProgress < activeDialogue.length) {
            String newLine = activeDialogue[wordProgress] + " ";
            int newLineWidth = g.getFontMetrics(stringFont).stringWidth(newLine);
        
            if (!line1Full && line1Width + newLineWidth < lineSpace){
                line1 += newLine;
                line1Width += newLineWidth;
                wordProgress++;
            }
            else {
                line1Full = true;
            }

            if (line1Full && line2Width + newLineWidth < lineSpace){
                line2 += newLine;
                line2Width += newLineWidth;
                wordProgress++;
            }
            else if (line1Full){
                line2Full = true;
            }

            if (line1Full && line2Full){
                break;
            }
        }

        if (wordProgress == activeDialogue.length){
            lineFinished = true;
        }

        lineProcessed = true;
    }

    public void drawDialogueBox(Graphics g){
        Graphics2D g2D = (Graphics2D)g;

        Font stringFont = new Font(Font.MONOSPACED, Font.BOLD, 20);
        int stringPadding = 5;

        int boxMargin = 10;
        int boxWidth = GamePanel.SRCEEN_WIDTH - boxMargin*2;
        int boxHeight = 100;
        int arc = 30;
        
        if (!lineProcessed){
            processLines(g, stringFont, boxWidth - stringPadding*2);
        }

        g.setColor(Color.WHITE);
        g.fillRoundRect(boxMargin, GamePanel.SRCEEN_HEIGHT - boxHeight - boxMargin, boxWidth, boxHeight, arc, arc);

        g2D.setColor(Color.BLACK);
        g2D.setStroke(new BasicStroke(3));
        g2D.drawRoundRect(boxMargin, GamePanel.SRCEEN_HEIGHT - boxHeight - boxMargin, boxWidth, boxHeight, arc, arc);

        g.setColor(Color.BLACK);
        g.setFont(stringFont);
        g.drawString(line1, boxMargin + stringPadding, GamePanel.SRCEEN_HEIGHT - boxHeight + 30);
        g.drawString(line2, boxMargin + stringPadding, GamePanel.SRCEEN_HEIGHT - boxHeight + 60);

    }
}
