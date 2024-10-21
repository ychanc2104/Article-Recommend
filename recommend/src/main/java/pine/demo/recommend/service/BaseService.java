package pine.demo.recommend.service;


import lombok.extern.slf4j.Slf4j;
import pine.demo.recommend.service.dto.ResponseDto;

import java.util.Date;

@Slf4j
public abstract class BaseService<RequestInput, ResponseOutput> {

    public ResponseDto<ResponseOutput> request(RequestInput input) {
        Date startTime = new Date();
        ResponseOutput processedData = processData(input);;
        ResponseDto<ResponseOutput> result = generateResult(processedData, startTime);

        return result;
    }

    protected abstract ResponseOutput processData(RequestInput input);

    protected abstract  ResponseDto<ResponseOutput> generateResult(ResponseOutput data, Date startTime);
}