package kr.co.milionvolt.ifive.domain.notification;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChargingStatusDTO {
    private String id;
    private String userId;
    private String username;
    private Integer modelId;
    private double carBattery;
    private String carNumber;
    private Integer carId;
    private Integer reservationId;
    private double modelBattery;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer stationId;
    private String name;
    private String address;
    private Integer chargerId;
    private double pricePerKWh;
    private String chargerSpeed;

    private double totalPay;
    private String message;
}
