/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mzwakhe Mavimbela
 */

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class CommandLineInterpreter {
    
    private ScanningClass scannerService = new ScanningClass();
    private QuarantineFiles quarantineService = new QuarantineFiles();
    private ActivityLogger activityLog = new ActivityLogger();

    private File selectedFolder = null;
    private File quarantineFolder = null;

    private List<File> suspiciousFiles;

    public void start() {

        Scanner input = new Scanner(System.in);
        int choice = 0;

        while (choice != 3) {

            System.out.println("\n==============================");
            System.out.println("       SECUREBYTE CLI MODE");
            System.out.println("==============================");

            System.out.println("1. Scan Folder");
            System.out.println("2. Quarantine Suspicious Files");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            if (!input.hasNextInt()) {
                System.out.println("Please enter a number.");
                input.nextLine();
                continue;
            }

            choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {

                System.out.print("Enter folder path: ");
                String folderPath = input.nextLine();

                selectedFolder = new File(folderPath);

                if (selectedFolder.exists()
                        && selectedFolder.isDirectory()) {

                    int totalFiles =
                            scannerService.countFiles(selectedFolder);

                    suspiciousFiles =
                            scannerService.findSuspiciousFiles(selectedFolder);

                    System.out.println("\nSCAN RESULTS");
                    System.out.println("------------------------------");
                    System.out.println("Folder: "
                            + selectedFolder.getAbsolutePath());
                    System.out.println("Files scanned: " + totalFiles);
                    System.out.println("Suspicious files found: "
                            + suspiciousFiles.size());

                    for (File file : suspiciousFiles) {

                        String reason = scannerService.checkFile(file);

                        System.out.println("\nFile: " + file.getName());
                        System.out.println("Path: "
                                + file.getAbsolutePath());
                        System.out.println("Reason: " + reason);
                    }

                    System.out.println("\nScan completed.");

                    activityLog.saveLog("CLI SCAN OPERATION");
                    activityLog.saveLog("Folder: "
                            + selectedFolder.getAbsolutePath());
                    activityLog.saveLog("Files scanned: " + totalFiles);
                    activityLog.saveLog("Suspicious files found: "
                            + suspiciousFiles.size());
                    activityLog.saveLog("Status: Completed");

                } else {

                    System.out.println("Invalid folder path.");

                    activityLog.saveLog(
                            "CLI scan failed: Invalid folder path.");

                    selectedFolder = null;
                    suspiciousFiles = null;
                }

            } else if (choice == 2) {

                if (selectedFolder == null || suspiciousFiles == null) {

                    System.out.println("Please scan a folder first.");

                } else if (suspiciousFiles.isEmpty()) {

                    System.out.println(
                            "No suspicious files to quarantine.");

                } else {

                    System.out.print(
                            "Are you sure you want to quarantine these files? (yes/no): ");

                    String answer = input.nextLine();

                    if (answer.equalsIgnoreCase("yes")) {

                        quarantineFolder = new File(
                                selectedFolder.getAbsoluteFile().getParentFile(),
                                "SecureByte_Quarantine");

                        if (!quarantineFolder.exists()) {
                            quarantineFolder.mkdirs();
                        }

                        int moved = quarantineService.quarantineFiles(
                                selectedFolder,
                                quarantineFolder,
                                suspiciousFiles);

                        System.out.println(moved
                                + " file(s) moved to quarantine.");

                        activityLog.saveLog(
                                "CLI QUARANTINE OPERATION");

                        activityLog.saveLog("Original folder: "
                                + selectedFolder.getAbsolutePath());

                        activityLog.saveLog("Files moved: " + moved);

                        activityLog.saveLog("Quarantine folder: "
                                + quarantineFolder.getAbsolutePath());

                        activityLog.saveLog("Status: Completed");

                        suspiciousFiles =
                                scannerService.findSuspiciousFiles(
                                        selectedFolder);

                    } else {

                        System.out.println("Quarantine cancelled.");

                        activityLog.saveLog(
                                "CLI quarantine operation cancelled.");
                    }
                }

            } else if (choice == 3) {

                System.out.println("Exiting SecureByte CLI...");

            } else {

                System.out.println("Invalid option. Try again.");
            }
        }
    }
    
}
 