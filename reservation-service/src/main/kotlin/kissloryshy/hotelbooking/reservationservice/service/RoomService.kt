package kissloryshy.hotelbooking.reservationservice.service

import kissloryshy.hotelbooking.reservationservice.entity.Room
import kissloryshy.hotelbooking.reservationservice.entity.dto.RoomCountDto
import kissloryshy.hotelbooking.reservationservice.entity.dto.RoomDto
import kissloryshy.hotelbooking.reservationservice.mapper.RoomMapper
import kissloryshy.hotelbooking.reservationservice.repository.RoomRepository
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service

@Service
class RoomService(
    private val roomRepository: RoomRepository
) {
    private val converter = Mappers.getMapper(RoomMapper::class.java)
    fun getCount(): RoomCountDto {
        return RoomCountDto(roomRepository.count())
    }

    fun getAll(): List<Room> {
        return roomRepository.findAll()
    }

    fun getByNumber(roomNumber: Int): RoomDto? {
        return converter.toDto(roomRepository.findByNumber(roomNumber))
    }

    fun create(roomDto: RoomDto): RoomDto {
        return converter.toDto(roomRepository.save(converter.toModel(roomDto)))
    }
}