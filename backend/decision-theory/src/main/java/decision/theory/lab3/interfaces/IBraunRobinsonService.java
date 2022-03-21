package decision.theory.lab3.interfaces;

import java.util.List;

import decision.theory.lab3.model.BraunRobinsonRow;

public interface IBraunRobinsonService {
    
    List<BraunRobinsonRow> calculate(double[][] matrix, int iterationAmount, IInitStrategy strategy);
    
    List<BraunRobinsonRow> calculate(double[][] matrix, double precision, IInitStrategy strategy);
    
}
