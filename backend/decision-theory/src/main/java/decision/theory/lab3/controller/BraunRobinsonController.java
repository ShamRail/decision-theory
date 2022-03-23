package decision.theory.lab3.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import decision.theory.lab3.interfaces.IBraunRobinsonService;
import decision.theory.lab3.model.dto.MbrDTO;
import decision.theory.lab3.service.ManInputStrategy;

@CrossOrigin
@RestController
@RequestMapping("/mbr")
public class BraunRobinsonController {

    private IBraunRobinsonService braunRobinsonService;

    public BraunRobinsonController(IBraunRobinsonService braunRobinsonService) {
        this.braunRobinsonService = braunRobinsonService;
    }

    @PostMapping("/solve")
    public ResponseEntity<?> solve(@RequestBody MbrDTO mbrDTO, @RequestParam boolean byPrecision) {
        try {
            if (mbrDTO.getFirstRow() - 1  < 0 || mbrDTO.getFirstCol() - 1 < 0) {
                return new ResponseEntity(Map.of("message", "Invalid numbers of row or col"), HttpStatus.BAD_REQUEST);
            }
            var matrix = mbrDTO.getMatrix();
            var strategy = new ManInputStrategy(mbrDTO.getFirstRow() - 1, mbrDTO.getFirstCol() - 1);
            var precision = mbrDTO.getPrecision();
            var iterationAmount = mbrDTO.getIterationsAmount();
            var result = byPrecision ? braunRobinsonService.calculate(matrix, precision, strategy)
                    : braunRobinsonService.calculate(matrix, iterationAmount, strategy);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(Map.of("message", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
