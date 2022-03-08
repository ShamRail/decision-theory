package decision.theory.lab1.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DmkStateDTO {

    @JsonProperty("_states")
    private List<String> states;

    @JsonProperty("_strategies")
    private List<String> strategies;

    @JsonProperty("_minV")
    private int minV;

    @JsonProperty("_maxV")
    private int maxV;

    @JsonProperty("_stepAmount")
    private int stepAmount;

    @JsonProperty("_lastGeneratedData")
    private DmkGeneratedDataDTO lastGeneratedData;

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public List<String> getStrategies() {
        return strategies;
    }

    public void setStrategies(List<String> strategies) {
        this.strategies = strategies;
    }

    public int getMinV() {
        return minV;
    }

    public void setMinV(int minV) {
        this.minV = minV;
    }

    public int getMaxV() {
        return maxV;
    }

    public void setMaxV(int maxV) {
        this.maxV = maxV;
    }

    public int getStepAmount() {
        return stepAmount;
    }

    public void setStepAmount(int stepAmount) {
        this.stepAmount = stepAmount;
    }

    public DmkGeneratedDataDTO getLastGeneratedData() {
        return lastGeneratedData;
    }

    public void setLastGeneratedData(DmkGeneratedDataDTO lastGeneratedData) {
        this.lastGeneratedData = lastGeneratedData;
    }
}
