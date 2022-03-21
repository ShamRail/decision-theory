package decision.theory.lab3.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import decision.theory.lab3.interfaces.IInitStrategy;
import decision.theory.lab3.model.BraunRobinsonRow;

@SuppressWarnings("checkstyle:MagicNumber")
public class BraunRobinsonServiceTest {

    @Test
    public void testCalculate() {
        var matrix = new double[][]{
            {6, 2, 5},
            {4, 3, 7},
            {5, 5, 6}
        };
        var iterationAmount = 3;
        var service = new BraunRobinsonService();
        var expResult = List.of(
                new BraunRobinsonRow(1, List.of(6.0, 4.0, 5.0), List.of(6.0, 2.0, 5.0), 2, 4, 1, 6),
                new BraunRobinsonRow(2, List.of(12.0, 8.0, 10.0), List.of(10.0, 5.0, 12.0), 2, 4, 3, 6),
                new BraunRobinsonRow(3, List.of(17.0, 15.0, 16.0), List.of(14.0, 8.0, 19.0), 2, 5, 3, 19.0 / 3.0)
        );
        List<BraunRobinsonRow> result = service.calculate(matrix, iterationAmount, getInitStrategy(0, 0));
        assertEquals(expResult, result);
    }

    @Test
    public void testCalculate2() {
        var matrix = new double[][]{
            {3, 7, 9},
            {6, 1, 0},
            {2, 5, 10}
        };
        var iterationAmount = 2;
        var service = new BraunRobinsonService();
        var expResult = List.of(
                new BraunRobinsonRow(1, List.of(7.0, 1.0, 5.0), List.of(3.0, 7.0, 9.0), 2, 1, 3, 9),
                new BraunRobinsonRow(2, List.of(16.0, 1.0, 15.0), List.of(9.0, 8.0, 9.0), 2, 0.5, 1, 4.5)
        );
        List<BraunRobinsonRow> result = service.calculate(matrix, iterationAmount, getInitStrategy(0, 1));
        assertEquals(expResult, result);
    }

    @Test
    public void testCalculate3() {
        var matrix = new double[][]{
            {10, 12, 2, 23},
            {9, 56, 65, 29},
            {14, 3, 37, 43},
            {78, 4, 17, 28}
        };
        var iterationAmount = 3;
        var service = new BraunRobinsonService();
        
        List<BraunRobinsonRow> result = service.calculate(matrix, iterationAmount, getInitStrategy(0, 0));

        var expResult = List.of(
                new BraunRobinsonRow(1, List.of(10.0, 9.0, 14.0, 78.0), List.of(10.0, 12.0, 2.0, 23.0), 2, 9.0, 4, 23),
                new BraunRobinsonRow(2, List.of(33.0, 38.0, 57.0, 106.0), List.of(19.0, 68.0, 67.0, 52.0), 1, 16.5, 2, 34),
                new BraunRobinsonRow(3, List.of(45.0, 94.0, 60.0, 110.0), List.of(29.0, 80.0, 69.0, 75.0), 1, 15.0, 2, 80.0 / 3.0)
        );

        assertEquals(expResult, result);
    }
    
    @Test
    public void testCalculateWithPrecision() {
        var matrix = new double[][]{
            {10, 12, 2, 23},
            {9, 56, 65, 29},
            {14, 3, 37, 43},
            {78, 4, 17, 28}
        };

        var epsilon = 0.0001;
        var service = new BraunRobinsonService();
        var result = service.calculate(matrix, epsilon, getInitStrategy(0, 0));
        var lastLine = result.get(result.size() - 1);
        
        assertEquals(22.91, lastLine.getMin(), 0.1);
        assertEquals(23.01, lastLine.getMax(), 0.1);
    }

    @Disabled
    @Test
    public void testCalculate4() {
        var matrix = new double[][]{
            {3, 7, 9},
            {6, 1, 0},
            {2, 5, 10}
        };
        var iterationAmount = 3;
        var service = new BraunRobinsonService();
        var expResult = List.of(
                new BraunRobinsonRow(1, List.of(7.0, 1.0, 5.0), List.of(3.0, 7.0, 9.0), 2, 1, 3, 9),
                new BraunRobinsonRow(2, List.of(16.0, 1.0, 15.0), List.of(9.0, 8.0, 9.0), 2, 0.5, 1, 4.5)
        );
        List<BraunRobinsonRow> result = service.calculate(matrix, iterationAmount, getInitStrategy(1, 2));
        assertEquals(expResult, result);
    }

    private IInitStrategy getInitStrategy(int rowIndex, int colIndex) {
        return new IInitStrategy() {
            @Override
            public Result choose(double[][] matrix) {
                return new Result(rowIndex, 0, colIndex, 0);
            }
            
        };
    }

}
