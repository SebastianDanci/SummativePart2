package task1;

import java.io.File;

public class ApplicationRunner {

    public static void main(String[] args) {

        String fileLocation = System.getProperty("user.dir");
        String dataPath = fileLocation + File.separator + "literature-prizes.txt";
        if (ReadFile.readFile(dataPath)) {
            Base.runMenu();
        }
    }

}
