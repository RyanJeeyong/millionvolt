package kr.co.milionvolt.ifive.controller.reservation;

import kr.co.milionvolt.ifive.domain.reservation.ReservationDTO;
import kr.co.milionvolt.ifive.service.reservation.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ReservationController {

    @Autowired
    private ReservationServiceImpl reservationServiceImpl;

    @PostMapping("/api/v1/reservation/{imp_uid}")
    public ResponseEntity<Map<String, Object>> reservation(@PathVariable String imp_uid, @RequestBody ReservationDTO reservationDTO) {
        Map<String, Object> response = new HashMap<>();

        System.out.println(reservationDTO);

        try {;
            reservationDTO.setImpUid(imp_uid);
            boolean success = reservationServiceImpl.saveReservation(reservationDTO);

            if (success) {
                response.put("message", "예약이 완료되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "이미 예약된 시간이 있습니다. 시간을 확인해주세요.");
                return ResponseEntity.status(400).body(response);
            }
        } catch (Exception e) {
            response.put("message", "예약 처리 중 오류가 발생했습니다.");
            return ResponseEntity.status(500).body(response);
        }
    }
}
