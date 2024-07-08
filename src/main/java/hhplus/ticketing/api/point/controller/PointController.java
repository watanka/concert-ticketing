package hhplus.ticketing.api.point.controller;

import hhplus.ticketing.api.point.dto.PointRequest;
import hhplus.ticketing.api.point.dto.PointTransactionRequest;
import hhplus.ticketing.api.point.facade.PointFacade;
import hhplus.ticketing.domain.point.components.PointService;
import hhplus.ticketing.domain.point.models.PointTransaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="포인트", description="포인트 충전/포인트 내역 조회")
@RequiredArgsConstructor
@RestController
public class PointController {

    @Autowired
    private final PointFacade pointFacade;

    @Autowired
    private final PointService pointService;


    @Operation(summary="포인트 사용/충전", description="포인트를 사용하거나 충전합니다.")
    @PostMapping("/points")
    @ApiResponse(responseCode = "200", description = "OK")
    void transactPoint(@RequestBody PointRequest pointRequest){

        pointFacade.transact(pointRequest.userId(), pointRequest.toPoint());
    }

    @Operation(summary="포인트내역 조회", description="포인트 내역을 조회합니다.")
    @GetMapping("/point_history")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array=@ArraySchema(schema=@Schema(implementation = PointTransaction.class))))
    List<PointTransaction> viewPointHistory(PointTransactionRequest pointTransactionRequest){

        return pointService.queryTransactions(pointTransactionRequest.userId());
//        List<PointTransaction> pointHistory = List.of(
//                new PointTransaction(1, 1, LocalDateTime.now(), 100000, PointType.RECHARGE),
//                new PointTransaction(2, 1, LocalDateTime.now(),80000, PointType.USE)
//        );
//
//        return new PointHistoryResponse(20000, pointHistory);

    }
}
