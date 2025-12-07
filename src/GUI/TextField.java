package GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TextField extends Component {

    private final List<StringBuilder> lines = new ArrayList<>();
    private boolean focused = false;

    private int cursorLine = 0;
    private int cursorCol = 0;

    public TextField(int x, int y, int width, int height) {
        super(x, y, width, height);
        lines.add(new StringBuilder());
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.WHITE);
        g.fillRect(xPos, yPos, width, height);

        g.setColor(Color.BLACK);
        g.drawRect(xPos, yPos, width, height);

        FontMetrics fm = g.getFontMetrics();
        int lineHeight = fm.getHeight();

        // final list of display lines after wrapping
        List<String> displayLines = new ArrayList<>();

        // mapping info for cursor
        int cursorDisplayLine = 0;
        int cursorDisplayColX = 0;

        for (int i = 0; i < lines.size(); i++) {

            String line = lines.get(i).toString();
            List<String> wrapped = wrapLine(line, fm, width - 10);

            for (int w = 0; w < wrapped.size(); w++) {
                String piece = wrapped.get(w);

                // Map logical cursor → visual cursor position
                if (i == cursorLine) {
                    // chars before this wrapped piece
                    int prevChars = 0;
                    for (int k = 0; k < w; k++)
                        prevChars += wrapped.get(k).length();

                    if (cursorCol >= prevChars && cursorCol <= prevChars + piece.length()) {
                        cursorDisplayLine = displayLines.size();
                        cursorDisplayColX = fm.stringWidth(piece.substring(0, cursorCol - prevChars));
                    }
                }

                displayLines.add(piece);
            }
        }

        // draw wrapped lines
        for (int i = 0; i < displayLines.size(); i++) {
            int drawY = yPos + (i + 1) * lineHeight;
            if (drawY > yPos + height - 4) break;
            g.drawString(displayLines.get(i), xPos + 4, drawY - 4);
        }

        // draw cursor
        if (focused) {
            int cursorX = xPos + 4 + cursorDisplayColX;
            int cursorY1 = yPos + cursorDisplayLine * lineHeight + 4;
            int cursorY2 = cursorY1 + lineHeight - 8;
            g.drawLine(cursorX, cursorY1, cursorX, cursorY2);
        }
    }

    private List<String> wrapLine(String line, FontMetrics fm, int maxWidth) {
        List<String> out = new ArrayList<>();

        if (line.isEmpty()) {
            out.add("");
            return out;
        }

        String[] words = line.split(" ");
        StringBuilder current = new StringBuilder();

        for (String word : words) {

            // If adding this word exceeds width → push current and reset
            String tryLine = current.length() == 0 ? word : current + " " + word;
            if (fm.stringWidth(tryLine) <= maxWidth) {
                if (current.length() > 0) current.append(" ");
                current.append(word);
                continue;
            }

            // If the word itself is too long → hard-wrap the word
            if (fm.stringWidth(word) > maxWidth) {
                // flush current line if not empty
                if (current.length() > 0) {
                    out.add(current.toString());
                    current.setLength(0);
                }

                // hard wrap long word
                StringBuilder longWord = new StringBuilder(word);
                while (longWord.length() > 0) {
                    int cut = computeMaxFittingChars(longWord, fm, maxWidth);

                    out.add(longWord.substring(0, cut));
                    longWord.delete(0, cut);
                }

                continue; // processed this word entirely
            }

            // Word fits normally but current line is full → wrap line
            out.add(current.toString());
            current.setLength(0);
            current.append(word);
        }

        if (current.length() > 0)
            out.add(current.toString());

        return out;
    }

    /**
     * Returns how many characters from sb fit into maxWidth.
     */
    private int computeMaxFittingChars(CharSequence sb, FontMetrics fm, int maxWidth) {
        int lo = 0;
        int hi = sb.length();

        // binary search for fastest performance
        while (lo < hi) {
            int mid = (lo + hi + 1) / 2;
            String sub = sb.subSequence(0, mid).toString();

            if (fm.stringWidth(sub) <= maxWidth) lo = mid;
            else hi = mid - 1;
        }
        return Math.max(1, lo);
    }

    @Override
    public void onMouseClick(int mx, int my) {
        focused = contains(mx, my);
    }

    @Override
    public void onKeyTyped(char c) {
        if (!focused) return;

        if (c == '\b') { handleBackspace(); return; }
        if (c == '\n' || c == '\r') { insertNewLine(); return; }

        insertCharacter(c);
    }

    private void insertCharacter(char c) {
        lines.get(cursorLine).insert(cursorCol, c);
        cursorCol++;
    }

    private void insertNewLine() {
        StringBuilder current = lines.get(cursorLine);
        String remaining = current.substring(cursorCol);

        current.delete(cursorCol, current.length());
        lines.add(cursorLine + 1, new StringBuilder(remaining));

        cursorLine++;
        cursorCol = 0;
    }

    private void handleBackspace() {
        if (cursorCol > 0) {
            lines.get(cursorLine).deleteCharAt(cursorCol - 1);
            cursorCol--;
        } else if (cursorLine > 0) {
            StringBuilder prev = lines.get(cursorLine - 1);
            int prevLen = prev.length();
            prev.append(lines.get(cursorLine));
            lines.remove(cursorLine);
            cursorLine--;
            cursorCol = prevLen;
        }
    }

    public String getText() {
        return String.join("\n", lines);
    }

    public void setText(String text) {
        // Clear current content
        lines.clear();

        // Split input text into logical lines
        String[] split = text.split("\n", -1);

        for (String s : split) {
            lines.add(new StringBuilder(s));
        }

        // Reset cursor to end of last line
        cursorLine = Math.max(0, lines.size() - 1);
        cursorCol = lines.get(cursorLine).length();
    }
}