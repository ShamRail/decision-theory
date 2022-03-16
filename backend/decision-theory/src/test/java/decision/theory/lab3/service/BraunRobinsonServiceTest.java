package decision.theory.lab3.service;

import decision.theory.lab3.model.BraunRobinsonRow;
import decision.theory.util.RandomService;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("checkstyle:MagicNumber")
public class BraunRobinsonServiceTest {

    /**
     * Test of calculate method, of class BraunRobinsonService.
     */
    @Test
    public void testCalculate() {
        var matrix = new double[][] {
                {6, 2, 5}, 
                {4, 3, 7}, 
                {5, 5, 6}
        };
        var iterationAmount = 3;
        var service = new BraunRobinsonService(getRandomStub(List.of(0.0, 0.0)));
        var expResult = List.of(
               new BraunRobinsonRow(1, List.of(6.0, 4.0, 5.0), List.of(6.0, 2.0, 5.0), 2, 4, 1, 6),
               new BraunRobinsonRow(2, List.of(12.0, 8.0, 10.0), List.of(10.0, 5.0, 12.0), 2, 4, 3, 6),
               new BraunRobinsonRow(3, List.of(17.0, 15.0, 16.0), List.of(14.0, 8.0, 19.0), 2, 5, 3, 19.0 / 3.0)
        );
        List<BraunRobinsonRow> result = service.calculate(matrix, iterationAmount);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testCalculate2() {
        var matrix = new double[][] {
                {3, 7, 9}, 
                {6, 1, 0}, 
                {2, 5, 10}
        };
        var iterationAmount = 2;
        var service = new BraunRobinsonService(getRandomStub(List.of(0.0, 1.0)));
        var expResult = List.of(
               new BraunRobinsonRow(1, List.of(7.0, 1.0, 5.0), List.of(3.0, 7.0, 9.0), 2, 1, 3, 9),
               new BraunRobinsonRow(2, List.of(16.0, 1.0, 15.0), List.of(9.0, 8.0, 9.0), 2, 0.5, 1, 4.5)
        );
        List<BraunRobinsonRow> result = service.calculate(matrix, iterationAmount);
        assertEquals(expResult, result);
    }
    
    private RandomService getRandomStub(List<Double> values) {
        return new RandomService() {
            
            private int i = 0;
            
            @Override
            public double generate(double minValue, double maxValue) {
                return values.get(i++);
            }
            
        };
    }
    
}
