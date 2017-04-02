import java.util.Comparator;

/**
 * Created by jorda on 5/25/2016.
 */
public class LengthComparator implements Comparator<String> {

    public int compare(String arg0, String arg1) {
        // Use Integer.compare to compare the two Strings' lengths.
        return Integer.compare(arg0.length(), arg1.length());
    }

}
