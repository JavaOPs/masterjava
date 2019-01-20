package ru.javaops.masterjava.matrix;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 10)
@Measurement(iterations = 10)
@BenchmarkMode({Mode.SingleShotTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Threads(1)
@Fork(10)
@Timeout(time = 5, timeUnit = TimeUnit.MINUTES)
public class MatrixBenchmark {

    // Matrix size
    private static final int MATRIX_SIZE = 1000;

    @Param({"3", "4", "10"})
    private int threadNumber;

    private static int[][] matrixA;
    private static int[][] matrixB;

    @Setup
    public void setUp() {
        matrixA = MatrixUtil.create(MATRIX_SIZE);
        matrixB = MatrixUtil.create(MATRIX_SIZE);
    }

    private ExecutorService executor;

    //    @Benchmark
    public int[][] singleThreadMultiplyOpt() throws Exception {
        return MatrixUtil.singleThreadMultiplyOpt(matrixA, matrixB);
    }

    //    @Benchmark
    public int[][] singleThreadMultiplyOpt2() throws Exception {
        return MatrixUtil.singleThreadMultiplyOpt(matrixA, matrixB);
    }

    @Benchmark
    public int[][] concurrentMultiplyStreams() throws Exception {
        return MatrixUtil.concurrentMultiplyStreams(matrixA, matrixB, threadNumber);
    }

    //    @Benchmark
    public int[][] concurrentMultiply() throws Exception {
        return MatrixUtil.concurrentMultiply(matrixA, matrixB, executor);
    }

    @Benchmark
    public int[][] concurrentMultiply2() throws Exception {
        return MatrixUtil.concurrentMultiply2(matrixA, matrixB, executor);
    }

    @Setup
    public void setup() {
        executor = Executors.newFixedThreadPool(threadNumber);
    }

    @TearDown
    public void tearDown() {
        executor.shutdown();
    }
}