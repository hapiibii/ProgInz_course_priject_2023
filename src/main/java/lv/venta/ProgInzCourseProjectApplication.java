package lv.venta;

import java.io.File;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import lv.venta.models.Comment;
import lv.venta.models.News;
import lv.venta.models.Course;
import lv.venta.models.Degree;

import lv.venta.models.Documents;

import lv.venta.models.Thesis;
import lv.venta.models.StudioProgramm;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.models.users.Role;
import lv.venta.models.users.Student;
import lv.venta.models.users.User;
import lv.venta.models.users.UserAuthority;
import lv.venta.repos.IAcademicPersonelRepo;
import lv.venta.repos.ICommentRepo;
import lv.venta.repos.ICourseRepo;

import lv.venta.repos.IDocumentsRepo;

import lv.venta.repos.IPersonRepo;
import lv.venta.repos.IStudentRepo;
import lv.venta.repos.IStudioProgrammRepo;
import lv.venta.repos.IThesisRepo;
import lv.venta.repos.IUserAuthorityRepo;
import lv.venta.repos.IUserRepo;

@SpringBootApplication
public class ProgInzCourseProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgInzCourseProjectApplication.class, args);
	}
	
	@Bean
	public PasswordEncoder passwordEncoderSimple() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public CommandLineRunner textModelLayer(IUserRepo userRepo, IPersonRepo personRepo, IStudentRepo studentRepo, IAcademicPersonelRepo personelRepo, ICourseRepo courseRepo, IThesisRepo thesisRepo, ICommentRepo commentRepo, IDocumentsRepo documentRepo, IUserAuthorityRepo authorityRepo) {

		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				//User us1 = new User("123", "karina.krinkele@venta.lv"); //pasn
				//User us2 = new User("123", "karlis.immers@venta.lv"); //pasn
				//User us3 = new User("123", "baiba.egle@venta.lv"); //st
				//User us4 = new User("123", "marina.satura@venta.lv"); //st
				//userRepo.save(us1);
				//userRepo.save(us2);
				//userRepo.save(us3);
				//userRepo.save(us4);			
				User user1 = new User("Test", "Test", passwordEncoderSimple().encode("123"));
				userRepo.save(user1);
				
				User user2 = new User("Janis", "Janis", passwordEncoderSimple().encode("111"));
				userRepo.save(user2);
				
				UserAuthority auth1 = new UserAuthority("USER");
				UserAuthority auth2 = new UserAuthority("ADMIN");
				auth1.addUser(user1);
				auth2.addUser(user2);
				auth2.addUser(user1);
				authorityRepo.save(auth1);
				authorityRepo.save(auth2);
				
				user1.addAuthority(auth1);
				user1.addAuthority(auth2);
				user2.addAuthority(auth2);
				userRepo.save(user1);
				userRepo.save(user2);
				
				
				Course c1 = new Course("Javaa", 4);
				Course c2 = new Course("Datastr", 2);
				courseRepo.save(c1);
				courseRepo.save(c2);
				
				// user vietā ieliku null
				AcademicPersonel ac1 = new AcademicPersonel("Karina", "Skirmante", "121212-11111", Role.Akademiskais_personals, null, Degree.mg);
				AcademicPersonel ac2 = new AcademicPersonel("Karlis", "Immers", "121213-11151", Role.Akademiskais_personals, null, Degree.mg);
				personelRepo.save(ac1);
				personelRepo.save(ac2);
				
				// user vietā ieliku null
				Student s1 = new Student("Baiba", "Egle", "123123-12312", Role.Students, null, "22000043", false);
				Student s2 = new Student("Marina", "Satura", "123153-12322", Role.Students, null, "22000089", true);
				
				
				s2.addDebtCourse(c1);
				s2.addDebtCourse(c2);
				
				studentRepo.save(s1);
				studentRepo.save(s2);
				
				c1.addStudent(s2);
				c2.addStudent(s2);
				
				courseRepo.save(c1);
				courseRepo.save(c2);
				
				
				Thesis thesis1 = new Thesis("Title 1 EN", "Title 1 LV", "Goal 1", "Tasks 1", s1, ac1);
				Thesis thesis2 = new Thesis("Title 2 EN", "Title 2 LV", "Goal 2", "Tasks 2", s2, ac2);
				thesis1.addReviewer(ac2);
				thesis2.addReviewer(ac1);
				thesisRepo.save(thesis1);
				thesisRepo.save(thesis2);
				
				

		
				ac1.addThesisForReviews(thesis2);
				ac2.addThesisForReviews(thesis1);
				personelRepo.save(ac1);
				personelRepo.save(ac2);
				
				Comment com1 = new Comment("Nepareizs nosaukums", ac2, thesis1);
				Comment com2 = new Comment("Viss cotka", ac1, thesis2);
				commentRepo.save(com1);
				commentRepo.save(com2);
				
				LocalDate today = LocalDate.now();
				LocalDate tomorrow = LocalDate.now().plusDays(1);
				News new1 = new News ("Parbaude", "Seit ir parbaude vai var pievienot jaunumu", today, tomorrow);

				
				
				
				String filePath = "files/iesniegums1.odt";
				File file = new File(filePath);
				Documents doc1 = new Documents("Test", file);
				documentRepo.save(doc1);
				
				String filePath2 = "files/dokuments1.odt";
				File file2 = new File(filePath);
				Documents doc2 = new Documents("Test", file2);
				documentRepo.save(doc2);
				

			}
		};
	}

}
