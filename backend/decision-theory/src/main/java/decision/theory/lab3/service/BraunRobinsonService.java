package decision.theory.lab3.service;

import decision.theory.lab3.interfaces.IBraunRobinsonService;
import decision.theory.lab3.model.BraunRobinsonRow;
import decision.theory.util.IRandomService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;

@Service
public class BraunRobinsonService implements IBraunRobinsonService {

    private final IRandomService randomService;

    public BraunRobinsonService(IRandomService randomService) {
        this.randomService = randomService;
    }
    
    @Override
    public List<BraunRobinsonRow> calculate(double[][] matrix, int iterationAmount) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            throw new IllegalArgumentException("Matrix is empty or it's vector");
        }
        if (iterationAmount < 2) {
            throw new IllegalArgumentException("Iteration amount must be more than or equal to 1");
        }
        var result = new ArrayList<BraunRobinsonRow>();
        var rowCount = matrix.length;
        var colCount = matrix[0].length;
        var row = copyRow(matrix[(int) (randomService.generate(0, rowCount))]);
        var col = copyCol(matrix, (int) (randomService.generate(0, colCount)));
        var min = findExtremum(col, (el1, el2) -> el1 < el2);
        var max = findExtremum(row, (el1, el2) -> el1 > el2);
        var previousRow = new BraunRobinsonRow(1, 
                col, row, min.index + 1, min.value, max.index + 1, max.value
        );
        result.add(previousRow);
        for (int k = 2; k <= iterationAmount; k++) {
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
            previousRow = new BraunRobinsonRow(
                    k, colResult, rowResult, 
                    min.index + 1, min.value / k, 
                    max.index + 1, max.value / k
            );
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
