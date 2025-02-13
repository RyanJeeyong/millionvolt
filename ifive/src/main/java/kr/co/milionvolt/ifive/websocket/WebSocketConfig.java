package kr.co.milionvolt.ifive.websocket;

import kr.co.milionvolt.ifive.service.charger.ChargerService;
import kr.co.milionvolt.ifive.service.charging.ChargingStatusSerivce;
import kr.co.milionvolt.ifive.service.reservation.ReservationRedisService;
import kr.co.milionvolt.ifive.service.user.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChargingStatusSerivce chargingStatusSerivce;

    private final UserService userService;

    private final ReservationRedisService reservationRedisService;

    private final ChargerService chargerService;

    public WebSocketConfig(ChargingStatusSerivce chargingStatusSerivce, UserService userService, ReservationRedisService reservationRedisService, ChargerService chargerService) {
        this.chargingStatusSerivce = chargingStatusSerivce;
        this.userService = userService;
        this.reservationRedisService = reservationRedisService;
        this.chargerService = chargerService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChargingWebSocketHandler(chargingStatusSerivce), "/charging")
                .setAllowedOrigins("*");

        registry.addHandler(new AlarmWebSocketHandler(userService, reservationRedisService), "/alarm")
                .setAllowedOrigins("*");

        registry.addHandler(new ChargeStateChangeWebSocketHandler(chargerService), "/chargerstatus")
                .setAllowedOrigins("*");


    }

}
