package decision.theory.lab1.interfaces.model;

import decision.theory.lab1.interfaces.model.random.IGenerator;

public interface IMarkProcessBuilder {

    IMarkProcessBuilder addState(IMarkProcessState state);

    IMarkProcessBuilder addStrategy(IMarkProcessStrategy strategy);

    IMarkProcessBuilder withStepAmount(int stepAmount);

    IMarkProcessBuilder withProbabilityGenerator(IGenerator generator);

    IMarkProcessBuilder withValueGenerator(IGenerator generator);

    IMarkProcess build();

}
