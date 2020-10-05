package connection;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class DownloadManager implements Runnable{

    private URL url;

    public DownloadManager(URL url) {
        this.url = url;
    }

    private void getConnection() {
        String fileName = url.toString();
        fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
        System.out.println(fileName);

        File directory = new File("downloads");

        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                System.out.println("Unable to create directory");
            }
        } else {
            System.out.println("Directory exists: " + directory.getAbsolutePath());
            System.out.println("Proceeding...");
        }
        File outputFile = new File("downloads/" + fileName);

        try (BufferedInputStream is = new BufferedInputStream(url.openStream());
             FileOutputStream fos = new FileOutputStream(outputFile)){

            byte[] dataBuffer = new byte[1024];
            int bytesRead;

            System.out.println("downloading " + fileName + "...");
            while ((bytesRead = is.read(dataBuffer, 0, 1024)) != -1) {
                fos.write(dataBuffer, 0, bytesRead);
            }
            System.out.println(fileName + " downloaded successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("new thread started...");
        System.out.println("thread name: " + Thread.currentThread().getName());
        getConnection();
    }
}
