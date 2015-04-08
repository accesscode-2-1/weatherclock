package nyc.c4q.ac21.weatherclock;

import java.io.*;

/**
 * Wrapper for a terminal that understands ANSI escape codes.
 *
 * See http://en.wikipedia.org/wiki/ANSI_escape_code .
 */
public class AnsiTerminal {

    private static final byte ESCAPE = 0x1b;

    private final FileOutputStream out;

    private void write(byte b) {
        try {
            out.write(b);
        } catch (IOException e) {
            // Ignore.
        }
    }

    private void csi(String code) {
        write(ESCAPE);
        write((byte) '[');
        write(code);
    }

    private void csi(int arg, char code) {
        csi(Integer.toString(arg) + code);
    }

    private void csi(int arg0, int arg1, char code) {
        csi(Integer.toString(arg0) + ';' + arg1 + code);
    }

    private void csi(int arg0, int arg1, int arg2, char code) {
        csi(Integer.toString(arg0) + ';' + arg1 + ';' + arg2 + code);
    }

    private void sgr(int code) {
        assert 0 <= code && code < 107;
        csi(code, 'm');
    }

    /**
     * Basic colors available for drawing.
     *
     * Note that there are two shades of each: 'intense' (bright) and normal.
     */
    public static enum Color {
        BLACK,
        RED,
        GREEN,
        YELLOW,
        BLUE,
        MAGENTA,
        CYAN,
        WHITE;

        public int getCode(boolean foreground, boolean intense) {
            return (foreground ? (intense ? 90 : 30) : (intense ? 100 : 40)) + ordinal();
        }
    }

    public AnsiTerminal() {
        FileOutputStream out;
        try {
            out = new FileOutputStream(new File("/dev/tty"));
        } catch (FileNotFoundException e) {
            // Fall back to stdout.
            out = new FileOutputStream(java.io.FileDescriptor.out);
        }
        this.out = out;
    }

    /**
     * Writes text to the terminal.
     *
     * Uses the current cursor location, text color, and background color.
     *
     * @param string
     *   The text to write.
     */
    public void write(String string) {
        try {
            out.write(string.getBytes());
        }
        catch (IOException e) {
            // Ignore.
        }
    }

    /**
     * Resets the terminal.
     */
    public void reset() {
        sgr(0);
    }

    /**
     * Clears the screen.
     */
    public void clear() {
        csi(2, 'J');
    }

    /**
     * Sets the color for drawing text.
     * @param color
     *   The color to use.
     * @param intense
     *   If true, use an intense color.
     */
    public void setTextColor(Color color, boolean intense) {
        sgr(color.getCode(true, intense));
    }

    /**
     * Sets the text color.  Intense color is used.
     * @param color
     *   The color to use.
     */
    public void setTextColor(Color color) {
        setTextColor(color, true);
    }

    /**
     * Sets the text color to an extended color code.
     * @param colorCode
     *   The extended color code, between 0 and 255.
     */
    public void setTextColor(int colorCode) {
        assert 0 <= colorCode && colorCode < 256;
        csi(38, 5, colorCode, 'm');
    }

    /**
     * Sets the background color.
     * @param color
     *   The color to use.
     * @param intense
     *   If true, use an intense color.
     */
    public void setBackgroundColor(Color color, boolean intense) {
        sgr(color.getCode(false, intense));
    }

    /**
     * Sets the background color.
     * @param color
     *   The color to use.
     */
    public void setBackgroundColor(Color color) {
        setBackgroundColor(color, false);
    }

    /**
     * Sets the background color to an extended color code.
     * @param colorCode
     *   The extended color code, between 0 and 255.
     */
    public void setBackgroundColor(int colorCode) {
        assert 0 <= colorCode && colorCode < 256;
        csi(48, 5, colorCode, 'm');
    }

    /**
     * Moves the cursor to the specified location.
     * @param line
     *   The line number.  Lines are numbered from 1 starting at the top line and increasing downward.
     * @param col
     *   The column number.  Columns are numbered from 1 starting at the left column and increasing rightward.
     */
    public void moveTo(int line, int col) {
        csi(line, col, 'H');
    }

    /**
     * Hides the cursor.
     */
    public void hideCursor() {
        csi("?25l");
    }

    /**
     * Shows the cursor.
     */
    public void showCursor() {
        csi("?25h");
    }

    /**
     * Scrolls the screen.
     * @param numLines
     *   The number of lines to scroll.  Positive numbers indicate scrolling downward.
     */
    public void scroll(int numLines) {
        csi(numLines, 'S');
    }

}
