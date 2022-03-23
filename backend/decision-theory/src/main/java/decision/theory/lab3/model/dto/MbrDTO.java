package decision.theory.lab3.model.dto;

public class MbrDTO {

    private double[][] matrix;

    private int iterationsAmount;

    private double precision;

    private int firstRow;

    private int firstCol;

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public int getIterationsAmount() {
        return iterationsAmount;
    }

    public void setIterationsAmount(int iterationsAmount) {
        this.iterationsAmount = iterationsAmount;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getFirstCol() {
        return firstCol;
    }

    public void setFirstCol(int firstCol) {
        this.firstCol = firstCol;
    }

}
