package decision.theory.lab3.service;

import decision.theory.lab3.interfaces.IInitStrategy;

public class ManInputStrategy implements IInitStrategy {

    private int row;

    private int col;

    public ManInputStrategy(int row, int col) {
        super();
        this.row = row;
        this.col = col;
    }

    @Override
    public Result choose(double[][] matrix) {
        return new Result(row, 0, col, 0);
    }

}
