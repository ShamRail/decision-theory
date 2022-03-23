package decision.theory.lab3.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.NonNegativeConstraint;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.springframework.stereotype.Component;

import decision.theory.lab3.interfaces.IProbabilitySolver;

@Component
public class ProbabilitySolver implements IProbabilitySolver {

    @Override
    public MixProbabilitiesResult solve(double[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            throw new IllegalArgumentException("Rows or cols are empty");
        }
        var result = new MaxMinInitStrategy().choose(matrix);
        if (result.getMaxMin() == result.getMinMax()) {
            throw new IllegalArgumentException(
                    String.format(
                            "MaxMin = MinMax. Mix strategies can't be found. Game price = %.2f", result.getMaxMin()
                    )
            );
        }
        var tMatrix = transponate(matrix);
        var rowsResult = solveBy(matrix, Relationship.LEQ, GoalType.MAXIMIZE);
        var colsResult = solveBy(tMatrix, Relationship.GEQ, GoalType.MINIMIZE);
        return new MixProbabilitiesResult(rowsResult, colsResult, calculateGamePrice(matrix, rowsResult, colsResult));
    }

    private double[][] transponate(double[][] matrix) {
        var rowCount = matrix.length;
        var colCount = matrix[0].length;
        var result = new double[colCount][rowCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        return result;
    }

    private List<Double> solveBy(double[][] matrix, Relationship relationship, GoalType goalType) {
        var rowCount = matrix.length;
        var colCount = matrix[0].length;
        var functionGoal = IntStream.range(0, colCount)
                .mapToDouble(i -> 1.0)
                .toArray();
        var function = new LinearObjectiveFunction(functionGoal, 0);
        var constraints = IntStream.range(0, rowCount)
                .mapToObj(i -> new LinearConstraint(matrix[i], relationship, 1.0))
                .collect(Collectors.toList());
        var solution = new SimplexSolver()
                .optimize(function, new LinearConstraintSet(constraints), goalType, new NonNegativeConstraint(true))
                .getPoint();
        var totalKoefficient = 1.0 / Arrays.stream(solution)
                .sum();
        return Arrays.stream(solution)
                .map(xi -> xi * totalKoefficient)
                .mapToObj(d -> d)
                .toList();
    }

    private double calculateGamePrice(double[][] matrix, List<Double> rowsProbabilities,
            List<Double> columnProbabilitis) {
        var totalMathExpectation = 0.0;
        for (var i = 0; i < matrix.length; i++) {
            for (var j = 0; j < matrix[i].length; j++) {
                totalMathExpectation += matrix[i][j] * rowsProbabilities.get(j) * columnProbabilitis.get(i);
            }
        }
        return totalMathExpectation;
    }

}
