package nl.maccs.project.hackathon.controller;

import nl.maccs.project.hackathon.exception.ResourceNotFoundException;
import nl.maccs.project.hackathon.model.TitlesModel;
import nl.maccs.project.hackathon.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Urfan
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TitleController {

    @Autowired
    TitleRepository titleRepository;

    Levenshtein levenshtein;

    @GetMapping("/title")
    public List<TitlesModel> getAllTitleReferences() {
        return titleRepository.findAll();
    }

    @PostMapping("/title")
    public TitlesModel createTitle(@Valid @RequestBody TitlesModel titlesModel) {
        for (TitlesModel repo : titleRepository.findAll()) {
            String s1 = repo.getTitle();
            String s2 = titlesModel.getTitle();
           levenshtein.getMatrix(s1, s2);
        }

        return titleRepository.save(titlesModel);
    }

    @PutMapping("/title/{id}")
    public TitlesModel updateTitle(@PathVariable(value = "id") Long titleId, @Valid @RequestBody TitlesModel titleDetails) {
        TitlesModel titlesModel = titleRepository.findById(titleId).orElseThrow(() -> new ResourceNotFoundException("TitleReference", "id", titleId));

        titlesModel.setTitle(titleDetails.getTitle());
        TitlesModel updateTitle = titleRepository.save(titlesModel);

        return updateTitle;
    }

    //Delete title
    @DeleteMapping("/title/{id}")
    public ResponseEntity<?> deleteTitle(@PathVariable(value = "id") Long titleId) {
        TitlesModel titlesModel = titleRepository.findById(titleId).orElseThrow(() -> new ResourceNotFoundException("Title", "id", titleId));

        titleRepository.delete(titlesModel);

        return ResponseEntity.ok().build();
    }

    public static int drEvil(String s1, String s2) {
        return dist(s1.toCharArray(), s2.toCharArray());
    }

    private static int dist(char[] s1, char[] s2) {
        int[] prev = new int[s2.length + 1];

        for (int j = 0; j < s2.length + 1; j++) {
            prev[j] = j;
        }

        for (int i = 1; i < s1.length + 1; i++) {

            // calculate current line of distance matrix
            int[] curr = new int[s2.length + 1];
            curr[0] = i;

            for (int j = 1; j < s2.length + 1; j++) {
                int d1 = prev[j] + 1;
                int d2 = curr[j - 1] + 1;
                int d3 = prev[j - 1];
                if (s1[i - 1] != s2[j - 1]) {
                    d3 += 1;
                }

                curr[j] = Math.min(Math.min(d1, d2), d3);
            }
            prev = curr;
        }
        System.out.println("Results: " + prev[s2.length]);
        return prev[s2.length];

    }

}
