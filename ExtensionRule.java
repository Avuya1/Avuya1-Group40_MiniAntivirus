import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ExtensionRule implements Rule{
    
    private List<String> badExtensions = Arrays.asList(
            "lnk", "bat", "vbs", "scr", "ps1", "jar", "msi"
    );

    @Override
    public boolean isSuspicious(File file) {
        String name = file.getName().toLowerCase();

        int lastDot = name.lastIndexOf('.');

        if (lastDot > 0) {
            String extension = name.substring(lastDot + 1);
            return badExtensions.contains(extension);
        }

        return false;
    }

    @Override
    public String getReason() {
        return "Suspicious file extension";
    }
    
}
