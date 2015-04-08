package nyc.c4q.ac21.weatherclock;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.net.URL;
import java.util.Calendar;

public class Main {

    /**
     * SAMPLE CODE: Returns sunset time for the current day.
     */
    public static Calendar getSunset() {
        URL url = HTTP.stringToURL("http://api.openweathermap.org/data/2.5/weather?q=New%20York,NY");
        String doc = HTTP.get(url);
        JSONObject obj = (JSONObject) JSONValue.parse(doc);

        JSONObject sys = (JSONObject) obj.get("sys");
        if (sys == null)
            return null;
        Long sunsetTimestamp = (Long) sys.get("sunset");
        if (sunsetTimestamp == null)
            return null;
        return DateTime.fromTimestamp(sunsetTimestamp);
    }
    /**
     * Weather description by Hoshiko
     */
    public static String getWeatherDesc() {
        //URL url = HTTP.stringToURL("http://api.openweathermap.org/data/2.5/weather?q=New%20York,NY");
        URL url = HTTP.stringToURL("http://api.openweathermap.org/data/2.5/weather?id=5128581");
        String doc = HTTP.get(url);
        JSONObject obj = (JSONObject) JSONValue.parse(doc);
        JSONArray weatherJSONArray = (JSONArray)obj.get("weather");
        JSONObject weatherJSONObj = (JSONObject) weatherJSONArray.get(0);

        if (weatherJSONObj == null)
            return null;

        String description = (String)weatherJSONObj.get("description");
        if (description == null)
            return null;
        return description;
    }

    /**
     * SAMPLE CODE: Displays a very primitive clock.
     */
    public static void main(String[] args) {
        // Find out the size of the terminal currently.
        final int numCols = TerminalSize.getNumColumns();
        final int numRows = TerminalSize.getNumLines();

        // Create the terminal.
        final AnsiTerminal terminal = new AnsiTerminal();

        // When the program shuts down, reset the terminal to its original state.
        // This code makes sure the terminal is reset even if you kill your
        // program by pressing Control-C.
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                terminal.showCursor();
                terminal.reset();
                terminal.scroll(1);
                terminal.moveTo(numRows, 0);
            }
        });

        // Clear the screen to black.
        terminal.setBackgroundColor(AnsiTerminal.Color.BLACK);
        terminal.clear();
        // Don't show the cursor.
        terminal.hideCursor();

        // Get sunset time for the current day.
        Calendar sunset = getSunset();

        int xPosition = 1 + numCols / 2 - 5;
        while (true) {
            // Get the current date and time.
            Calendar cal = Calendar.getInstance();

            // Write the time, including seconds, in white.
            String time = DateTime.formatTime(cal, true);
            if (cal.get(Calendar.HOUR_OF_DAY) >= 12)
                time += " PM";
            else
                time += " AM";
            terminal.setTextColor(AnsiTerminal.Color.WHITE);
            terminal.moveTo(3, xPosition);
            terminal.write(time);

            // Write the date in gray.
//                String date = DateTime.formatDate(cal);
            //                terminal.setTextColor(AnsiTerminal.Color.WHITE, false);
            //                terminal.moveTo(5, xPosition);
            //                terminal.write(date);

            // Write the day of the week in green on a blue background.
            String dayOfWeek = DateTime.getDayOfWeekNames().get(cal.get(Calendar.DAY_OF_WEEK));
            terminal.setTextColor(AnsiTerminal.Color.GREEN);
            terminal.setBackgroundColor(AnsiTerminal.Color.BLUE);
            terminal.moveTo(7, xPosition-4);
            //
            String date = DateTime.formatDate(cal);
            terminal.write(dayOfWeek+", "+date);

            // Set the background color back to black.
            terminal.setBackgroundColor(AnsiTerminal.Color.BLACK);

            // Write sunset time in dark yellow.
            String sunsetTime = DateTime.formatTime(sunset, false);
            terminal.setTextColor(AnsiTerminal.Color.YELLOW, false);
            terminal.moveTo(9, xPosition - 2);
            terminal.write("sunset at " + sunsetTime);

            // Pause for one second, and do it again.
            DateTime.pause(1.0);


            //Hoshiko weather description

            terminal.setTextColor(AnsiTerminal.Color.WHITE, false);
            terminal.moveTo(11, xPosition - 2);
            terminal.write(getWeatherDesc());



        }
    }
}
