package lv.venta;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lv.venta.models.Comment;
import lv.venta.models.Course;
import lv.venta.models.Degree;
import lv.venta.models.Thesis;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.models.users.Student;
import lv.venta.models.users.User;
import lv.venta.repos.IAcademicPersonelRepo;
import lv.venta.repos.ICommentRepo;
import lv.venta.repos.ICourseRepo;
import lv.venta.repos.IPersonRepo;
import lv.venta.repos.IStudentRepo;
import lv.venta.repos.IThesisRepo;
import lv.venta.repos.IUserRepo;

@SpringBootApplication
public class ProgInzCourseProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgInzCourseProjectApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner textModelLayer(IUserRepo userRepo, IPersonRepo personRepo, IStudentRepo studentRepo, IAcademicPersonelRepo personelRepo, ICourseRepo courseRepo, IThesisRepo thesisRepo, ICommentRepo commentRepo) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				User us1 = new User("123", "karina.krinkele@venta.lv"); //pasn
				User us2 = new User("123", "karlis.immers@venta.lv"); //pasn
				User us3 = new User("123", "baiba.egle@venta.lv"); //st
				User us4 = new User("123", "marina.satura@venta.lv"); //st
				userRepo.save(us1);
				userRepo.save(us2);
				userRepo.save(us3);
				userRepo.save(us4);
				
				Course c1 = new Course("Javaa", 4);
				Course c2 = new Course("Datastr", 2);
				courseRepo.save(c1);
				courseRepo.save(c2);
				
				AcademicPersonel ac1 = new AcademicPersonel("Karina", "Skirmante", "121212-11111", us1, Degree.mg);
				AcademicPersonel ac2 = new AcademicPersonel("Karlis", "Immers", "121213-11151", us2, Degree.mg);
				personelRepo.save(ac1);
				personelRepo.save(ac2);
				
				Student s1 = new Student("Baiba", "Egle", "123123-12312", us3, "22000043", false);
				Student s2 = new Student("Marina", "Satura", "123153-12322", us4, "22000089", true);
				
				
				s2.addDebtCourse(c1);
				s2.addDebtCourse(c2);
				
				studentRepo.save(s1);
				studentRepo.save(s2);
				
				c1.addStudent(s2);
				c2.addStudent(s2);
				
				courseRepo.save(c1);
				courseRepo.save(c2);
				
				Thesis th1 = new Thesis("Sistemas izstrade", "Developement of System", "Development", "1...2.3..4", s1, ac1);
				Thesis th2 = new Thesis("Petijums", "Research", "Research", "1...2.3..4", s2, ac2);
				th1.addReviewer(ac2);
				th2.addReviewer(ac1);
				thesisRepo.save(th1);
				thesisRepo.save(th2);
				ac1.addThesisForReviews(th2);
				ac2.addThesisForReviews(th1);
				personelRepo.save(ac1);
				personelRepo.save(ac2);
				
				Comment com1 = new Comment("Nepareizs nosaukums", ac2, th1);
				Comment com2 = new Comment("Viss cotka", ac1, th2);
				commentRepo.save(com1);
				commentRepo.save(com2);
			}
		};
	}

}
