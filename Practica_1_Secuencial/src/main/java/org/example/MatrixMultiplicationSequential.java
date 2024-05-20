package org.example;

public class MatrixMultiplicationSequential {
    public static void main(String[] args) {
        // Tamaño de las matrices cuadradas
        int n = 3;
        int[][] A_matriz = new int[n][n]; // Matriz A
        int[][] B_matriz = new int[n][n]; // Matriz B
        int[][] C_matriz = new int[n][n]; // Matriz C (resultado de la multiplicación)

        fillMatrixWithRandomInts(A_matriz, n); // Llena la matriz A con números aleatorios
        fillMatrixWithRandomInts(B_matriz, n); // Llena la matriz B con números aleatorios

        System.out.println("Matriz A inicializada:");
        imprimirMatriz(A_matriz);
        System.out.println("Matriz B inicializada:");
        imprimirMatriz(B_matriz);

        // Multiplicación de matrices
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C_matriz[i][j] = 0;
                for (int k = 0; k < n; k++) {
                    C_matriz[i][j] += A_matriz[i][k] * B_matriz[k][j]; // Multiplicación de matrices
                }
            }
        }

        System.out.println("Matriz C resultante:");
        imprimirMatriz(C_matriz); // Imprime la matriz C
    }

    public static void imprimirMatriz(int[][] matriz) {
        // Determinar el ancho máximo de los números en la matriz para el formato
        int maxWidth = 0;
        for (int[] fila : matriz) {
            for (int num : fila) {
                maxWidth = Math.max(maxWidth, String.valueOf(num).length());
            }
        }

        // Imprimir la matriz con formato
        for (int[] fila : matriz) {
            for (int num : fila) {
                System.out.print(String.format("%" + maxWidth + "d ", num));
            }
            System.out.println();
        }
    }

    public static void fillMatrixWithRandomInts(int[][] matrix, int size) {
        java.util.Random rand = new java.util.Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rand.nextInt(10); // Números aleatorios entre 0 y 9
            }
        }
    }
}
