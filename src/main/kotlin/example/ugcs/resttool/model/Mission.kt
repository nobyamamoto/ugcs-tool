package example.ugcs.resttool.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MissionDto(
  val id : Int,
  val name : String,
  val created : String
)


