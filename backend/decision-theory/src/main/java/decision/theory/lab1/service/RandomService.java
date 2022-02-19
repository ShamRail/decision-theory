package decision.theory.lab1.service;

import decision.theory.lab1.interfaces.service.IRandomService;
import org.springframework.stereotype.Service;

@Service
public class RandomService implements IRandomService {

    @Override
    public double[] generateFullGroup(int count) {
        var group = new double[count];
        var total = 1.0;
        for (var i = 0; i < count - 1; i++) {
            var part = Math.random() * total;
            group[i] = part;
            total -= part;
        }
        group[count - 1] = total;
        return group;
    }

    @Override
    public double generate(double minValue, double maxValue) {
        return minValue + Math.random() * (maxValue - minValue);
    }

}
