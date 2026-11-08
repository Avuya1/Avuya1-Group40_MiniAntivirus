import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileInputStream;

public class antivirus {
    public static void main (String[] args){
        if (args.length != 1) {
            System.err.println("Please supply folder name as argument.");
            System.exit(1);
        }
        String folderPath = args[0];


        File folder = new File(folderPath);
        File[] files = null;
        java.util.stream.Stream<Path> paths = null;

        try {
            paths = Files.walk(folder.toPath());
            files = paths.filter(Files::isRegularFile).map(Path::toFile).toArray(File[]::new);
        } catch (Exception exception) {
            System.err.println("Cannot read folder.");
        } finally {
            if (paths != null) {
                paths.close();
            }
        }

        if (!folder.exists()) {
            System.out.println("File does not exist");
        }

        if (files == null) {
            System.err.println("Cannot read folder");
            System.exit(1);
        }
        Boolean isSafe = true;
        isSafe = maliciousHash(files);      


    }

    public static Boolean maliciousHash (File[] files) {
        Path hashListPath = Path.of("knownMaliciousHashes.txt");
        List<String> knownHashes = null;

        Boolean match = false;
        int count = 0;
        int total = 0;

        try {
            knownHashes = Files.readAllLines(Path.of("knownMaliciousHashes.txt"));
        } catch (IOException exception) {
            throw new IllegalStateException("File not readable.");
        }

        for (File file : files) {
            if (!file.isFile()) {
                continue;
            } else {
                total++;
                hashes scanner = new hashes(file.getAbsolutePath());
                String fileHash = scanner.calculateHash();

                for (String knownHash : knownHashes) {
                    if (fileHash.equals(knownHash.trim())) {
                        System.out.println("Malicious file, " + file.getName() + ": " + scanner.calculateHash());
                        match = true;
                        count++;
                        break;
                    }
                }
            }
        }

        System.out.printf("\nFiles scanned: %d", total);
        System.out.printf("\nKnown hash mathces: %d", count);
        
        return match;
    }

    // public static Boolean maliciousHash (File[] files) {
    //     Path hashListPath = Path.of("knownMaliciousHashes.txt");
    //     List<String> knownHashes = null;

    //     Boolean match = false;
    //     int count = 0;
    //     int total = 0;

    //     try {
    //         knownHashes = Files.readAllLines(Path.of("knownMaliciousHashes.txt"));
    //     } catch (IOException exception) {
    //         throw new IllegalStateException("File not readable.");
    //     }

    //     for (File file : files) {
    //         if (!file.isFile()) {
    //             continue;
    //         } else {
    //             hashes scanner = new hashes(file.getAbsolutePath());
    //             String fileHash = scanner.calculateHash();

    //             for (String knownHash : knownHashes) {
    //                 total++;
    //                 if (fileHash.equals(knownHash.trim())) {
    //                     System.out.println("Malicious file, " + file.getName() + ": " + scanner.calculateHash());
    //                     match = true;
    //                     count++;
    //                 }
    //             }
    //         }
    //     }

    //     System.out.printf("\nFiles scanned: %d", total);
    //     System.out.printf("\nKnown hash mathces: %d", count);
        
    //     return match;
    // }
}