package com.knet51.ccweb.jpa.services.promotion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knet51.ccweb.controllers.common.defs.GlobalDefs;
import com.knet51.ccweb.jpa.entities.User;
import com.knet51.ccweb.jpa.entities.courses.Course;
import com.knet51.ccweb.jpa.services.FriendsRelateService;
import com.knet51.ccweb.jpa.services.UserCourseService;
import com.knet51.ccweb.jpa.services.UserService;
import com.knet51.ccweb.jpa.services.course.CourseService;

@Transactional
@Service("UserRecommendService")
public class UserRecommendServiceImpl implements UserRecommendService {

	@Autowired
	private UserService userService;
	@Autowired
	private FriendsRelateService friendsService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private UserCourseService userCourseService;

	// key logic, first role "friendRole" means the friends of yours who are in
	// friendRole, the second role "targetRole" means the friends of your
	// friends list, which get from the first time search, in that targetRole.
	private List<User> getRandomUsersFriends(String friendRole,
			String targetRole, Long id) {
		User theUser = userService.findOne(id);
		List<User> userList = new ArrayList<User>();
		List<User> hostList = friendsService.getAllHostInfo(id, friendRole);
		Set<User> userSet = new HashSet<User>();
		for (User user : hostList) {
			userSet.addAll(friendsService.getAllHostInfo(user.getId(),
					targetRole));
			userSet.addAll(friendsService.getAllFansInfo(user.getId(),
					targetRole));
		}
		boolean hasme = userSet.remove(theUser);
		System.out.println(hasme);
		userList.addAll(userSet);
		for (User user : userList) {
			System.out.println(user.getId());
		}
		Collections.shuffle(userList);
		return userList;
	}

	private List<User> getRandomUsersFriends(String friendRole,
			String targetRole, Long id, int count) {
		List<User> userList = new ArrayList<User>();
		if (count > 0) {
			userList = getRandomUsersFriends(friendRole, targetRole, id);
			if (userList.size() > count) {
				return userList.subList(0, count);
			} else {
				return userList;
			}
		} else {
			return null;
		}
	}

	private List<Course> getRandomTeacherCourses(Long id) {
		List<Course> courseList = new ArrayList<Course>();
		List<User> hostList = friendsService.getAllHostInfo(id, "teacher");

		for (User user : hostList) {
			courseList.addAll(courseService
					.getAllTeacherCourseByUseridAndPublish(user.getId(),
							GlobalDefs.PUBLISH_NUM_ADMIN_FRONT));
		}
		Collections.shuffle(courseList);
		return courseList;
	}

	private List<Course> getRandomTeacherCourses(Long id, int count) {
		List<Course> courseList = new ArrayList<Course>();
		if (count > 0) {
			courseList = getRandomTeacherCourses(id);
			if (courseList.size() > count) {
				return courseList.subList(0, count);
			} else {
				return courseList;
			}
		} else {
			return null;
		}
	}

	private List<Course> getRandomUserCourses(Long id) {
		List<Course> courseList = new ArrayList<Course>();
		Set<Course> userCourseSet = new HashSet<Course>();
		List<User> hostList = friendsService.getAllHostInfo(id, "user");

		for (User user : hostList) {
			userCourseSet.addAll(userCourseService.findAllCourseByUserId(user
					.getId()));
		}
		courseList.addAll(userCourseSet);
		Collections.shuffle(courseList);
		return courseList;
	}

	private List<Course> getRandomUserCourses(Long id, int count) {
		List<Course> courseList = new ArrayList<Course>();
		if (count > 0) {
			courseList = getRandomUserCourses(id);
			if (courseList.size() > count) {
				return courseList.subList(0, count);
			} else {
				return courseList;
			}
		} else {
			return null;
		}
	}

	private List<User> getRandomUsers(String role) {
		List<User> userList = userService.findUserByRole(role);
		Collections.shuffle(userList);
		return userList;
	}

	@Override
	public List<User> getRandomUsers(String role, int count) {
		if (count > 0) {
			List<User> userList = getRandomUsers(role);
			if (userList.size() > count) {
				return userList.subList(0, count);
			} else {
				return userList;
			}
		} else {
			return null;
		}
	}

	private List<User> getRecommendTeachersFromMyTeacher(Long id, int count) {
		List<User> userList = getRandomUsersFriends("teacher", "teacher", id,
				count);
		return userList;
	}

	private List<User> getRecommendTeachersFromFriendsTeacher(Long id, int count) {
		List<User> userList = getRandomUsersFriends("user", "teacher", id,
				count);
		return userList;
	}

	private List<User> getRecommendUsersFromFriends(Long id, int count) {
		List<User> userList = getRandomUsersFriends("user", "user", id, count);
		return userList;
	}

	private List<Course> getRandomCourses() {
		List<Course> courseList = courseService.findAllPublish();
		Collections.shuffle(courseList);
		return courseList;
	}

	@Override
	public List<Course> getRandomCourses(int count) {
		if (count > 0) {
			List<Course> courseList = getRandomCourses();
			if (courseList.size() > count) {
				return courseList.subList(0, count);
			} else {
				return courseList;
			}
		} else {
			return null;
		}
	}

	private List<Course> getRecommendTeachersCourses(Long id, int count) {
		return getRandomTeacherCourses(id, count);
	}

	private List<Course> getRecommendUsersCourses(Long id, int count) {
		return getRandomUserCourses(id, count);
	}

	@Override
	public List<User> getRecommendTeacher(Long user_id, int count) {
		List<User> userList = new ArrayList<User>();
		Set<User> userSet = new HashSet<User>();
		userSet.addAll(getRecommendTeachersFromMyTeacher(user_id, count));
		userSet.addAll(getRecommendTeachersFromFriendsTeacher(user_id, count));

		userList.addAll(userSet);
		Collections.shuffle(userList);
		if (userList != null && userList.size() >= count) {
			return userList.subList(0, count);
		} else {
			userList.addAll(getRandomUsers("teacher", count - userList.size()));
			return userList;
		}
	}

	@Override
	public List<Course> getRecommendCourses(Long user_id, int count) {
		List<Course> courseList = new ArrayList<Course>();
		Set<Course> courseSet = new HashSet<Course>();
		courseSet.addAll(getRecommendTeachersCourses(user_id, count));
		courseSet.addAll(getRecommendUsersCourses(user_id, count));
		courseList.addAll(courseSet);
		Collections.shuffle(courseList);
		if (courseList != null && courseList.size() >= count) {
			return courseList.subList(0, count);
		} else {
			courseList.addAll(getRandomCourses(count - courseList.size()));
			return courseList;
		}
	}

	@Override
	public List<User> getRecommendUser(Long user_id, int count) {
		List<User> userList = new ArrayList<User>();
		userList.addAll(getRecommendUsersFromFriends(user_id, count));
		if (userList != null && userList.size() >= count) {
			return userList.subList(0, count);
		} else {
			User theUser = userService.findOne(user_id);
			Set<User> userSet = new HashSet<User>();
			List<User> randomList = getRandomUsers("user");
			userSet.addAll(randomList);
			for (User user : userList) {
				userSet.remove(user);
			}
			userSet.remove(theUser);
			randomList.clear();
			randomList.addAll(userSet);
			userList.addAll(randomList);
			if (userList.size() >= count) {
				return userList.subList(0, count);
			} else {
				return userList;
			}
		}
	}

}
