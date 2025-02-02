package org.lucas.ui.framework;

import java.util.Scanner;

public class Terminal {
    Scanner scanner = new Scanner(System.in);
    // In the real implementation, use ANSI escape codes:
    // Example: "\u001b[31m" for red text, "\u001b[0m" to reset
    public void setTextColor(Color color) {
        System.out.print(color.getAnsiCode());
    }

    public void resetColor() {
        System.out.print("\u001b[0m"); // ANSI code to reset
    }

    public void clearScreen() {
        System.out.print("\u001b[2J"); // ANSI code to clear screen
        System.out.print("\u001b[H"); // Move cursor to top-left
    }

    public void setCursorPosition(int x, int y) {
        System.out.printf("\u001b[%d;%dH", y + 1, x + 1); // ANSI code to move cursor
    }

    public void write(String text) {
        System.out.print(text);
    }

    public void writeln(String text) {
        System.out.println(text);
    }

    public void flush(){
        System.out.flush();
    }

    public String getUserInput() {
        scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
