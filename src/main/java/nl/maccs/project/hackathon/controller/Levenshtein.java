package nl.maccs.project.hackathon.controller;

/**
 * @author Urfan
 */
public class Levenshtein {

    private static int[][] matrix;
    private static boolean calculated = false;
    private String s1;
    private String s2;

    public int[][] getMatrix(String s1, String s2) {
        setupMatrix(s1, s2);
        return matrix;
    }

    public int getSimilarity() {
        if (!calculated) {
            setupMatrix(s1, s2);
        }
        return matrix[s1.length()][s2.length()];
    }

    private void setupMatrix(String s1, String s2) {
        matrix = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 1; i <= s1.length(); i++) {
            matrix[i][0] = i;
        }

        for (int j = 1; j <= s1.length(); j++) {
            matrix[0][j] = j;
        }

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    matrix[i][j] = matrix[i - 1][j - 1];
                } else {
                    int minimum = Integer.MAX_VALUE;
                    if ((matrix[i - 1][j]) + 1 < minimum) {
                        minimum = (matrix[i - 1][j]) + 1;
                    }

                    if ((matrix[i][j - 1]) + 1 < minimum) {
                        minimum = (matrix[i][j - 1]) + 1;
                    }

                    if ((matrix[i - 1][j - 1]) + 1 < minimum) {
                        minimum = (matrix[i - 1][j - 1]) + 1;
                    }
                    matrix[i][j] = minimum;
                }
            }
        }
        calculated = true;
        displayMatrix();
    }

    private void displayMatrix() {
        System.out.println("  " + s1);
        for (int y = 0; y <= s2.length(); y++) {
            if (y - 1 < 0)
                System.out.print(" ");
            else
                System.out.print(s2.charAt(y - 1));

            for (int x = 0; x <= s1.length(); x++) {
                System.out.print(matrix[x][y]);
            }
            System.out.println();
        }
    }

}
