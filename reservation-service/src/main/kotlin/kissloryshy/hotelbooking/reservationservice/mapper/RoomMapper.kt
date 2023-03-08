package kissloryshy.hotelbooking.reservationservice.mapper

import kissloryshy.hotelbooking.reservationservice.entity.Room
import kissloryshy.hotelbooking.reservationservice.entity.dto.RoomDto
import org.mapstruct.Mapper

@Mapper
interface RoomMapper {
    fun toDto(room: Room): RoomDto
    fun toModel(roomDto: RoomDto): Room
}