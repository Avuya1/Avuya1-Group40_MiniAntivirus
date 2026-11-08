/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mzwakhe Mavimbela
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ScanningClass {
    
     // Count all files inside the selected folder
    public int countFiles(File folder) {
        
       int count = 0;
       File[] files = folder.listFiles();
        if (files == null) {
            return 0;
        }
        
        for (File file : files) {

            if (file.isDirectory()) {
                count = count + countFiles(file);
            } else if (file.isFile()) {
                count++;
            }
        }
        
        return count;
    }

    // Find files that match our suspicious extension rules
    public List<File> findSuspiciousFiles(File folder) {

        List<File> suspiciousFiles = new ArrayList<>();

        File[] files = folder.listFiles();

        if (files == null) {
            return suspiciousFiles;
        }

        String[] suspiciousExtensions = {".bat", ".cmd",".vbs", ".ps1",".scr", ".exe",".msi",".cpl"};

        for (File file : files) {

            if (file.isDirectory()) {
                suspiciousFiles.addAll(
                    findSuspiciousFiles(file)
                );

            } else {

                String fileName =file.getName().toLowerCase(Locale.ROOT);

                for (String extension : suspiciousExtensions) {
                    if (fileName.endsWith(extension)) {
                        suspiciousFiles.add(file);
                        break;
                    }
                }
            }
        }

        return suspiciousFiles;
    }
    
}
