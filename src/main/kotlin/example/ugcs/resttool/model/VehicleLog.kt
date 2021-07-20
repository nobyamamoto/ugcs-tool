package com.sensyn.ugcs.devtool.model

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class VehicleLog(
  val time : LocalDateTime,
  val message : String,
  val level : String
)


