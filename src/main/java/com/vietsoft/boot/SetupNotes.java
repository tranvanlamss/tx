//package com.vietsoft.boot;
//
//import java.util.Date;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.vietsoft.model.notebook.Note;
//import com.vietsoft.repo.NoteRepo;
//
//
//@Configuration
//public class SetupNotes {
//	static final Logger logger = LoggerFactory.getLogger(SetupNotes.class);
//	@Value("${server.properties.boot.enable}")
//	boolean bootEnable;
//	@Bean
//	CommandLineRunner loadNotes(NoteRepo repo) {
//		return args -> {
//			if (bootEnable) {
//				logger.info("loadNotes.........");
//				long size = repo.count();
//				if (size > 0) {
//					logger.info("loadNotes exists {}", size);
//					return;
//				}
//
//				for (int i = 0; i < 60; i++) {
//					Note note = new Note();
//					note.setName("name_" + i);
//					note.setContent("note content..." + i);
//					note.setCreatedTime(new Date());
//					repo.save(note);
//				}
//			}
//		};
//	}
//}
