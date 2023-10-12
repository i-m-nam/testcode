package simple.testtool.controller.study;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import simple.testtool.domain.study.Study;
import simple.testtool.domain.study.StudyRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudyController {

    private final StudyRepository repository;

    @GetMapping("/study/{id}")
    public Study getStudy(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Study not found for '" + id + "'"));
    }

    @GetMapping("/study")
    public List<Study> getAllStudy() {
        return repository.findAll();
    }

    @PostMapping("/study")
    public Study createsStudy(@RequestBody Study study) {
        return repository.save(study);
    }

}
