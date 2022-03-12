package decision.theory.lab2.controller;

import decision.theory.lab2.interfaces.ILvmService;
import decision.theory.lab2.model.LvmNodeResult;
import decision.theory.lab2.model.dto.LvmInputDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/lvm")
public class LvmController {

    private final ILvmService lvmService;

    public LvmController(ILvmService lvmService) {
        this.lvmService = lvmService;
    }

    @PostMapping("/calculate-tree")
    public List<LvmNodeResult> calculateTree(@RequestBody LvmInputDTO input) {
        return lvmService.restoreFunctionsAndCalculate(input.edges(), input.nodeProbabilities());
    }

}
