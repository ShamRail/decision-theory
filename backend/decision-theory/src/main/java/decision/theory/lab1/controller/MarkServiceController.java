package decision.theory.lab1.controller;

import decision.theory.lab1.interfaces.model.IMarkProcessResult;
import decision.theory.lab1.interfaces.model.IMarkProcessSourceData;
import decision.theory.lab1.interfaces.service.IMarkProcessService;
import decision.theory.lab1.model.MarkProcessSourceData;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/dmk")
public class MarkServiceController {

    private final IMarkProcessService markProcessService;

    public MarkServiceController(IMarkProcessService markProcessService) {
        this.markProcessService = markProcessService;
    }

    @GetMapping("/generate")
    public IMarkProcessSourceData generateSourceData(
            @RequestParam int stateCount,
            @RequestParam int strategyCount,
            @RequestParam double minV,
            @RequestParam double maxV
    ) {
        return markProcessService.generateSourceData(stateCount, strategyCount, minV, maxV);
    }

    @PostMapping("/calculate")
    public IMarkProcessResult calculateProcess(
            @RequestBody MarkProcessSourceData sourceData,
            @RequestParam int stepAmount) {
        return markProcessService.calculate(
                sourceData.getProbabilities(), sourceData.getValues(), stepAmount
        );
    }

}
