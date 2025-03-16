import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class DatabaseManager {
    public static String[] retrieveLinesFromDB(String fileLocation){
        String[] processedData;

        LinkedList<String> retrievedData = new LinkedList<>();
        File file = new File(fileLocation);
        Scanner scanner = null;

        if (!file.exists() || !file.canRead()){
            return null;
        }

        try {
            scanner = new Scanner(file);
        } 
        catch (Exception e) {
            return null;
        }

        while(scanner.hasNextLine()){
            retrievedData.add(scanner.nextLine());
        }
        scanner.close();
        
        processedData = new String[retrievedData.size()];
        for (int i = 0; i < processedData.length; i++){
            processedData[i] = (String)retrievedData.removeFirst();
        }

        return processedData;
    }
}
