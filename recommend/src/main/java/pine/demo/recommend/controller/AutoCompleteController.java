package pine.demo.recommend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pine.demo.recommend.controller.request.AutoCompleteSearchDio;
import pine.demo.recommend.service.AutoCompleteService;
import pine.demo.recommend.service.dto.ResponseDto;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("autoComplete")
@RestController("AutoCompleteController")
public class AutoCompleteController {
    private final AutoCompleteService autoCompleteService;

    @GetMapping(path = "/search")
    public ResponseDto<List<String>> search(
            @RequestParam String prefix,
            @RequestParam(defaultValue = "5") Integer topK
//            @RequestParam(required = false, defaultValue = "10") Integer searchDepth
    ){
        AutoCompleteSearchDio input = new AutoCompleteSearchDio();
        input.setPrefix(prefix);
        input.setTopK(topK);
//        input.setSearchDepth(searchDepth);

        return autoCompleteService.request(input);
    }

}