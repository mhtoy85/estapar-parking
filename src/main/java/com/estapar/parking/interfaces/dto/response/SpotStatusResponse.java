package com.estapar.parking.interfaces.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record SpotStatusResponse (
    boolean ocupied,
    @JsonProperty("entry_time")
    OffsetDateTime entryTime,
    @JsonProperty("time_parked")
    OffsetDateTime timeParked){
}
