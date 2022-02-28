package decision.theory.lab1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import decision.theory.lab1.model.dto.DmkStateDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@Controller
@RequestMapping("/import")
public class ImportController {

    private final ObjectMapper deserializer = new ObjectMapper();

    @PostMapping(value = "/dmk",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DmkStateDTO> uploadDmkState(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(deserializer.readValue(file.getBytes(), DmkStateDTO.class));
    }

}
