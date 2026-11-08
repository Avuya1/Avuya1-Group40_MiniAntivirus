/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mzwakhe Mavimbela
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActivityLogger {
    
    public void saveLog(String message) {

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String logEntry = "[" + time + "] " + message;

        try {
            PrintWriter writer = new PrintWriter(new FileWriter("activity_log.txt", true));

            writer.println(logEntry);
            writer.close();

        } catch (IOException e) {
            System.out.println("Could not save activity log.");
        }
    }
}
