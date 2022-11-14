package Interpreter.SyntaxAnalyzer;

import java.io.IOException;

import static Interpreter.LexicalAnalyzer.LexicalHelperFunctions.reset;
import static Interpreter.SyntaxAnalyzer.Driver.main;
import static Interpreter.LexicalAnalyzer.GlobalVariables.currentCharInLine;
import static Interpreter.LexicalAnalyzer.GlobalVariables.currentLine;
import static Interpreter.LexicalAnalyzer.LexicalHelperFunctions.position;
import static Interpreter.LexicalAnalyzer.Next.*;

public class SyntaxHelperFunctions {

    public static String whatTheHell = "";
    static String[] reservedForMatch = {"program", "",":", "end"};

    static String[] reservedForBody = {"bool", "identifier", "int", "end", "bool", "int", ";", ":=", "if", "then", "else", "fi",
            "while", "do", "od", "print", "<", "=<", "=", "!=", ">=", ">", "+", "or", "*", "/", "and", "not",
            "false", "true", "_", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"
            , "l", "m", "n", "o", "p", "q", "u", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    public static void SyntaxError (String symbol) throws IOException {
        System.out.println("\nBad symbol '" + symbol + "' at " + position(currentLine, currentCharInLine) + " expected '" + symbol + "' CHANGE THIS TO ACTUAL EXPECTED RESULT"
        + "\nResetting program\n.........\n");
        reset();
        main(new String[0]);
    }

    public static void programHelper (String munchedWord) {
        if (munchedWord.equalsIgnoreCase("program")) {
            identifier = munchedWord.toLowerCase();
            getProgram = true;
            return;
        }

        if (identifier.equals("program") && !munchedWord.equalsIgnoreCase("program")) {
            getIdentifier = true;
            for (int i = 0; i < munchedWord.length(); i++) {
                whatTheHell += munchedWord.charAt(i);
            }
            reservedForMatch[1] = whatTheHell;
            identifier = whatTheHell;
            return;
        }

        if (munchedWord.equals(":")) {
            getColon = true;
            getBody = true;
            return;
        }
        if (munchedWord.equalsIgnoreCase("end")) {
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
        //Goes straight to false because the MUNCHEDWORD is usually empty
        if (symbol != null && !symbol.isEmpty() && !symbol.isBlank()){
            System.out.println(symbol + "sfadsfafdafdf");
            for (String s : reservedForBody) {
                if (symbol.toLowerCase().equals(s) || s.contains(symbol.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static char[] stringToChar (StringBuilder sb) {
        return sb.toString().toCharArray();
    }
}
