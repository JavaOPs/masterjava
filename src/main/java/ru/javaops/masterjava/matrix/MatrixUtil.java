package ru.javaops.masterjava.matrix;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * gkislin
 * 03.07.2016
 */
public class MatrixUtil {

    public static int[][] concurrentMultiply(int[][] matrixA, int[][] matrixB, ExecutorService executor) throws InterruptedException, ExecutionException {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        class ColumnMultipleResult {
            private final int col;
            private final int[] columnC;

            private ColumnMultipleResult(int col, int[] columnC) {
                this.col = col;
                this.columnC = columnC;
            }
        }

        final CompletionService<ColumnMultipleResult> completionService = new ExecutorCompletionService<>(executor);

        for (int j = 0; j < matrixSize; j++) {
            final int col = j;
            final int[] columnB = new int[matrixSize];
            for (int k = 0; k < matrixSize; k++) {
                columnB[k] = matrixB[k][col];
            }
            completionService.submit(() -> {
                final int[] columnC = new int[matrixSize];

                for (int row = 0; row < matrixSize; row++) {
                    final int[] rowA = matrixA[row];
                    int sum = 0;
                    for (int k = 0; k < matrixSize; k++) {
                        sum += rowA[k] * columnB[k];
                    }
                    columnC[row] = sum;
                }
                return new ColumnMultipleResult(col, columnC);
            });
        }

        for (int i = 0; i < matrixSize; i++) {
            ColumnMultipleResult res = completionService.take().get();
            for (int k = 0; k < matrixSize; k++) {
                matrixC[k][res.col] = res.columnC[k];
            }
        }
        return matrixC;
    }

    public static int[][] concurrentMultiplyCayman(int[][] matrixA, int[][] matrixB, ExecutorService executor) throws InterruptedException, ExecutionException {
        final int matrixSize = matrixA.length;
        final int[][] matrixResult = new int[matrixSize][matrixSize];
        final int threadCount = Runtime.getRuntime().availableProcessors();
        final int maxIndex = matrixSize * matrixSize;
        final int cellsInThread = maxIndex / threadCount;
        final int[][] matrixBFinal = new int[matrixSize][matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                matrixBFinal[i][j] = matrixB[j][i];
            }
        }

        Set<Callable<Boolean>> threads = new HashSet<>();
        int fromIndex = 0;
        for (int i = 1; i <= threadCount; i++) {
            final int toIndex = i == threadCount ? maxIndex : fromIndex + cellsInThread;
            final int firstIndexFinal = fromIndex;
            threads.add(() -> {
                for (int j = firstIndexFinal; j < toIndex; j++) {
                    final int row = j / matrixSize;
                    final int col = j % matrixSize;

                    int sum = 0;
                    for (int k = 0; k < matrixSize; k++) {
                        sum += matrixA[row][k] * matrixBFinal[col][k];
                    }
                    matrixResult[row][col] = sum;
                }
                return true;
            });
            fromIndex = toIndex;
        }
        executor.invokeAll(threads);
        return matrixResult;
    }

    public static int[][] concurrentMultiplyDarthVader(int[][] matrixA, int[][] matrixB, ExecutorService executor)
            throws InterruptedException, ExecutionException {

        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        List<Callable<Void>> tasks = IntStream.range(0, matrixSize)
                .parallel()
                .mapToObj(i -> new Callable<Void>() {
                    private final int[] tempColumn = new int[matrixSize];

                    @Override
                    public Void call() throws Exception {
                        for (int c = 0; c < matrixSize; c++) {
                            tempColumn[c] = matrixB[c][i];
                        }
                        for (int j = 0; j < matrixSize; j++) {
                            int row[] = matrixA[j];
                            int sum = 0;
                            for (int k = 0; k < matrixSize; k++) {
                                sum += tempColumn[k] * row[k];
                            }
                            matrixC[j][i] = sum;
                        }
                        return null;
                    }
                })
                .collect(Collectors.toList());

        executor.invokeAll(tasks);
        return matrixC;
    }

    public static int[][] concurrentMultiply2(int[][] matrixA, int[][] matrixB, ExecutorService executor) throws InterruptedException, ExecutionException {
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

    // Optimized by https://habrahabr.ru/post/114797/
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