package decision.theory.lab3.model;

import java.util.List;
import java.util.Objects;

public class BraunRobinsonRow {
    
    private int iteration;
    
    private List<Double> colResult;
    
    private List<Double> rowResult;
    
    private int minIndex;
    
    private double min;
    
    private int maxIndex;
    
    private double max;

    public BraunRobinsonRow(int iteration, List<Double> colResult, List<Double> rowResult, 
                            int minIndex, double min, int maxIndex, double max) {
        this.iteration = iteration;
        this.colResult = colResult;
        this.rowResult = rowResult;
        this.minIndex = minIndex;
        this.min = min;
        this.maxIndex = maxIndex;
        this.max = max;
    }

    public int getIteration() {
        return iteration;
    }

    public List<Double> getColResult() {
        return colResult;
    }

    public List<Double> getRowResult() {
        return rowResult;
    }

    public int getMinIndex() {
        return minIndex;
    }

    public double getMin() {
        return min;
    }

    public int getMaxIndex() {
        return maxIndex;
    }

    public double getMax() {
        return max;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BraunRobinsonRow other = (BraunRobinsonRow) obj;
        if (this.iteration != other.iteration) {
            return false;
        }
        if (this.minIndex != other.minIndex) {
            return false;
        }
        if (Double.doubleToLongBits(this.min) != Double.doubleToLongBits(other.min)) {
            return false;
        }
        if (this.maxIndex != other.maxIndex) {
            return false;
        }
        if (Double.doubleToLongBits(this.max) != Double.doubleToLongBits(other.max)) {
            return false;
        }
        if (!Objects.equals(this.colResult, other.colResult)) {
            return false;
        }
        return Objects.equals(this.rowResult, other.rowResult);
    }

    @Override
    public String toString() {
        return "BraunRobinsonRow{" + "iteration=" + iteration + ", colResult=" + colResult + ", rowResult=" 
                + rowResult + ", minIndex=" + minIndex + ", min=" + min + ", maxIndex=" 
                + maxIndex + ", max=" + max + '}';
    }
    
    
    
    
}
