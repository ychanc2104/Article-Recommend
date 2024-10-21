package pine.demo.recommend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pine.demo.recommend.controller.request.AutoCompleteSearchDio;
import pine.demo.recommend.service.dto.MetaDto;
import pine.demo.recommend.service.dto.ResponseDto;
import pine.demo.recommend.worker.ArticleTrieWorker;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AutoCompleteService extends BaseService<AutoCompleteSearchDio, List<String>> {
    private final ArticleTrieWorker articleTrieWorker;

    @Override
    protected List<String> processData(AutoCompleteSearchDio input) {
        return articleTrieWorker.searchTopK(input.getPrefix(), input.getTopK());
    }

    @Override
    protected ResponseDto<List<String>> generateResult(List<String> data, Date startTime) {
        ResponseDto<List<String>> result = new ResponseDto<>();
        result.setData(data);
        result.setMeta(MetaDto.at(startTime));
        result.setSuccess(true);

        return result;
    }
}
