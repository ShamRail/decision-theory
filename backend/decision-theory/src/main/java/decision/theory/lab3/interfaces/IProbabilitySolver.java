package decision.theory.lab3.interfaces;

import java.util.List;

public interface IProbabilitySolver {

    MixProbabilitiesResult solve(double[][] matrix);

    class MixProbabilitiesResult {

        private List<Double> rowResult;

        private List<Double> colResult;

        private double gamePrice;

        public MixProbabilitiesResult(List<Double> rowResult, List<Double> colResult, double gamePrice) {
            super();
            this.rowResult = rowResult;
            this.colResult = colResult;
            this.gamePrice = gamePrice;
        }

        public List<Double> getRowResult() {
            return rowResult;
        }

        public void setRowResult(List<Double> rowResult) {
            this.rowResult = rowResult;
        }

        public List<Double> getColResult() {
            return colResult;
        }

        public void setColResult(List<Double> colResult) {
            this.colResult = colResult;
        }

        public double getGamePrice() {
            return gamePrice;
        }

        public void setGamePrice(double gamePrice) {
            this.gamePrice = gamePrice;
        }

    }

}
