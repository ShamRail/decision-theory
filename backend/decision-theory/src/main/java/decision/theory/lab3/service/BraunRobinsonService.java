package decision.theory.lab3.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import decision.theory.lab3.interfaces.IBraunRobinsonService;
import decision.theory.lab3.model.BraunRobinsonRow;
import decision.theory.util.IRandomService;

@Service
public class BraunRobinsonService implements IBraunRobinsonService {

    private final IRandomService randomService;

    public BraunRobinsonService(IRandomService randomService) {
        this.randomService = randomService;
    }

    @Override
    public List<BraunRobinsonRow> calculate(double[][] matrix, int iterationAmount) {
        Locale.setDefault(Locale.ENGLISH);
        if (matrix.length == 0 || matrix[0].length == 0) {
            throw new IllegalArgumentException("Matrix is empty or it's vector");
        }
        if (iterationAmount < 2) {
            throw new IllegalArgumentException("Iteration amount must be more than or equal to 1");
        }
        var result = new ArrayList<BraunRobinsonRow>();
        var rowCount = matrix.length;
        var colCount = matrix[0].length;
        var firstRow = (int) (randomService.generate(0, rowCount));
        var firstCol = (int) (randomService.generate(0, colCount));
        var row = copyRow(matrix[firstRow]);
        var col = copyCol(matrix, firstCol);
        var min = findExtremum(col, (el1, el2) -> el1 < el2);
        var max = findExtremum(row, (el1, el2) -> el1 > el2);
        var previousRow = new BraunRobinsonRow(1,
                col, row, min.index + 1, min.value, max.index + 1, max.value
        );
        result.add(previousRow);
        System.out.printf("Пусть при k = 1 i1 = %d, j1 = %d %n%n", firstCol + 1, firstRow + 1);
        IntStream.range(0, colCount).forEach(i -> System.out.printf("g1%d = a%d%d = %.2f%n", i + 1, i + 1, firstCol + 1, col.get(i)));
        System.out.println();
        IntStream.range(0, rowCount).forEach(j -> System.out.printf("h1%d = a%d%d = %.2f%n", j + 1, firstRow + 1, j + 1, row.get(j)));
        System.out.println();
        System.out.printf(
                "M1 = min {%s} = %.2f%n", col.stream().map(v -> String.format("%.2f", v)).collect(Collectors.joining(", ")), min.value
        );
        System.out.printf(
                "V1 = max {%s} = %.2f%n", row.stream().map(v -> String.format("%.2f", v)).collect(Collectors.joining(", ")), max.value
        );
        System.out.println();
        System.out.printf("i1 = %d, j1 = %d %n%n", min.index + 1, max.index + 1);

        for (int k = 2; k <= iterationAmount; k++) {
            System.out.printf("k = %d%n%n", k);
            var colResult = new ArrayList<Double>(previousRow.getColResult());
            var rowResult = new ArrayList<Double>(previousRow.getRowResult());
            for (int i = 0; i < rowCount; i++) {
                System.out.printf(
                        "g_%d_%d = g_%d_%d + a%d%d = %.2f + %.2f = %.2f%n",
                        k, i + 1, k - 1, i + 1, i + 1, max.index + 1, colResult.get(i),
                        matrix[i][max.index], colResult.get(i) + matrix[i][max.index]
                );
                colResult.set(i, colResult.get(i) + matrix[i][max.index]);
            }
            System.out.println();

            for (int j = 0; j < colCount; j++) {
                System.out.printf(
                        "h_%d_%d = h_%d_%d + a%d%d = %.2f + %.2f = %.2f%n",
                        k, j + 1, k - 1, j + 1, min.index + 1, j + 1, rowResult.get(j),
                        matrix[min.index][j], rowResult.get(j) + matrix[min.index][j]
                );
                rowResult.set(j, rowResult.get(j) + matrix[min.index][j]);
            }
            System.out.println();

            min = findExtremum(colResult, (el1, el2) -> el1 < el2);
            max = findExtremum(rowResult, (el1, el2) -> el1 > el2);
            previousRow = new BraunRobinsonRow(
                    k, colResult, rowResult,
                    min.index + 1, min.value / k,
                    max.index + 1, max.value / k
            );

            System.out.printf(
                    "M%d = min {%s} / %d = %.2f / %d = %.2f%n",
                    k, colResult.stream().map(v -> String.format("%.2f", v)).collect(Collectors.joining(", ")),
                    k, min.value, k, min.value / k
            );
            System.out.printf(
                    "V%d = max {%s} / %d = %.2f / %d = %.2f%n",
                    k, rowResult.stream().map(v -> String.format("%.2f", v)).collect(Collectors.joining(", ")),
                    k, max.value, k, max.value / k
            );
            System.out.println();
            System.out.printf("i%d = %d, j%d = %d %n%n", k, min.index + 1, k, max.index + 1);

            result.add(previousRow);
        }
        return result;
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

    private Extremum maxInRow(double[] row) {
        var max = new Extremum(row[0], 0);
        for (var i = 1; i < row.length; i++) {
            if (row[i] > max.value) {
                max.value = row[i];
                max.index = i;
            }
        }
        return max;
    }

    private Extremum minInCol(double[][] matrix, int col) {
        var min = new Extremum(matrix[0][col], 0);
        var colLength = matrix[col].length;
        for (var j = 1; j < colLength; j++) {
            if (matrix[j][col] < min.value) {
                min.value = matrix[j][col];
                min.index = j;
            }
        }
        return min;
    }

    private List<Double> copyRow(double[] row) {
        return Arrays.stream(row).mapToObj(d -> d).collect(Collectors.toList());
    }

    private List<Double> copyCol(double[][] matrix, int col) {
        var colLength = matrix[col].length;
        return IntStream.range(0, colLength).mapToObj(j -> matrix[j][col]).collect(Collectors.toList());
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
