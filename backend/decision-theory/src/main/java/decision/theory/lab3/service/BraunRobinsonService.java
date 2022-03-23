package decision.theory.lab3.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import decision.theory.lab3.interfaces.IBraunRobinsonService;
import decision.theory.lab3.interfaces.IInitStrategy;
import decision.theory.lab3.interfaces.IProbabilitySolver;
import decision.theory.lab3.model.BraunRobinsonResult;
import decision.theory.lab3.model.BraunRobinsonRow;

@Service
public class BraunRobinsonService implements IBraunRobinsonService {

    private final IProbabilitySolver probabilitySolver;

    public BraunRobinsonService(IProbabilitySolver probabilitySolver) {
        this.probabilitySolver = probabilitySolver;
    }

    @Override
    public BraunRobinsonResult calculate(double[][] matrix, int iterationAmount, IInitStrategy strategy) {
        if (iterationAmount < 2) {
            throw new IllegalArgumentException("Iteration amount must be more than or equal to 1");
        }
        return calculate(matrix, strategy, false, iterationAmount);
    }

    @Override
    public BraunRobinsonResult calculate(double[][] matrix, double precision, IInitStrategy strategy) {
        if (precision < 0) {
            throw new IllegalArgumentException("Precision must be positive");
        }
        return calculate(matrix, strategy, true, precision);
    }

    private BraunRobinsonResult calculate(double[][] matrix, IInitStrategy strategy, boolean stopByPrecision,
            double precisionOrIterations) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            throw new IllegalArgumentException("Matrix is empty or it's vector");
        }
        var log = new StringWriter();
        var out = new PrintWriter(log);
        var result = new ArrayList<BraunRobinsonRow>();
        var rowCount = matrix.length;
        var colCount = matrix[0].length;
        var initResult = strategy.choose(matrix);
        var firstRow = initResult.getRowIndex();
        var firstCol = initResult.getColIndex();
        var row = copyRow(matrix[firstRow]);
        var col = copyCol(matrix, firstCol);
        var min = findExtremum(col, (el1, el2) -> el1 < el2);
        var max = findExtremum(row, (el1, el2) -> el1 > el2);
        var previousRow = new BraunRobinsonRow(1, col, row, min.index + 1, min.value, max.index + 1, max.value);
        result.add(previousRow);
        
        out.printf("Пусть при k = 1 i1 = %d, j1 = %d %n%n", firstCol + 1, firstRow + 1);
        IntStream.range(0, rowCount).forEach(i -> out.printf("g1%d = a%d%d = %.2f%n", i + 1, i + 1, firstCol + 1, col.get(i)));
        out.println();
        IntStream.range(0, colCount).forEach(j -> out.printf("h1%d = a%d%d = %.2f%n", j + 1, firstRow + 1, j + 1, row.get(j)));
        out.println();
        out.printf(
                "M1 = min {%s} = %.2f%n", col.stream().map(v -> String.format("%.2f", v)).collect(Collectors.joining(", ")), min.value
        );
        out.printf(
                "V1 = max {%s} = %.2f%n", row.stream().map(v -> String.format("%.2f", v)).collect(Collectors.joining(", ")), max.value
        );
        out.println();
        out.printf("i1 = %d, j1 = %d %n%n", min.index + 1, max.index + 1);
        
        var deltaCol = Double.MAX_VALUE;
        var deltaRow = Double.MIN_VALUE;
        for (int k = 2; (!stopByPrecision && k <= precisionOrIterations)
                || (stopByPrecision && (deltaCol > precisionOrIterations || deltaRow > precisionOrIterations)); k++) {
            out.printf("k = %d%n%n", k);
            var colResult = new ArrayList<Double>(previousRow.getColResult());
            var rowResult = new ArrayList<Double>(previousRow.getRowResult());
            for (int i = 0; i < rowCount; i++) {
                out.printf(
                        "g_%d_%d = g_%d_%d + a%d%d = %.2f + %.2f = %.2f%n",
                        k, i + 1, k - 1, i + 1, i + 1, max.index + 1, colResult.get(i),
                        matrix[i][max.index], colResult.get(i) + matrix[i][max.index]
                );
                colResult.set(i, colResult.get(i) + matrix[i][max.index]);
            }
            out.println();
            
            for (int j = 0; j < colCount; j++) {
                out.printf(
                        "h_%d_%d = h_%d_%d + a%d%d = %.2f + %.2f = %.2f%n",
                        k, j + 1, k - 1, j + 1, min.index + 1, j + 1, rowResult.get(j),
                        matrix[min.index][j], rowResult.get(j) + matrix[min.index][j]
                );
                rowResult.set(j, rowResult.get(j) + matrix[min.index][j]);
            }
            out.println();
            
            min = findExtremum(colResult, (el1, el2) -> el1 < el2);
            max = findExtremum(rowResult, (el1, el2) -> el1 > el2);
            deltaCol = Math.abs(previousRow.getMin() - min.value / k);
            deltaRow = Math.abs(previousRow.getMax() - max.value / k);
            previousRow = new BraunRobinsonRow(
                    k, colResult, rowResult, min.index + 1, min.value / k, max.index + 1, max.value / k
            );
            
            out.printf(
                    "M%d = min {%s} / %d = %.2f / %d = %.2f%n",
                    k, colResult.stream().map(v -> String.format("%.2f", v)).collect(Collectors.joining(", ")),
                    k, min.value, k, min.value / k
            );
            out.printf(
                    "V%d = max {%s} / %d = %.2f / %d = %.2f%n",
                    k, rowResult.stream().map(v -> String.format("%.2f", v)).collect(Collectors.joining(", ")),
                    k, max.value, k, max.value / k
            );
            out.println();
            out.printf("i%d = %d, j%d = %d %n%n", k, min.index + 1, k, max.index + 1);
            
            result.add(previousRow);
        }
        return setGameProperties(matrix, new BraunRobinsonResult(result, log.getBuffer().toString()));
    }

    private Extremum findExtremum(List<Double> elements, BiPredicate<Double, Double> condition) {
        var extremum = new Extremum(elements.get(0), 0);
        for (var i = 1; i < elements.size(); i++) {
            if (condition.test(elements.get(i), extremum.value)) {
                extremum.value = elements.get(i);
                extremum.index = i;
            }
        }
        return extremum;
    }

    private BraunRobinsonResult setGameProperties(double[][] matrix, BraunRobinsonResult game) {
        var mmResult = new MaxMinInitStrategy().choose(matrix);
        game.setMaxMin(mmResult.getMaxMin());
        game.setMinMax(mmResult.getMinMax());
        if (mmResult.getMaxMin() == mmResult.getMinMax()) {
            game.setGamePrice(mmResult.getMaxMin());
            game.setMaxMinIndex(mmResult.getRowIndex());
            game.setMinMaxIndex(mmResult.getColIndex());
        } else {
            var mixResult = probabilitySolver.solve(matrix);
            game.setGamePrice(mixResult.getGamePrice());
            game.setColMixStrategies(mixResult.getColResult());
            game.setRowMixStrategies(mixResult.getRowResult());
        }
        return game;
    }

    private List<Double> copyRow(double[] row) {
        return Arrays.stream(row)
                .mapToObj(d -> d)
                .collect(Collectors.toList());
    }

    private List<Double> copyCol(double[][] matrix, int col) {
        var colLength = matrix.length;
        return IntStream.range(0, colLength)
                .mapToObj(i -> matrix[i][col])
                .collect(Collectors.toList());
    }

    private static class Extremum {

        private double value;

        private int index;

        Extremum(double value, int index) {
            this.value = value;
            this.index = index;
        }

    }

}
