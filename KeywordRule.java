import java.io.File;
import java.util.Arrays;
import java.util.List;

public class KeywordRule implements Rule {
    
     private List<String> badWords = Arrays.asList(
            "hack", "crack", "keygen", "trojan", "virus", "free_money"
    );

    @Override
    public boolean isSuspicious(File file) {
        String name = file.getName().toLowerCase();

        for (String word : badWords) {
            if (name.contains(word)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String getReason() {
        return "Suspicious keyword in filename";
        
        
    }
    
    
    
}
