import com.google.gson.Gson;
import json.CheckidayAPI;
import json.Holidays;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

/**
 * Created by jorda on 5/15/2016.
 */
public class Main {
    private static OkHttpClient client = new OkHttpClient();

    private Main()
    {
        // no instances
    }

    public static void main(String[] args) throws TwitterException {

        // If no internet connection available, end the program
        if (!netIsAvailable()) {
            System.out.println("No internet available. Program ending.");
            return;
        }

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                // The next four statements accept tokens and keys provided by Twitter's API.
                // Recommended to be read in from a configuration file, instead of hard code.
                .setOAuthConsumerKey("blahblah_fillme")
                .setOAuthConsumerSecret("blahblah_fillme")
                .setOAuthAccessToken("blahblah_fillme")
                .setOAuthAccessTokenSecret("blahblah_fillme");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        // processes the list of Holidays taken from the Checkiday API,
        // and then stores it into a StatusUpdate object for Twitte4J to use.
        twitter.updateStatus(processHolidayList());

    }


    static String getJSON(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    // Retrieve array of Holidays by specified day's date
    // Date examples: "today", "yesterday", "tomorrow", "5/26/2016"
    static Holidays[] getHolidayData(String date) {
        String json = null;

        try {
            json = getJSON("http://www.checkiday.com/api/3/?d=" + date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        CheckidayAPI checkidayAPI = gson.fromJson(json, CheckidayAPI.class); // gets all jsons and puts into variables

        return checkidayAPI.getHolidays();


    }

    static StatusUpdate processHolidayList() {

        Holidays[] holidayData = getHolidayData("today");
        int numberOfHolidays = getHolidayData("today").length;

        // Create array to store sorted holidays by string length
        String[] todaysHolidayNamesArray = new String[numberOfHolidays];

        for (int i = 0; i < numberOfHolidays; i++) {
            todaysHolidayNamesArray[i] = holidayData[i].getName();
        }

        // Sort holidays by string length
        Arrays.sort(todaysHolidayNamesArray, new LengthComparator());

//----------------------------------------------------------------------//

// Tweet Status Character Limit = 140
// Tweet Status Character Limit with attached picture (media) = 116, occupies 24
//lastStatus.media(new File("C:\\Users\\jorda\\IdeaProjects\\NHAlert\\src\\main\\resources\\happy-holidays.jpg"));


        // TODO: Add a Media enable/disable switcher... Media currently disabled!

        String tempStatusStr;
        String headStatusStr = "Today's top Holidays:\n\n";
        String footStatusStr = "\n\"All your holidays, updated daily at 9:00 AM.\"";
        int footStatusStrLen = footStatusStr.length();
        int tweetedHolidayLimit = 0;

        for (String holidayName : todaysHolidayNamesArray) {

            tempStatusStr = "-" + holidayName + "\n";

            if ((headStatusStr.length() + tempStatusStr.length() + footStatusStrLen) > 140)
                break;

            headStatusStr += "-" + holidayName + "\n";
            tweetedHolidayLimit++;
        }

        headStatusStr += footStatusStr;

        // For Testing Char length in a Tweet
        //System.out.println(headStatusStr);
        //System.out.println("\nChar length of whole Tweet: " + headStatusStr.length());
        //System.out.println("# of Holidays tweeted: " + tweetedHolidayLimit);

        StatusUpdate result = new StatusUpdate(headStatusStr);

        return result;
    }


    static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

}
