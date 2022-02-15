package decision.theory.lab1.interfaces.model.random;

public interface IGenerator {

    double generate(IDistribution distribution, double lowerBound, double upperBound);

}
