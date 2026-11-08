

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;

public class QuarantineFiles {
    
    
    // Move suspicious files into quarantine
    public int quarantineFiles(File selectedFolder,File quarantineFolder, List<File> suspiciousFiles) {
        
        int movedCount = 0;
        for (File file : suspiciousFiles) {
            
            try {
                Path relativePath = selectedFolder.toPath().relativize(file.toPath());

                Path destination = quarantineFolder.toPath().resolve(relativePath);

                Files.createDirectories(destination.getParent());

                if (!Files.exists(destination)) {
                    
                    Files.move(file.toPath(), destination);
                    movedCount++;
                }

            } catch (IOException e) {
                System.out.println("Could not quarantine: "+ file.getName());
            }
        }
        
        return movedCount;
    }

    // Remove files from quarantine
    public int removeFiles(File quarantineFolder) {
        
        int removedCount = 0;
        File[] files = quarantineFolder.listFiles();
        
        if (files == null) {
            return 0;
        }

        for (File file : files) {

            if (deleteFile(file)) {
                removedCount++;
            }
        }

        return removedCount;
    }

    // Delete files and folders
    private boolean deleteFile(File file) {

        if (file.isDirectory()) {

            File[] children = file.listFiles();

            if (children == null) {
                return false;
            }

            for (File child : children) {

                if (!deleteFile(child)) {
                    return false;
                }
            }
        }

       return file.delete();
    }
    
    //add items to Quarantined Files List
    public List<File> getQuarantinedFiles(File quarantineFolder) {
        
        List<File> filesList = new ArrayList<>();
        
        //Prevent error by checking if Quarantined files exist
        if (quarantineFolder == null || !quarantineFolder.exists()) {
            return filesList;
        }

        addFilesToList(quarantineFolder, filesList);
        return filesList;
    }

    
    private void addFilesToList(File folder, List<File> filesList) {
        File[] files = folder.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {

            if (file.isDirectory()) {
                addFilesToList(file, filesList);
            } else {
                filesList.add(file);
            }
        }
    }
    
    // Restore a quarantined file
public boolean restoreFile(File originalFolder,
        File quarantineFolder, File quarantinedFile) {

    try {

        // Get the file's original location
        Path relativePath = quarantineFolder.toPath()
                .relativize(quarantinedFile.toPath());

        Path destination = originalFolder.toPath()
                .resolve(relativePath);

        // Create folders if they do not exist
        Files.createDirectories(destination.getParent());

        // Do not overwrite an existing file
        if (Files.exists(destination)) {
            return false;
        }

        // Move the file back
        Files.move(quarantinedFile.toPath(), destination);

        return true;

    } catch (IOException e) {

        System.out.println("Could not restore: "
                + quarantinedFile.getName());

        return false;
    }
}
}

