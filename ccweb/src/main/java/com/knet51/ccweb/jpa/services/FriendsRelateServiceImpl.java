package com.knet51.ccweb.jpa.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knet51.ccweb.jpa.dao.FriendsRelatedDao;
import com.knet51.ccweb.jpa.dao.UserDao;
import com.knet51.ccweb.jpa.entities.FriendsRelated;
import com.knet51.ccweb.jpa.entities.User;

@Transactional
@Service("friendsRelateService")
public class FriendsRelateServiceImpl implements FriendsRelateService {
	@Autowired
	private FriendsRelatedDao friendsRelatedDao;

	@Autowired
	private UserDao userDao;

	@Override
	public FriendsRelated save(FriendsRelated friendsRelated) {
		return friendsRelatedDao.save(friendsRelated);
	}

	@Override
	public FriendsRelated update(FriendsRelated friendsRelated) {
		return friendsRelatedDao.update(friendsRelated);
	}

	@Override
	public FriendsRelated findOneById(Long id) {
		return null;
	}

	@Override
	public void deleteById(Long id) {
		friendsRelatedDao.deleteById(id);
	}

	@Override
	public List<FriendsRelated> getAllFans(Long id) {
		return friendsRelatedDao.getAllFollow(id);
	}

	@Override
	public List<FriendsRelated> getAllHost(Long followId) {
		return friendsRelatedDao.getAllHost(followId);
	}

	/**
	 * 判断followId是否存在以此来验证是否对其关注
	 */
	@Override
	public boolean isTheFollower(Long followId, Long hostId) {
		int followValue = friendsRelatedDao.getFollowById(hostId, followId);
		return (followValue != 0);
	}

	/* get the fans infor */
	@Override
	public List<User> getAllFansInfo(Long user_id) {
		List<FriendsRelated> fansList = friendsRelatedDao.getAllFollow(user_id);
		List<User> fansInfoList = new ArrayList<User>();
		for (int i = 0; i < fansList.size(); i++) {
			User user = userDao.findById(fansList.get(i).getFollow_id());
			fansInfoList.add(user);
		}
		return fansInfoList;
	}

	@Override
	public List<User> getAllFansInfo(Long user_id, String role) {
		List<FriendsRelated> fansList = friendsRelatedDao.getAllFollow(user_id);
		List<User> fansInfoList = new ArrayList<User>();
		for (int i = 0; i < fansList.size(); i++) {
			User user = userDao.findById(fansList.get(i).getFollow_id());
			if (user != null && user.getRole() != null
					&& user.getRole().equals(role)) {
				fansInfoList.add(user);
			}
		}
		return fansInfoList;
	}

	/* get the host infor */
	@Override
	public List<User> getAllHostInfo(Long user_id) {
		List<FriendsRelated> hostList = friendsRelatedDao.getAllHost(user_id);
		List<User> hostInfoList = new ArrayList<User>();
		for (int i = 0; i < hostList.size(); i++) {
			User user = userDao.findById(hostList.get(i).getHost_id());
			hostInfoList.add(user);
		}
		return hostInfoList;
	}

	@Override
	public List<User> getAllHostInfo(Long user_id, String role) {
		List<FriendsRelated> hostList = friendsRelatedDao.getAllHost(user_id);
		List<User> hostInfoList = new ArrayList<User>();
		for (int i = 0; i < hostList.size(); i++) {
			User user = userDao.findById(hostList.get(i).getHost_id());
			if (user != null && user.getRole() != null
					&& user.getRole().equals(role)) {
				hostInfoList.add(user);
			}
		}
		return hostInfoList;
	}

	@Override
	public FriendsRelated findOneByHostIdAndFollowId(Long host_id,
			Long follow_id) {
		// TODO Auto-generated method stub
		return friendsRelatedDao.findOneByFollowIdAndHostId(host_id, follow_id);
	}

}
