package decision.theory.lab3.interfaces;

import decision.theory.lab3.model.BraunRobinsonRow;
import java.util.List;

public interface IBraunRobinsonService {
    
    List<BraunRobinsonRow> calculate(double[][] matrix, int iterationAmount);
    
}
