package decision.theory.lab3.interfaces;

public interface IInitStrategy {

    Result choose(double[][] matrix);

    class Result {

        private int rowIndex;
        
        private double maxMin;
        
        private int colIndex;
        
        private double minMax;

        public Result(int rowIndex, double maxMin, int colIndex, double minMax) {
            super();
            this.rowIndex = rowIndex;
            this.colIndex = colIndex;
            this.minMax = minMax;
            this.maxMin = maxMin;
        }

        public int getRowIndex() {
            return rowIndex;
        }

        public void setRowIndex(int rowIndex) {
            this.rowIndex = rowIndex;
        }

        public int getColIndex() {
            return colIndex;
        }

        public void setColIndex(int colIndex) {
            this.colIndex = colIndex;
        }

        public double getMaxMin() {
            return maxMin;
        }

        public void setMaxMin(double maxMin) {
            this.maxMin = maxMin;
        }

        public double getMinMax() {
            return minMax;
        }

        public void setMinMax(double minMax) {
            this.minMax = minMax;
        }        
        
    }

}