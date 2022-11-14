package Interpreter.SyntaxAnalyzer;

import java.io.IOException;

import static Interpreter.LexicalAnalyzer.Driver.main;
import static Interpreter.LexicalAnalyzer.LexicalHelperFunctions.reset;
import static Interpreter.LexicalAnalyzer.GlobalVariables.currentCharInLine;
import static Interpreter.LexicalAnalyzer.GlobalVariables.currentLine;
import static Interpreter.LexicalAnalyzer.LexicalHelperFunctions.position;
import static Interpreter.LexicalAnalyzer.Next.*;

public class SyntaxHelperFunctions {

    public static String copyMunch = "";
    static String[] reservedForMatch = {"program", "",":", "end"};
    public static boolean noColon = false;

    static String[] reservedForBody = {"bool", "identifier", "int", "bool", "int", ";", ":=", "if", "then", "else", "fi",
            "while", "do", "od", "print", "<", "=<", "=", "!=", ">=", ">", "+", "or", "*", "/", "(", ")","and", "not",
            "false", "true", "-","_", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"
            , "l", "m", "n", "o", "p", "q", "u", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    public static void SyntaxError (String symbol) throws IOException {
        if (noColon) {
            System.out.println("\nSyntax error at " + position(currentLine, currentCharInLine) + ". Expected ':' after '" + symbol
                    + "'.\nResetting program\n.........\n");
        }
        if (!getEnd) {
            System.out.println("\nSyntax error at " + position(currentLine, currentCharInLine) + ". Expected 'end' symbol at the end of the file, got '" + symbol
                    + "'.\nResetting program\n.........\n");
        }
        else {
            System.out.println("\nBad symbol '" + symbol + "' at " + position(currentLine, currentCharInLine) + " expected '" + symbol + "' CHANGE THIS TO ACTUAL EXPECTED RESULT"
            + "\nResetting program\n.........\n");
        }
        noColon = false;
        reset();
        main(new String[0]);
    }

    public static void programHelper (String munchedWord) throws IOException {
        if (munchedWord.equalsIgnoreCase("program")) {
            identifier = munchedWord.toLowerCase();
            getProgram = true;
            return;
        }

        if (identifier.equals("program") && !munchedWord.equalsIgnoreCase("program")) {
            if (charToMunch == ':') {
                getIdentifier = true;
                for (int i = 0; i < munchedWord.length(); i++) {
                    copyMunch += munchedWord.charAt(i);
                }
                reservedForMatch[1] = copyMunch;
                identifier = copyMunch;
                return;
            }
            else {
                noColon = true;
                SyntaxError(munchedWord);
            }
        }

        if (munchedWord.equals(":")) {
            getColon = true;
            return;
        }
        if (munchedWord.equalsIgnoreCase("end")) {
            getBody = false;
            getEnd = true;
        }
    }

    public static Boolean matchHelper (String symbol) {
        for (String s : reservedForMatch) {
            if (symbol.equals(s)){
                return true;
            }
        }
        return false;
    }

    public static Boolean bodyHelper (String symbol) {
        if (symbol != null && !symbol.isEmpty() && !symbol.isBlank()&& !symbol.equals(" ") && !munchedWord.contains("\t")) {
            for (String s : reservedForBody) {
                if (symbol.toLowerCase().equals(s) || s.contains(symbol.toLowerCase())) {
                    System.out.println("Found match for " + symbol);
                    return true;
                }
            }
            System.out.println("Failed to match " + symbol);
        }
        return false;
    }

    public static char[] stringToChar (StringBuilder sb) {
        return sb.toString().toCharArray();
    }
}
