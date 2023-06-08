package SP_Homework_2_by_May_23;

public class CharConversion {
    public static void main(String[] args) {
        char myInitialChar = '4';
        // Method No. 1 - conversion into an integer using String.valueOf
        String auxilString = String.valueOf(myInitialChar);
        int result = Integer.parseInt(auxilString);
        System.out.println("Method 1 output: " + result + " + 6 = "+ (result+6));

        System.out.println();

        // Method No. 2 - conversion into a byte using Character.getNumericValue
        byte result2 = (byte) Character.getNumericValue(myInitialChar);
        System.out.println("Method 2 output: " + result2 + " + 6 = "+ (result+6));
    }
}
