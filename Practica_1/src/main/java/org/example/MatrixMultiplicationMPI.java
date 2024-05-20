package org.example;

import mpi.*;

public class MatrixMultiplicationMPI {

    public static void main(String[] args) throws Exception {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank(); // Obtiene el rango del proceso actual
        int size = MPI.COMM_WORLD.Size(); // Obtiene el tamaño del comunicador MPI

        // Tamaño de las matrices cuadradas
        int n = 3;
        int[][] A_matriz = new int[n][n]; // Matriz A
        int[][] B_matriz = new int[n][n]; // Matriz B
        int[][] C_matriz = new int[n][n]; // Matriz C (resultado de la multiplicación)

        // Buffers para MPI.Gatherv
        int[] sendbuf = new int[n * n]; // Buffer para enviar datos
        int[] recvbuf = null; // Buffer para recibir datos (inicializado más adelante)

        // 1. Arquitectura de Memoria Compartida
        // Solo el proceso raíz inicializa las matrices A y B y el buffer de recepción
        if (rank == 0) {
            fillMatrixWithRandomInts(A_matriz, n); // Llena la matriz A con números aleatorios
            fillMatrixWithRandomInts(B_matriz, n); // Llena la matriz B con números aleatorios

            System.out.println("Matriz A inicializada por el proceso raiz:");
            imprimirMatriz(A_matriz);
            System.out.println("Matriz B inicializada por el proceso raiz:");
            imprimirMatriz(B_matriz);

            recvbuf = new int[n * n]; // Inicializa el buffer de recepción
        }


        // 2. Arquitectura de Memoria Distribuida
        // Broadcast de la matriz A
        for (int i = 0; i < n; i++) {
            MPI.COMM_WORLD.Bcast(A_matriz[i], 0, n, MPI.INT, 0); // Transmite la fila i de la matriz A
        }

        // Broadcast de la matriz B
        for (int i = 0; i < n; i++) {
            MPI.COMM_WORLD.Bcast(B_matriz[i], 0, n, MPI.INT, 0); // Transmite la fila i de la matriz B
        }


        // Barrera para asegurar que todos los procesos hayan recibido las matrices
        MPI.COMM_WORLD.Barrier();


        // Medir tiempo de inicio
        long startTime = System.currentTimeMillis();

        // Calcular las filas correspondientes para cada proceso
        int rowsPerProcess = n / size; // Número de filas por proceso
        int startRow = rank * rowsPerProcess; // Fila inicial para el proceso actual
        int endRow = (rank == size - 1) ? n : startRow + rowsPerProcess; // Fila final para el proceso actual

        System.out.println("Proceso " + rank + " calculara " + (endRow - startRow) * n + " valores de la matriz C.");

        // Calcular las filas asignadas de la matriz C
        int index = 0;
        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < n; j++) {
                C_matriz[i][j] = 0;
                for (int k = 0; k < n; k++) {
                    C_matriz[i][j] += A_matriz[i][k] * B_matriz[k][j]; // Multiplicación de matrices
                }
                sendbuf[index] = C_matriz[i][j]; // Agrega el resultado al buffer de envío
                index++;
            }
        }

        // 3. Paso de Mensajes
        // Reunir las filas de la matriz C en el proceso raíz usando mensajes
        if (rank == 0) {
            for (int i = 1; i < size; i++) {
                int srcStartRow = i * rowsPerProcess;
                int srcEndRow = (i == size - 1) ? n : srcStartRow + rowsPerProcess;
                for (int j = srcStartRow; j < srcEndRow; j++) {
                    MPI.COMM_WORLD.Recv(C_matriz[j], 0, n, MPI.INT, i, j); // Recibe la fila j de un proceso i
                }
            }
        } else {
            for (int i = startRow; i < endRow; i++) {
                MPI.COMM_WORLD.Send(C_matriz[i], 0, n, MPI.INT, 0, i); // Envía la fila i al proceso raíz
            }
        }

        // Medir tiempo de finalización
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        // El proceso raíz imprime la matriz resultante C
        if (rank == 0) {
            System.out.println("Tiempo de ejecucion: " + totalTime + " ms");
            System.out.println("Matriz C resultante:");
            imprimirMatriz(C_matriz); // Imprime la matriz C
        }

        // 4. Recopilación de Resultados
        // Usar MPI.Gatherv para recolectar todos los resultados en un arreglo
        int[] recvcounts = new int[size];
        int[] displs = new int[size];

        for (int i = 0; i < size; i++) {
            int rows = (i == size - 1) ? (n - i * rowsPerProcess) : rowsPerProcess;
            recvcounts[i] = rows * n; // Número de elementos a recibir del proceso i
            displs[i] = i * rowsPerProcess * n; // Desplazamiento para el proceso i en el arreglo de recepción
        }

        MPI.COMM_WORLD.Gatherv(sendbuf, 0, (endRow - startRow) * n, MPI.INT, recvbuf, 0, recvcounts, displs, MPI.INT, 0);

        // Si este es el proceso raíz, imprimimos el array resultante, los resultados parciales
        if (rank == 0) {
            System.out.print("Resultados parciales: ");
            for (int i = 0; i < n * n; i++) {
                if (recvbuf[i] != 0) {
                    System.out.print(recvbuf[i] + " ");
                }
            }
            System.out.println();
        }

        MPI.Finalize(); // Finaliza el entorno MPI
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
