package sba.sms.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

public class StudentService implements StudentI {

	@Override
	public List<Student> getAllStudents() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<Student> q = session.createQuery("from  Student", Student.class);

			List<Student> students = q.getResultList();
			//System.out.println(students);
			return students;

		} catch (HibernateException ex) {
			ex.printStackTrace();

		} finally {
			session.close();
		}

		return new ArrayList();
	}

	@Override
	public void createStudent(Student student) {

		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			tx = session.beginTransaction();
			session.persist(student);
			tx.commit();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}

	}

	@Override
	public Student getStudentByEmail(String email) {
		Student e = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			//Query<Student> q = session.createQuery("from Student", Student.class);
			 e = session.get(Student.class, email);
			
		//	e = q.getSingleResult();

		} catch (HibernateException ex) {
			ex.printStackTrace();

		} finally {
			session.close();
		}

		return e;
	}

	@Override
	public boolean validateStudent(String email, String password) {
		boolean result = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			/*
			Query<Student> q = session.createQuery("from  Student e where e.email = :email_id", Student.class)
					.setParameter("email_id", email);
					*/
			Student e = session.get(Student.class, email);
		

			if (e.getPassword().equals(password)) {
				result = true;
			} else
				result = false;

		} catch (HibernateException ex) {
			ex.printStackTrace();

		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public void registerStudentToCourse(String email, int courseId) {
		
		Transaction tx = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Student s = session.get(Student.class, (email));
            Course c = session.get(Course.class, courseId);
            tx = session.beginTransaction();

            Set<Student>  students = c.getStudents();
            if(!students.contains(s)) {
            	s.addCourse(c);
            	session.merge(s);
            }
            tx.commit(); 

        } catch (HibernateException ex) {
            ex.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }

    }
	

	@Override
	public List<Course> getStudentCourses(String email) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Course> result = null;
		try {
			//Query<Course> q = session.createQuery("from  Course", Course.class);
			Student s = session.get(Student.class, email);
          result = (List<Course>) s.getCourses();

			//List<Course> courses = q.getResultList();
			//System.out.println(courses);
			//return courses;

		} catch (HibernateException ex) {
			ex.printStackTrace();

		} finally {
			session.close();
		}
return result;
		//return new ArrayList();
	}

}
