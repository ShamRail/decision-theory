package decision.theory.lab2.model.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import decision.theory.lab2.model.LvmTreeEdge;

import java.util.List;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record LvmInputDTO(List<LvmTreeEdge> edges, Map<String, Double> nodeProbabilities) {
}
