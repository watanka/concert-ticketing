package hhplus.ticketing.watingqueue.aop;

import org.springframework.stereotype.Service;


@Service
public class TestAopService {

    @TokenValidation
    public String sampleRun(){
        return "sample run";
    }
}
