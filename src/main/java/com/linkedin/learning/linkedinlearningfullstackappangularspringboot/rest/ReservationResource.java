package com.linkedin.learning.linkedinlearningfullstackappangularspringboot.rest;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.converter.RoomEntityToReservableRoomResponseConverter;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.entity.ReservationEntity;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.entity.RoomEntity;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.model.request.ReservationRequest;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.model.response.ReservableRoomResponse;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.model.response.ReservationResponse;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.repository.PageableRoomRepository;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.repository.ReservationRepository;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.repository.RoomRepository;

@RestController
@RequestMapping(ResourceConstants.ROOM_RESERVATION_V1)
@CrossOrigin
public class ReservationResource {

	@Autowired
	PageableRoomRepository pageableRoomRepository;
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	ConversionService conversionService;

	@Autowired
	RoomEntityToReservableRoomResponseConverter converter;
	

	@RequestMapping(path = "", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Page<ReservableRoomResponse> getAvailableRooms(
			@RequestParam(value = "checkin")
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate checking, 
			@RequestParam(value = "checkout")
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate checkout, 
			Pageable pageable) {
		
		Page<RoomEntity> roomEntityList = pageableRoomRepository.findAll(pageable);
		
		return roomEntityList.map(converter::convert);
	}
	
	@RequestMapping(path = "/{roomId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<RoomEntity> getRoomById(
			@PathVariable
			Long roomId) {
		
		Optional<RoomEntity> roomEntity = roomRepository.findById(roomId);
		
		return new ResponseEntity<>(roomEntity.get(), HttpStatus.OK);
	}
	
	@RequestMapping(path = "", method= RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReservationResponse> createReservation(
			@RequestBody
			ReservationRequest reservationRequest) {
	
		ReservationEntity reservationEntity = conversionService.convert(reservationRequest, ReservationEntity.class);
		reservationRepository.save(reservationEntity);
		
		Optional<RoomEntity> roomEntity = roomRepository.findById(reservationRequest.getRoomId());
		roomEntity.get().addReservationEntity(reservationEntity);
		roomRepository.save(roomEntity.get());
		reservationEntity.setRoomEntity(roomEntity.get());

		/*Optional<RoomEntity> roomEntity = roomRepository.findById(reservationRequest.getId());
		reservationEntity.setRoomEntity(roomEntity.get());
		roomEntity.get().addReservationEntity(reservationEntity);
		roomRepository.save(roomEntity.get());*/

		ReservationResponse reservationResponse = conversionService.convert(reservationEntity, ReservationResponse.class);
		
		return new ResponseEntity<>(reservationResponse, HttpStatus.CREATED);
	}
	
	@RequestMapping(path = "", method= RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReservationResponse> updateReservation(
			@RequestBody
			ReservationRequest reservationRequest) {
	
		return new ResponseEntity<>(new ReservationResponse(), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/{reservationId}", method= RequestMethod.DELETE)
	public ResponseEntity<Void> deleteReservation(
			@PathVariable
			long reservationId) {
	
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}	
}
