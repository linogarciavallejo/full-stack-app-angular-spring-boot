package com.linkedin.learning.linkedinlearningfullstackappangularspringboot.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.entity.RoomEntity;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.model.Links;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.model.Self;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.model.response.ReservableRoomResponse;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.model.response.ReservationResponse;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.rest.ResourceConstants;

@Component
public class RoomEntityToReservableRoomResponseConverter implements Converter<RoomEntity, ReservableRoomResponse> {

	@Override
	public ReservableRoomResponse convert(RoomEntity source) {
		// TODO Auto-generated method stub
		
		ReservableRoomResponse reservationResponse = new ReservableRoomResponse();
		
		reservationResponse.setId(source.getId());
		reservationResponse.setRoomNumber(source.getRoomNumber());
		reservationResponse.setPrice(Integer.valueOf(source.getPrice()));
		
		
		Links links = new Links();
		Self self = new Self();
		
		self.setRef(ResourceConstants.ROOM_RESERVATION_V1 + "/" + source.getId());
		links.setSelf(self);
		
		reservationResponse.setLinks(links);
		
		
		return reservationResponse;
	}
	

}
