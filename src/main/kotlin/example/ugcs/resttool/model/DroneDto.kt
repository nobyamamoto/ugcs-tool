package example.ugcs.resttool.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DroneDto(
  val id : Int,
  val name : String,
  val platform : String,
  val serial : String
)


