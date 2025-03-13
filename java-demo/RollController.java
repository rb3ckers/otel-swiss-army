package otel;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.GlobalOpenTelemetry;

@RestController
public class RollController {
  private static final Logger logger = LoggerFactory.getLogger(RollController.class);
  private final Tracer tracer;
  private final Meter meter;
  private final LongCounter rollCounter;

  public RollController() {
    tracer = GlobalOpenTelemetry.getTracer(this.getClass().getName());
    meter = GlobalOpenTelemetry.getMeter(this.getClass().getName());
    rollCounter =
        meter
            .counterBuilder("java.demo.roll.counter")
            .setDescription("A count of dice roles")
            .build();
  }

  @GetMapping("/rolldice")
  public String index(@RequestParam("player") Optional<String> player) {
    var span = tracer.spanBuilder("roll-the-dice").startSpan();
    
    int result = this.getRandomNumber(1, 6);
    if (player.isPresent()) {
      logger.info("{} is rolling the dice: {}", player.get(), result);
      rollCounter.add(1, Attributes.of("player", player.get()));
    } else {
      logger.info("Anonymous player is rolling the dice: {}", result);
      rollCounter.add(1);
    }
    
    span.setAttribute("result", result);
    span.end();
    return Integer.toString(result);
  }

  public int getRandomNumber(int min, int max) {
    return ThreadLocalRandom.current().nextInt(min, max + 1);
  }
}
