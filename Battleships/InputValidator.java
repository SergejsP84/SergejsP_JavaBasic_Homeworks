package lesson5.Battleships;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputValidator {


    public boolean isValidInput(String input) {
        List<Character> validLetterRange = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J');
        List<Character> validNumberRange = Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', '0');
        if (input.length() == 2) {
            char firstChar = Character.toUpperCase(input.charAt(0));
            char secondChar = input.charAt(1);
            if (validLetterRange.contains(firstChar) && validNumberRange.contains(secondChar)) {
                return true;
            } else if (validNumberRange.contains(firstChar) && validLetterRange.contains(secondChar)) {
                return true;
            }
        }
        else if (input.length() == 3) {
            char firstChar = Character.toUpperCase(input.charAt(0));
            char secondChar = input.charAt(1);
            char thirdChar = input.charAt(2);
            if (validLetterRange.contains(firstChar) && secondChar == ('1') && thirdChar == ('0')) return true;
        }
        return false;
    }
}