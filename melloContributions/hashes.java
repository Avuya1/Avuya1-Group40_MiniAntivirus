import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class hashes {
    private File file;

    public hashes (String filePath) {
        this.file = new File(filePath);
    }

    public String calculateHash() {
        MessageDigest digest;

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA: ", exception);
        }

        InputStream input = null;
        try {
            input = Files.newInputStream(file.toPath());

            byte[] scanner = new byte[8192];
            int bytesRead;

            while((bytesRead = input.read(scanner)) != -1) {
                digest.update(scanner, 0, bytesRead);
            }
        } catch (IOException exception) {
            System.err.println("file IO exception.");
            System.exit(0);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException exception) {
                    System.err.println("Could not close file.");
                }
            }
        }

        StringBuilder decimal = new StringBuilder();

        for (byte value : digest.digest()) {
            if (decimal.length() > 0) {
                decimal.append(" ");
            }

            //Separate each byte to exactly 8 bits
            decimal.append(Byte.toUnsignedInt(value));
        }

        return decimal.toString();
    }
}
