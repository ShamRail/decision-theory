package decision.theory.lab3.model;

import java.util.Collections;
import java.util.List;

public class BraunRobinsonResult {

    private List<BraunRobinsonRow> steps;

    private int totalIterations;

    private double minMax;

    private double maxMin;

    private int maxMinIndex;

    private int minMaxIndex;

    private double gamePrice;

    private List<Double> rowMixStrategies = Collections.emptyList();

    private List<Double> colMixStrategies = Collections.emptyList();

    private String log;

    public BraunRobinsonResult(List<BraunRobinsonRow> steps, String log) {
        this.steps = steps;
        this.totalIterations = steps.size();
        this.log = log;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public List<BraunRobinsonRow> getSteps() {
        return steps;
    }

    public void setSteps(List<BraunRobinsonRow> steps) {
        this.steps = steps;
    }

    public int getTotalIterations() {
        return totalIterations;
    }

    public void setTotalIterations(int totalIterations) {
        this.totalIterations = totalIterations;
    }

    public double getMinMax() {
        return minMax;
    }

    public void setMinMax(double minMax) {
        this.minMax = minMax;
    }

    public double getMaxMin() {
        return maxMin;
    }

    public void setMaxMin(double maxMin) {
        this.maxMin = maxMin;
    }

    public double getGamePrice() {
        return gamePrice;
    }

    public void setGamePrice(double gamePrice) {
        this.gamePrice = gamePrice;
    }

    public List<Double> getRowMixStrategies() {
        return rowMixStrategies;
    }

    public void setRowMixStrategies(List<Double> rowMixStrategies) {
        this.rowMixStrategies = rowMixStrategies;
    }

    public List<Double> getColMixStrategies() {
        return colMixStrategies;
    }

    public void setColMixStrategies(List<Double> colMixStrategies) {
        this.colMixStrategies = colMixStrategies;
    }

    public int getMaxMinIndex() {
        return maxMinIndex;
    }

    public void setMaxMinIndex(int maxMinIndex) {
        this.maxMinIndex = maxMinIndex;
    }

    public int getMinMaxIndex() {
        return minMaxIndex;
    }

    public void setMinMaxIndex(int minMaxIndex) {
        this.minMaxIndex = minMaxIndex;
    }

}
