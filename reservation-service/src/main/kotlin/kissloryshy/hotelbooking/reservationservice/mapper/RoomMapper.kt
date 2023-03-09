package kissloryshy.hotelbooking.reservationservice.mapper

import kissloryshy.hotelbooking.reservationservice.entity.Room
import kissloryshy.hotelbooking.reservationservice.entity.dto.RoomDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface RoomMapper {

    fun toDto(room: Room): RoomDto

    @Mapping(target = "reservations", ignore = true)
    fun toModel(roomDto: RoomDto): Room

}
