//  Name: Brandon Gustrowsky
//  Assignment number: 6
//  Assignment: Dynamic Programming - Edit Distance
//  File name: BGustrowsky_2.java
//  Date last modified: November 29, 2022.
//  Honor statement: I have neither given nor received any unauthorized help on this assignment. 

import java.util.Scanner;

public class BGustrowsky_2 {
    public static void main(String[] args) {
        String init = "";
        String result = "";
        Scanner scanner;

        do {
            scanner = new Scanner(System.in);
            init = scanner.nextLine();
            result = scanner.nextLine();
            if (init.length() != 0 && result.length() != 0) { // Inputs are not empty
                if (!(init.contains("+") || init.contains("-") || init.contains("^") || init.contains("/") || result.contains("+") || result.contains("-") || result.contains("^") || result.contains("/"))) {
                    int initLength = init.length();
                    int resultLength = result.length();
                    int[][] distances = new int[initLength + 1][resultLength + 1];

                    // Fill in zeroth row of matrix
                    for (int i = 0; i <= resultLength; i++) {
                        distances[0][i] = i;
                    }
                    // Fill in zeroth column of matrix
                    for (int i = 0; i <= initLength; i++) {
                        distances[i][0] = i;
                    }

                    // Fill in the rest of the matrix. Starting i at 1 and j at 1 because their
                    // zeroth row/column has already been filled out above.
                    for (int i = 1; i <= initLength; i++) {
                        for (int j = 1; j <= resultLength; j++) {
                            if (init.charAt(i - 1) == result.charAt(j - 1)) { // Same characters?
                                distances[i][j] = distances[i - 1][j - 1]; // Assign to the upper left diagonal
                            } else { // Different characters
                                distances[i][j] = Math.min(
                                        Math.min(
                                                distances[i - 1][j],
                                                distances[i][j - 1]),
                                        distances[i - 1][j - 1]) + 1;
                            }
                        }
                    }

                    int initPos = initLength;
                    int resultPos = resultLength;
                    int editDistance = 0;
                    String editString = "";
                    while (resultPos > 0 && initPos > 0) {
                        if (init.charAt(initPos - 1) == result.charAt(resultPos - 1)) { // Are the characters the same?
                            editString = "^" + editString; // Keep the character
                            initPos--;
                            resultPos--;
                        } else {
                            int smallest = Math.min(
                                    Math.min(distances[initPos][resultPos - 1], distances[initPos - 1][resultPos - 1]),
                                    distances[initPos - 1][resultPos]);
                            if (smallest == distances[initPos - 1][resultPos - 1]) { // Is the smallest value diagonally left from the current position?
                                editString = "/" + result.charAt(resultPos - 1) + editString;
                                initPos--;
                                resultPos--;
                            } else if (smallest == distances[initPos - 1][resultPos]) { // Is the smallest value directly above the current position?
                                editString = "-" + editString;
                                initPos--;
                            } else if (smallest == distances[initPos][resultPos - 1]) { // Is the smallest value to the left of the current position?
                                editString = "+" + result.charAt(resultPos - 1) + editString;
                                resultPos--;
                            }
                            editDistance++;
                        }
                    }
                    // Add any characters from the beginning of the result string that were not
                    // reached
                    if (initPos == 0 && resultPos > 0) {
                        for (int i=resultPos; i > 0; i--) {
                            editString = "+" + result.charAt(i - 1) + editString;
                            resultPos--;
                            editDistance++;
                        }
                    // Remove any characters from the beginning of the init string that were
                    // not reached
                    } if (resultPos == 0 && initPos > 0) {
                        for (int i=initPos; i>0; i--) {
                            editString = "-" + editString;
                            initPos--;
                            editDistance++;
                        }
                    }
                    System.out.println(editDistance + ":  " + editString);
                }
            } else {
                break;
            }
        } while (true);
        scanner.close();
    }
}
