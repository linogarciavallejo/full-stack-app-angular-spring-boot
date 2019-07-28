package com.linkedin.learning.linkedinlearningfullstackappangularspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.entity.RoomEntity;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.repository.RoomRepository;

@Component
public class H2Bootstrap implements CommandLineRunner {
	
	@Autowired
	RoomRepository roomRepository;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
	
		System.out.println("Bootstrapping data:");
		
		roomRepository.save(new RoomEntity(405, "200"));
		roomRepository.save(new RoomEntity(406, "250"));
		roomRepository.save(new RoomEntity(407, "300"));
		
		
		Iterable<RoomEntity> itr = roomRepository.findAll();

		System.out.println("Printing out data:");

		for(RoomEntity room : itr) {
			System.out.println(room.getRoomNumber());
			System.out.println(room.getId());
		}
	}

}
