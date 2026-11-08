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
import java.util.Arrays;


public class ScanningClass {
    
    // Checks a file using the three detection rules
    public String checkFile(File file) {

        Rule[] rules = {
            new ExtensionRule(),
            new KeywordRule(),
            new SizeRule()
        };

        for (Rule rule : rules) {
            if (rule.isSuspicious(file)) {
                return rule.getReason();
            }
        }

        return "Safe";
}
    
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

        String[] malicious = antivirus.main(folder.getAbsolutePath());

        for (File file : files) {

            if (file.isDirectory()) {
                suspiciousFiles.addAll(findSuspiciousFiles(file));
            } else {

                String result = checkFile(file);

                if (!result.equals("Safe")) {
                    suspiciousFiles.add(file);
                }
            }
        }

        for (String name : malicious) {
            if (name == null) {
                continue;
            }
        
            File file = new File(folder, name);
            if (file.isFile() && !suspiciousFiles.contains(file)) {
                suspiciousFiles.add(file);
            }
        }

        return suspiciousFiles;
}
    
}
