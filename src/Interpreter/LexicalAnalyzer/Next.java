package Interpreter.LexicalAnalyzer;

import java.io.IOException;

import static Interpreter.LexicalAnalyzer.GlobalVariables.*;
import static Interpreter.LexicalAnalyzer.LexicalHelperFunctions.*;
import static Interpreter.LexicalAnalyzer.Printer.printer;
import static Interpreter.LexicalAnalyzer.Reader.sb;
import static Interpreter.LexicalAnalyzer.ThreeMainFunctions.*;
import static Interpreter.SyntaxAnalyzer.SyntaxHelperFunctions.programHelper;
import static Interpreter.SyntaxAnalyzer.SyntaxHelperFunctions.stringToChar;
import static Interpreter.SyntaxAnalyzer.ThreeMainFunctions.program;

public class Next {
    static int j = 0;
    public static String munchedWord = "";
    public static String identifier = "";
    public static String colon = "";
    public static String end = "";
    public static boolean getColon = false;
    public static boolean getIdentifier = false;
    public static boolean getProgram = false;

    //Program will have to call on the next method, ONCE to begin and then ONCE after each successful match method or body method.
    //Will have to stop next from going off on its own.
    // Make sure to uncomment the printer methods because you still need to make sure the file is lexically valid
    //Gets next lexeme
    public static void next() throws IOException {
        //On chance an empty array is passed we return
        if (stringToChar(sb).length == 0) {
            return;
        }
        char charToMunch = stringToChar(sb)[j];

        //Used in the ThreeMainFunctions file to find the next char
        TokenInfo nextChar = new TokenInfo(stringToChar(sb), j);

        munchedWord = maxMunch(charToMunch, currentLine);
        if (munchedWord != null) {
            //Prints characters attached and before an unaccepted symbol
            if (wrongInput) {
                printer(currentLine, munchedWord, kind(munchedWord), value(munchedWord));
                programHelper(munchedWord);
                System.out.println("\nIllegal character at " + position(currentLine, currentCharInLine) + ". Character is '" + charToMunch + "'.\nExiting program...");
                System.exit(0);
            }

            printer(currentLine, munchedWord, kind(munchedWord), value(munchedWord));
            programHelper(munchedWord);
            stringReset();

            if (symbolNext) {
                munchedWord = String.valueOf(TokenInfo.currentChar);
                printer(currentLine, munchedWord, kind(munchedWord), value(munchedWord));
                programHelper(munchedWord);
                stringReset();
            }
        }
        j++;
        while (!TokenInfo.currentKeyword.equals("end-of-text")  && j <= stringToChar(sb).length - 1) {
            if (munchedWord != null) {
                program();
            }
            next();
        }
    }
}