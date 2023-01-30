package sba.sms.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import sba.sms.dao.CourseI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

public class CourseService implements CourseI {

	@Override
	public void createCourse(Course course) {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			tx = session.beginTransaction();
			session.persist(course);
			tx.commit();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public Course getCourseById(int courseId) {
		Course course = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<Course> q = session.createQuery("from  Course e where e.id = :course_id", Course.class)
					.setParameter("course_id", courseId);
			course = q.getSingleResult();

		} catch (HibernateException ex) {
			ex.printStackTrace();

		} finally {
			session.close();
		}

		return course;
	}

	@Override
	public List<Course> getAllCourses() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<Course> q = session.createQuery("from  Course", Course.class);

			List<Course> courses = q.getResultList();
			// System.out.println(courses);
			return courses;

		} catch (HibernateException ex) {
			ex.printStackTrace();

		} finally {
			session.close();
		}

		return new ArrayList();
	}

}
