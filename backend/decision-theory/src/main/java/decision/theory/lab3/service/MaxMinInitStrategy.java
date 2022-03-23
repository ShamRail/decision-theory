package decision.theory.lab3.service;

import decision.theory.lab3.interfaces.IInitStrategy;

public class MaxMinInitStrategy implements IInitStrategy {

    @Override
    public Result choose(double[][] matrix) {
        var maxMin = findMaxMin(matrix);
        var minMax = findMinMax(matrix);        
        return new Result((int) maxMin[0], maxMin[1], (int) minMax[0], minMax[1]);
    }
    
    private double[] findMaxMin(double[][] matrix) {
        var result = new double[] {-1, Double.MIN_VALUE};
        for (var row = 0; row < matrix.length; row++) {
            var min = Double.MAX_VALUE;
            for (var col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] < min) {
                    min = matrix[row][col];
                }
            }
            if (min > result[1]) {
                result[0] = row;
                result[1] = min;
            }
        }
        return result;
    }
    
    private double[] findMinMax(double[][] matrix) {
        var result = new double[] {-1, Double.MAX_VALUE};
        var colCount = matrix[0].length;
        for (var col = 0; col < colCount; col++) {
            var max = Double.MIN_VALUE;
            for (var row = 0; row < matrix.length; row++) {
                if (matrix[row][col] > max) {
                    max = matrix[row][col];
                }
            }
            if (max < result[1]) {
                result[0] = col;
                result[1] = max;
            }
        }
        return result;
    }
    
}
