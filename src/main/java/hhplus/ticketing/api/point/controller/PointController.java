package hhplus.ticketing.api.point.controller;

import hhplus.ticketing.api.point.dto.PointRequest;
import hhplus.ticketing.api.point.dto.PointTransactionRequest;
import hhplus.ticketing.domain.point.models.PointTransaction;
import hhplus.ticketing.domain.point.models.PointType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PointController {

    @PostMapping("/points")
    void transactPoint(@RequestBody PointRequest pointRequest){


    }

    @GetMapping("/point_history")
    PointResponse viewPointHistory(PointTransactionRequest pointTransactionRequest){
        List<PointTransaction> pointHistory = List.of(
                new PointTransaction(1, 1, 100000, PointType.RECHARGE),
                new PointTransaction(2, 1, 80000, PointType.USE)
        );

        return new PointResponse(20000, pointHistory);

    }
}
