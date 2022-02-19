package decision.theory.lab1.interfaces.service;

public interface IRandomService {

    double[] generateFullGroup(int count);

    double generate(double minValue, double maxValue);

}
