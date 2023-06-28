package lv.venta.services;

import lv.venta.models.Degree;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.models.users.Role;
import lv.venta.models.users.User;

public interface IAcademicPersonelService {
	void createAcademicPersonel(AcademicPersonel academicPersonel);
	void updateAcademicPersonelWithPersoncode(long idperson, String name, String surname, String personcode, Role role, User user, Degree degree) throws Exception;
	void updateAcademicPersonelWithoutPersoncode(long idperson, String name, String surname, Role role, User user, Degree degree) throws Exception;
	void deleteAcademicPersonel(long idperson) throws Exception;
}
