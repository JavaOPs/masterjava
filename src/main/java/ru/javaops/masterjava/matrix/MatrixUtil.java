package ru.javaops.masterjava.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * gkislin
 * 03.07.2016
 */
public class MatrixUtil {

    // TODO implement parallel multiplication matrixA*matrixB
    public static int[][] concurrentMultiply(int[][] matrixA, int[][] matrixB, ExecutorService executor) throws InterruptedException, ExecutionException {
        final int matrixSize = matrixA.length;
        class Cell {
            int i;
            int[] value;

            public Cell(int i, int[] value) {
                this.i = i;
                this.value = value;
            }
        }

        final CompletionService<Cell> completionService = new ExecutorCompletionService<>(executor);

        final List<Future<Cell>> tasks = new ArrayList<>();

        for (int i = 0; i < matrixSize; i++) {
            final int x = i;
            tasks.add(completionService.submit(() -> {
                int[] columnC = new int[matrixSize];
                int[] columnB = new int[matrixSize];
                for (int j = 0; j < matrixSize; ++j) {
                    columnB[j] = matrixB[j][x];
                }
                for (int j = 0; j < matrixSize; j++) {
                    int rowA[] = matrixA[j];
                    int sum = 0;
                    for (int k = 0; k < matrixSize; k++) {
                        sum += rowA[k] * columnB[k];
                    }
                    columnC[j] = sum;
                }
                return new Cell(x, columnC);
            }));
        }
        return new Callable<int[][]>() {
            @Override
            public int[][] call() {
                final int[][] matrixC = new int[matrixSize][matrixSize];
                while (!tasks.isEmpty()) {
                    Cell cell = null;
                    Future<Cell> future = null;
                    try {
                        future = completionService.poll(10, TimeUnit.SECONDS);
                        cell = future.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    matrixC[cell.i] = cell.value;
                    tasks.remove(future);
                }
                return matrixC;
            }
        }.call();
    }

    // TODO optimize by https://habrahabr.ru/post/114797/
    public static int[][] singleThreadMultiply(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            int[] columnB = new int[matrixSize];
            for (int j = 0; j < matrixSize; ++j) {
                columnB[j] = matrixB[j][i];
            }
            for (int j = 0; j < matrixSize; j++) {
                int rowA[] = matrixA[j];
                int sum = 0;
                for (int k = 0; k < matrixSize; k++) {
                    sum += rowA[k] * columnB[k];
                }
                matrixC[i][j] = sum;
            }
        }
        return matrixC;
    }

    public static int[][] create(int size) {
        int[][] matrix = new int[size][size];
        Random rn = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rn.nextInt(10);
            }
        }
        return matrix;
    }

    public static boolean compare(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (matrixA[i][j] != matrixB[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
