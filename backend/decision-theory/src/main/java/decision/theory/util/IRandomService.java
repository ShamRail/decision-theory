package decision.theory.util;

public interface IRandomService {

    double[] generateFullGroup(int count);

    double generate(double minValue, double maxValue);

}
