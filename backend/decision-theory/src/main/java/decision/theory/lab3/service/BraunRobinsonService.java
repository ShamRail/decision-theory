package decision.theory.lab3.service;

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
        var deltaCol = Double.MAX_VALUE;
        var deltaRow = Double.MIN_VALUE;
        for (int k = 2; (!stopByPrecision && k <= precisionOrIterations)
                || (stopByPrecision && (deltaCol > precisionOrIterations || deltaRow > precisionOrIterations)); k++) {
            var colResult = new ArrayList<Double>(previousRow.getColResult());
            var rowResult = new ArrayList<Double>(previousRow.getRowResult());
            for (int i = 0; i < rowCount; i++) {
                colResult.set(i, colResult.get(i) + matrix[i][max.index]);
            }
            for (int j = 0; j < colCount; j++) {
                rowResult.set(j, rowResult.get(j) + matrix[min.index][j]);
            }
            min = findExtremum(colResult, (el1, el2) -> el1 < el2);
            max = findExtremum(rowResult, (el1, el2) -> el1 > el2);
            deltaCol = Math.abs(previousRow.getMin() - min.value / k);
            deltaRow = Math.abs(previousRow.getMax() - max.value / k);
            previousRow = new BraunRobinsonRow(
                    k, colResult, rowResult, min.index + 1, min.value / k, max.index + 1, max.value / k
            );
            result.add(previousRow);
        }
        return setGameProperties(matrix, new BraunRobinsonResult(result));
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
        var colLength = matrix[col].length;
        return IntStream.range(0, colLength)
                .mapToObj(j -> matrix[j][col])
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
