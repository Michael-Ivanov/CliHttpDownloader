package cli;

import connection.DownloadManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Starter {
    public static void main(String[] args) {
        new Starter().parseArgs(args);
    }

    private void parseArgs(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        todo: сделать парсер аргументов
        File file = new File("files.txt");
        String fileContents = readUrlsFile(file);
        List<String> files = Arrays.asList(fileContents.split("\\s+"));
        /*for (String fileStr : files) {
            System.out.println(fileStr);
            System.out.println("----------------------------");
        }*/

        try {
            for (String strUrl : files) {
                URL url = new URL(strUrl);
                Thread thread = new Thread(new DownloadManager(url));
                thread.start();
            }

        } catch (MalformedURLException e) {
            System.out.println("Incorrect URL");
        }

    }
    private String readUrlsFile(File file) {
        String result = null;
        try {
            FileReader fileReader = new FileReader(file);
            int input;
            StringBuilder builder = new StringBuilder();
            while ((input = fileReader.read()) != -1) {
                builder.append((char) input);
            }
            result = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
