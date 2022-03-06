package decision.theory.lab1.service;

import decision.theory.lab1.interfaces.model.IMarkProcessResult;
import decision.theory.lab1.interfaces.model.IMarkProcessSourceData;
import decision.theory.lab1.interfaces.service.IMarkProcessService;
import decision.theory.lab1.interfaces.service.IRandomService;
import decision.theory.lab1.model.MarkProcessResult;
import decision.theory.lab1.model.MarkProcessSourceData;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DiscreteMarkProcessService implements IMarkProcessService {

    private IRandomService randomService;

    public DiscreteMarkProcessService(IRandomService randomService) {
        this.randomService = randomService;
    }

    @Override
    public IMarkProcessResult calculate(List<double[][]> probabilityData, List<double[][]> valuesData, int stepAmount) {
        var strategiesCount = probabilityData.size();
        var stateCount = probabilityData.get(0).length;
        var table = formTable(
                stateCount, strategiesCount, probabilityData, valuesData
        );
        var log = new ArrayList<String>();
        var calculatedTable = calculateExpectedValue(table, log);
        var processResult = calculateTotalExpectedValue(calculatedTable, stepAmount, log);
        return prepareResult(processResult, probabilityData, log);
    }

    @Override
    public IMarkProcessSourceData generateSourceData(int statesCount, int strategiesCount,
                                                     double minV, double maxV) {
        var probabilities = new ArrayList<double[][]>(strategiesCount);
        var values = new ArrayList<double[][]>(strategiesCount);
        for (var k = 0; k < strategiesCount; k++) {
            var currentProbabilities = new double[statesCount][statesCount];
            var currentValues = new double[statesCount][statesCount];
            for (var i = 0; i < statesCount; i++) {
                var fullGroup = randomService.generateFullGroup(statesCount);
                for (var j = 0; j < statesCount; j++) {
                    currentValues[i][j] = randomService.generate(minV, maxV);
                }
                currentProbabilities[i] = fullGroup;
            }
            probabilities.add(currentProbabilities);
            values.add(currentValues);
        }
        return new MarkProcessSourceData(probabilities, values);
    }

    private List<CalculationRow> formTable(int stateCount, int strategiesCount,
                                           List<double[][]> probabilityData, List<double[][]> valuesData) {
        var rows = new ArrayList<CalculationRow>();
        for (var currentState = 0; currentState < stateCount; currentState++) {
            for (var currentStrategy = 0; currentStrategy < strategiesCount; currentStrategy++) {
                var row = new CalculationRow(currentState, currentStrategy);
                var vData = valuesData.get(currentStrategy);
                var pData = probabilityData.get(currentStrategy);
                for (var nextState = 0; nextState < stateCount; nextState++) {
                    row.values.add(vData[currentState][nextState]);
                    row.probabilities.add(pData[currentState][nextState]);
                }
                rows.add(row);
            }
        }
        return rows;
    }

    private List<CalculationRow> calculateExpectedValue(List<CalculationRow> table, List<String> log) {
        log.add("Ожидаемые доходы:");
        for (var row : table) {
            var logLine = String.format("q_%d_%d = ", row.strategy, row.currentState);
            var expectedValue = 0.0;
            for (int i = 0; i < row.values.size(); i++) {
                var value = row.values.get(i);
                var probability = row.probabilities.get(i);
                logLine = logLine.concat(String.format("%.2f * %.2f + ", value, probability));
                expectedValue += value * probability;
            }
            row.expectedValue = expectedValue;
            logLine = logLine.substring(0, logLine.length() - 2);
            logLine = logLine.concat(String.format("= %.2f", expectedValue));
            log.add(logLine);
        }
        return table;
    }

    private Map<Integer, Map<Integer, ResultRow>> calculateTotalExpectedValue(List<CalculationRow> table, int stepAmount, List<String> log) {

        log.addAll(List.of(
                "",
                "Полные ожидаемые доходы: ",
                "",
                "vj(0) = 0",
                ""
        ));

        var rowsPerState = table.stream().collect(Collectors.groupingBy(
                row -> row.currentState, Collectors.toList()
        ));

        var prevTotalValues = new HashMap<Integer, ResultRow>();
        var result = new HashMap<Integer, Map<Integer, ResultRow>>();
        result.put(0, new HashMap<>());
        for (var state : rowsPerState.keySet()) {
            var value = new ResultRow(state, -1, 0);
            prevTotalValues.put(state, value);
            result.get(0).put(state, value);
        }

        for (var step = 1; step <= stepAmount; step++) {
            result.put(step, new HashMap<>());
            var bestResults = new HashMap<Integer, ResultRow>();
            for (var groupPerState : rowsPerState.entrySet()) {
                var totalValues = new ArrayList<Double>();
                var bestNextState = new ResultRow(-1, -1, 0);
                var state = groupPerState.getKey();
                var group = groupPerState.getValue();
                for (var row : group) {
                    var logLine = String.format("v_%d_%d_(%d) = %.2f + ", row.strategy, row.currentState, step, row.expectedValue);

                    var totalValue = row.expectedValue;
                    for (int j = 0; j < row.values.size(); j++) {
                        var probability = row.probabilities.get(j);
                        var prevValue = prevTotalValues.get(j).value;
                        logLine = logLine.concat(String.format("%.2f * %.2f + ", prevValue, probability));
                        totalValue += probability * prevValue;
                    }
                    totalValues.add(totalValue);

                    if (totalValue > bestNextState.value) {
                        bestNextState = new ResultRow(state, row.strategy, totalValue);
                    }

                    logLine = logLine.substring(0, logLine.length() - 2);
                    logLine = logLine.concat(String.format("= %.2f", totalValue));
                    log.add(logLine);
                }

                bestResults.put(state, bestNextState);

                log.add(String.format(
                        "---> v%d(%d) = max[%s] = %.2f", state, step,
                        totalValues.stream().map(v -> String.format("%.2f", v)).collect(Collectors.joining(";")),
                        bestNextState.value
                ));
                log.add(String.format("---> d%d(%d) = %d", state, step, bestNextState.strategy));
                log.add("");
            }
            for (var resultPerState : bestResults.entrySet()) {
                prevTotalValues.put(resultPerState.getKey(), resultPerState.getValue());
                result.get(step).put(resultPerState.getKey(), resultPerState.getValue());
            }
        }

        return result;
    }

    private IMarkProcessResult prepareResult(Map<Integer, Map<Integer, ResultRow>> result, List<double[][]> probabilityData, List<String> log) {
        var valueResult = new TreeMap<Integer, Map<Integer, Double>>();
        var strategyResult = new TreeMap<Integer, Map<Integer, Integer>>();
        for (var keyValue : result.entrySet()) {
            var vResult = new TreeMap<Integer, Double>();
            var sResult = new TreeMap<Integer, Integer>();
            for (var value : keyValue.getValue().values()) {
                vResult.put(value.state, value.value);
                sResult.put(value.state, value.strategy);
            }
            valueResult.put(keyValue.getKey(), vResult);
            strategyResult.put(keyValue.getKey(), sResult);
        }
        return new MarkProcessResult(probabilityData, valueResult, strategyResult, log);
    }

    private static class CalculationRow {

        private int currentState;

        private int strategy;

        private List<Double> probabilities = new ArrayList<>();

        private List<Double> values = new ArrayList<>();

        private double expectedValue;

        public CalculationRow(int currentState, int strategy) {
            this.currentState = currentState;
            this.strategy = strategy;
        }

        @Override
        public String toString() {
            return "CalculationRow{" +
                    "currentState=" + currentState +
                    ", strategy=" + strategy +
                    ", probabilities=" + probabilities +
                    ", values=" + values +
                    ", expectedValue=" + expectedValue +
                    '}';
        }
    }

    private static record ResultRow(int state, int strategy, double value) {
    }

}
