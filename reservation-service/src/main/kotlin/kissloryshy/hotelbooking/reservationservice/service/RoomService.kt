package kissloryshy.hotelbooking.reservationservice.service

import kissloryshy.hotelbooking.reservationservice.entity.dto.RoomCountDto
import kissloryshy.hotelbooking.reservationservice.entity.dto.RoomDto
import kissloryshy.hotelbooking.reservationservice.mapper.RoomMapper
import kissloryshy.hotelbooking.reservationservice.repository.RoomRepository
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class RoomService(
    private val roomRepository: RoomRepository
) {
    private val converter = Mappers.getMapper(RoomMapper::class.java)

    fun getCount(): RoomCountDto {
        return RoomCountDto(roomRepository.count())
    }

    fun getAll(page: Int, size: Int): List<RoomDto> {
        val pagination = PageRequest.of(page, size)
        return roomRepository.findAll(pagination).map { converter.toDto(it) }.toList()
    }

    fun getByNumber(roomNumber: Int): RoomDto? {
        return roomRepository.findByNumber(roomNumber)?.let { converter.toDto(it) }
    }

    fun create(roomDto: RoomDto): RoomDto {
        return converter.toDto(roomRepository.save(converter.toModel(roomDto)))
    }
}
