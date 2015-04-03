package nyc.c4q.ac21.weatherclock;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Code for determining the terminal size.
 */
public class TerminalSize {

    private static int runTput(String command) {
        try {
            // Make sure tput is connected to the TTY when running.
            Process process = Runtime.getRuntime().exec(new String[]{
                    "bash", "-c", "/usr/bin/tput " + command + " 2> /dev/tty"});
            Scanner scanner = new Scanner(process.getInputStream());
            int result = scanner.nextInt();
            process.waitFor();
            return result;
        } catch (IOException exception) {
            throw new RuntimeException("tput failed");
        } catch (InterruptedException exc) {
            throw new RuntimeException("tput failed");
        } catch (NoSuchElementException exc) {
            throw new RuntimeException("tput failed");
        }

    }

    /**
     * @return
     *   The number of columns in the terminal.
     */
    public static int getNumColumns() {
        try {
            return runTput("cols");
        }
        catch (RuntimeException exc) {
            return 80;
        }
    }

    /**
     * @return
     *   The number of lines (rows) in the terminal.
     */
    public static int getNumLines() {
        try {
            return runTput("lines");
        }
        catch (RuntimeException exc) {
            return 25;
        }
    }

}
