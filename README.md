# Group 40: Mini Antivirus

## 📌 Project Overview
The **Mini Antivirus** is a lightweight, console-based Java application designed to scan directories for potentially malicious files. Unlike heavy commercial antivirus software, this tool uses a modular, rule-based detection approach combining heuristic scanning (extensions, filenames, sizes) and signature-based detection (SHA-256 hashing). 

This project was developed as part of the Software Design module to demonstrate Object-Oriented Programming (OOP), recursion, file I/O, cryptographic hashing, and team collaboration via GitHub.

## ✨ Core Features
* **Recursive Folder Scanning:** Walks through the main folder and all nested subfolders to ensure no file is missed.
* **Rule-Based Detection:** Uses an interface (`Rule`) to apply multiple independent detection strategies:
  * `ExtensionRule`: Flags dangerous file types (e.g., `.exe`, `.bat`, `.vbs`).
  * `KeywordRule`: Flags filenames containing suspicious words (e.g., `keygen`, `virus`, `hack`).
  * `SizeRule`: Flags anomalous files (e.g., 0 bytes or unusually large files).
  * `HashRule`: Computes the SHA-256 hash of a file and compares it against a blacklist of known malicious signatures (e.g., the EICAR test file).
* **Activity Logging:**  Records scan results and flagged files.
* **Quarantine System:** Safely moves flagged files to an isolated folder.

## 🛠️ System Requirements
To run this application, you need:
* **Java Development Kit (JDK):** Version 8 or higher (JDK 17 recommended).
* **IDE:** Apache NetBeans or Visual Studio Code.
* **Operating System:** Windows, macOS, or Linux.

## 🚀 How to Install and Run

1. **Clone the Repository:**
   Open your terminal or IDE and clone the project:
   ```bash
   git clone https://github.com/Avuya1/Group40_MiniAntivirus.git
