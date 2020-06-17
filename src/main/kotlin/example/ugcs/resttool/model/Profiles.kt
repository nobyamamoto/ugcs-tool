package example.ugcs.resttool.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PayloadProfileDto(
  val id : Int,
  val name : String,
  val parameters: Map<String, Double>? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class VehicleProfileDto(
  val id : Int,
  val name : String,
  val parameters: Map<String, Double>? = null
)


