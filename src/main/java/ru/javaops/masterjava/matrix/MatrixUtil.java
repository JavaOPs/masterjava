package ru.javaops.masterjava.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class MatrixUtil {

    public static int[][] concurrentMultiplyStreams(int[][] matrixA, int[][] matrixB, int threadNumber)
            throws InterruptedException, ExecutionException {

        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        new ForkJoinPool(threadNumber).submit(
                () -> IntStream.range(0, matrixSize)
                        .parallel()
                        .forEach(row -> {
                            final int[] rowA = matrixA[row];
                            final int[] rowC = matrixC[row];

                            for (int idx = 0; idx < matrixSize; idx++) {
                                final int elA = rowA[idx];
                                final int[] rowB = matrixB[idx];
                                for (int col = 0; col < matrixSize; col++) {
                                    rowC[col] += elA * rowB[col];
                                }
                            }
                        })).get();

        return matrixC;
    }

    public static int[][] concurrentMultiply(int[][] matrixA, int[][] matrixB, ExecutorService executor) throws InterruptedException, ExecutionException {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][];

        final int[][] matrixBT = new int[matrixSize][matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                matrixBT[i][j] = matrixB[j][i];
            }
        }

        List<Callable<Void>> tasks = new ArrayList<>(matrixSize);
        for (int j = 0; j < matrixSize; j++) {
            final int row = j;
            tasks.add(() -> {
                final int[] rowC = new int[matrixSize];
                for (int col = 0; col < matrixSize; col++) {
                    final int[] rowA = matrixA[row];
                    final int[] columnB = matrixBT[col];
                    int sum = 0;
                    for (int k = 0; k < matrixSize; k++) {
                        sum += rowA[k] * columnB[k];
                    }
                    rowC[col] = sum;
                }
                matrixC[row] = rowC;
                return null;
            });
        }
        executor.invokeAll(tasks);
        return matrixC;
    }

    public static int[][] concurrentMultiply2(int[][] matrixA, int[][] matrixB, ExecutorService executor) throws InterruptedException {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];
        final CountDownLatch latch = new CountDownLatch(matrixSize);

        for (int row = 0; row < matrixSize; row++) {
            final int[] rowA = matrixA[row];
            final int[] rowC = matrixC[row];

            executor.submit(() -> {
                for (int idx = 0; idx < matrixSize; idx++) {
                    final int elA = rowA[idx];
                    final int[] rowB = matrixB[idx];
                    for (int col = 0; col < matrixSize; col++) {
                        rowC[col] += elA * rowB[col];
                    }
                }
                latch.countDown();
            });
        }
        latch.await();
        return matrixC;
    }

    public static int[][] singleThreadMultiplyOpt(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        for (int col = 0; col < matrixSize; col++) {
            final int[] columnB = new int[matrixSize];
            for (int k = 0; k < matrixSize; k++) {
                columnB[k] = matrixB[k][col];
            }

            for (int row = 0; row < matrixSize; row++) {
                int sum = 0;
                final int[] rowA = matrixA[row];
                for (int k = 0; k < matrixSize; k++) {
                    sum += rowA[k] * columnB[k];
                }
                matrixC[row][col] = sum;
            }
        }
        return matrixC;
    }

    public static int[][] singleThreadMultiplyOpt2(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        for (int row = 0; row < matrixSize; row++) {
            final int[] rowA = matrixA[row];
            final int[] rowC = matrixC[row];

            for (int idx = 0; idx < matrixSize; idx++) {
                final int elA = rowA[idx];
                final int[] rowB = matrixB[idx];
                for (int col = 0; col < matrixSize; col++) {
                    rowC[col] += elA * rowB[col];
                }
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