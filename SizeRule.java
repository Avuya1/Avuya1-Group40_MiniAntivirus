import java.io.File;

public class SizeRule implements Rule {
    
    @Override
    public boolean isSuspicious(File file) {
        return file.length() == 0 ||
               file.length() > 100 * 1024 * 1024;
    }

    @Override
    public String getReason() {
        return "Unusual file size";
    }
    
    
}
