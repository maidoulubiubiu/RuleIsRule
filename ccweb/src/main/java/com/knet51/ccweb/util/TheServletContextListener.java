package com.knet51.ccweb.util;

import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.knet51.ccweb.jpa.entities.Student;
import com.knet51.ccweb.jpa.entities.Teacher;
import com.knet51.ccweb.jpa.entities.User;
import com.knet51.ccweb.jpa.entities.blog.BlogCategory;
import com.knet51.ccweb.jpa.entities.blog.BlogPost;
import com.knet51.ccweb.jpa.services.UserService;

/**
 * Application Lifecycle Listener implementation class
 * 
 */
public class TheServletContextListener implements ServletContextListener {
	
	@Autowired
	private UserService userService;
	
	private EntityManager em;
	/**
	 * Default constructor.
	 */
	public TheServletContextListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		
				
//		WebApplicationContext springContext = WebApplicationContextUtils
//				.getRequiredWebApplicationContext(sce.getServletContext());
//		springContext.getAutowireCapableBeanFactory().autowireBean(this);
//		
//		EntityManagerFactory factory = (EntityManagerFactory)springContext.getBean("entityManagerFactory");
//		logger.info(factory.toString());
//		this.em = factory.createEntityManager();
//		logger.info(em.toString());
//				
//		logger.info("userService==null "+(userService==null));
//		em.getTransaction().begin();
//		clearTestDataInDB();
//		setupTestData();		
//		em.getTransaction().commit();
	}
	@SuppressWarnings("unused")
	private void clearTestDataInDB() {
		destroyAllPosts();
		destroyTeachers();
		destroyAllUsers();
		destroyBlogCategory();
	}
	
	private void destroyAllUsers() {
		List<User> users = em.createQuery("select c from User c", User.class).getResultList();
		 for (User user : users) {
			em.remove(user);
		}
	}
	
	private void destroyBlogCategory() {
		List<BlogCategory> blogCategories = em.createQuery("select c from BlogCategory c", BlogCategory.class).getResultList();
		 for (BlogCategory category : blogCategories) {
			em.remove(category);
		}
	}
	
	private void destroyTeachers() {
		List<Teacher> teachers = em.createQuery("select c from Teacher c", Teacher.class).getResultList();
		 for (Teacher teacher : teachers) {
			em.remove(teacher);
		}
	}
	@SuppressWarnings("unused")
	private void destroyStudents() {
		List<Student> students = em.createQuery("select c from Student c", Student.class).getResultList();
		for (Student student : students) {
			em.remove(student);
		}
	}

	private void destroyAllPosts() {
		 List<BlogPost> blogPosts = em.createQuery("select c from BlogPost c", BlogPost.class).getResultList();
		 for (BlogPost blogPost : blogPosts) {
			em.remove(blogPost);
		}
	}
	@SuppressWarnings("unused")
	private void setupTestData() {
		createTeacherAndBlogPosts("steve@apple.com","steve");
		createTeacherAndBlogPosts("tim@apple.com", "tim");
		createTeacherAndBlogPosts("bill@microsoft.com", "bill");
		createTeacherAndBlogPosts("mark@facebook.com", "mark");
		createTeacherAndBlogPosts("reggie@nintendo.com", "reggie");
		createUserForTest("test@163.com","123");
	}
	
	private void createUserForTest(String email, String password){
		User user = new User(email,password,"user",1);
		user.setRandomUrl("pass");
		user.setPhoto_url("http://www.fdsm.fudan.edu.cn/UserPhotos/eb58f880-a258-48a4-aa9c-b0e7700c2abd_thumbnail.jpg");
		em.persist(user);
	}
	
	private void createTeacherAndBlogPosts(String email, String password) {
		User user = new User(email,password,"teacher",1);
		user.setName(user.getPassword());
		user.setGender("男");
		user.setRandomUrl("pass");
		user.setPhoto_url("http://www.fdsm.fudan.edu.cn/UserPhotos/eb58f880-a258-48a4-aa9c-b0e7700c2abd_thumbnail.jpg");
		em.persist(user);
		
		Teacher teacher = new Teacher(user);
		teacher.setId(user.getId());
//		teacher.setRole(0);
		em.persist(teacher);
		
		for (int i = 1; i < 6; i++) {
			BlogCategory category = new BlogCategory("category"+(i+1)+"_teacher"+teacher.getId());
			category.setAuthor(teacher);
			em.persist(category);
		}
		
		TypedQuery<BlogCategory> query = em.createQuery("select c from BlogCategory c where c.author = :author", BlogCategory.class);
		query.setParameter("author", teacher);
		List<BlogCategory> blogCategories = query.getResultList();
		BlogCategory blogCategory = blogCategories.get(new Random().nextInt(blogCategories.size()));
		for (int i = 1; i < 6; i++) {
			BlogPost post = new BlogPost(teacher, blogCategory, "title"+i, teacher.toString()+i);
			em.persist(post);	
		}		
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
	}
}
