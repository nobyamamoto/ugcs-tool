package example.ugcs.resttool.presentation

import example.ugcs.resttool.model.MissionDto
import example.ugcs.resttool.model.PayloadProfileDto
import example.ugcs.resttool.config.UgcsConfig
import example.ugcs.resttool.model.DroneDto
import example.ugcs.resttool.model.VehicleProfileDto
import com.ugcs.ucs.client.Client
import com.ugcs.ucs.client.ClientSession
import com.ugcs.ucs.proto.DomainProto
import com.ugcs.ucs.proto.MessagesProto
import org.springframework.web.bind.annotation.*
import java.net.InetSocketAddress
import java.time.LocalDateTime
import java.time.ZoneOffset


class CustomClientSession(client: Client?) : ClientSession(client) {
  fun getClientId() : Int{
    return this.clientId
  }
}

@RestController
@RequestMapping("/ugcs")
class DevController(
  val ugcsConfig: UgcsConfig
) {

  companion object {
    val log = org.slf4j.LoggerFactory.getLogger(this::class.java.enclosingClass)!!
  }


  fun info() {
    log.info("host = ${this.ugcsConfig.serverHost}")
    log.info("port = ${this.ugcsConfig.serverPort}")
  }

  fun server(): InetSocketAddress {
    return InetSocketAddress(
      this.ugcsConfig.serverHost, this.ugcsConfig.serverPort.toInt())
  }

  fun login( client : Client ) : CustomClientSession {
    client.connect()
    val session = CustomClientSession(client)
    session.authorizeHci();
    session.login(this.ugcsConfig.login, this.ugcsConfig.password)
    return session
  }

  @GetMapping("/license")
  fun license(): String {
    info()
    val serverAddress = server()
    try {
      Client(serverAddress).use { client ->
        val session = login(client)
        return session.getObjectList(DomainProto.License::class.java, false)
          .first().license.activationCode
      }
    } catch (e: Exception) {
      e.printStackTrace()
      throw e
    }
  }

  @GetMapping("/camera_profiles")
  fun cameraProfiles(
    @RequestParam(required = false, defaultValue = "false") isParam : Boolean
  ): List<PayloadProfileDto> {
    info()
    val serverAddress = server()
    try {
      Client(serverAddress).use { client ->
        val session = login(client)
        return session.getObjectList(DomainProto.PayloadProfile::class.java, false)
          .map {
            val param = if(isParam) it.payloadProfile.parametersList
                        .associateBy( { profile -> profile.type.name }, { profile -> profile.value })
                        else null
              PayloadProfileDto(
                      id = it.payloadProfile.id,
                      name = it.payloadProfile.name,
                      parameters = param
              )
          }.toList()
      }
    } catch (e: Exception) {
      e.printStackTrace()
      throw e
    }
  }

  @GetMapping("/drone_profiles")
  fun droneProfiles(
    @RequestParam(required = false, defaultValue = "false") isParam : Boolean
  ): List<VehicleProfileDto> {
    info()
    val serverAddress = server()
    try {
      Client(serverAddress).use { client ->
        val session = login(client)
        return session.getObjectList(DomainProto.VehicleProfile::class.java, false)
          .map {
            val param = if(isParam) it.vehicleProfile.parametersList
              .associateBy( { profile -> profile.type.name }, { profile -> profile.value })
            else null
              VehicleProfileDto(
                      id = it.vehicleProfile.id,
                      name = it.vehicleProfile.name,
                      parameters = param
              )
          }.toList()
      }
    } catch (e: Exception) {
      e.printStackTrace()
      throw e
    }
  }

  @GetMapping("/drones")
  fun drones(
    @RequestParam(required = false, defaultValue = "") name : String
  ): List<DroneDto> {
    info()
    val serverAddress = server()
    try {
      Client(serverAddress).use { client ->
        val session = login(client)
        return session.getObjectList(DomainProto.Vehicle::class.java, true)
          .filter { it.vehicle.name.contains( name ) }
          .map {
              DroneDto(
                      id = it.vehicle.id,
                      name = it.vehicle.name,
                      serial = it.vehicle.serialNumber,
                      platform = it.vehicle.platform.alias
              )
          }.toList()
      }
    } catch (e: Exception) {
      e.printStackTrace()
      throw e
    }
  }


  @GetMapping("/missions")
  fun missions(
    @RequestParam(required = false, defaultValue = "") name : String
  ): List<MissionDto> {
    info()
    val serverAddress = server()
    try {
      Client(serverAddress).use { client ->
        val session = login(client)
        return session.getObjectList(DomainProto.Mission::class.java, false)
          .filter { it.mission.name.contains( name ) }
          .take(25) // limit 25
          .map {
              MissionDto(
                      id = it.mission.id,
                      name = it.mission.name,
                      created = LocalDateTime.ofEpochSecond(it.mission.creationTime / 1000, 0, ZoneOffset.ofHours(9)).toString()
              )
          }.toList()
      }
    } catch (e: Exception) {
      e.printStackTrace()
      throw e
    }
  }

  @GetMapping("/mission/export/{name}")
  fun missionExport(
    @PathVariable name : String
  ): String {
    info()
    val serverAddress = server()
    try {
      Client(serverAddress).use { client ->
        val session = login(client)

        val mission = session.getObjectList(DomainProto.Mission::class.java, false)
          ?.first { it.mission.name.contains(name) }
          ?: return "not found"

        val res = client.execute<MessagesProto.ExportMissionResponse>(
          MessagesProto.ExportMissionRequest.newBuilder()
            .setMission(mission.mission)
            .setClientId(session.getClientId())
            .build()
        )
        return res.missionData.toStringUtf8()
      }
    } catch (e: Exception) {
      e.printStackTrace()
      throw e
    }
  }
}
