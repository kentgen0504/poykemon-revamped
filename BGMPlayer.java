import javax.sound.sampled.*;
import java.io.File;

public class BGMPlayer {
    private static Clip currentClip;

    public static void playBGM(String filePath){

        stopBGM();

        if (currentClip != null && currentClip.isRunning()){
            currentClip.stop();
            currentClip.close();
        }

        try {
            File bgmFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bgmFile);
            currentClip = AudioSystem.getClip();
            currentClip.open(audioStream);
            currentClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Clip not initialized");
        }
    }

    public static void stopBGM(){
        if (currentClip != null) {
            if (currentClip.isRunning()) {
                currentClip.stop(); // Stop the currently playing BGM
            }
            currentClip.close(); // Release resources associated with the clip
            currentClip = null; // Reset the clip to avoid overlap issues
        }
    }
}