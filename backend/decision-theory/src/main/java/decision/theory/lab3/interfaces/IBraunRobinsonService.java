package decision.theory.lab3.interfaces;

import decision.theory.lab3.model.BraunRobinsonResult;

public interface IBraunRobinsonService {
    
    BraunRobinsonResult calculate(double[][] matrix, int iterationAmount, IInitStrategy strategy);
    
    BraunRobinsonResult calculate(double[][] matrix, double precision, IInitStrategy strategy);
    
}
