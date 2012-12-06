package com.knet51.ccweb.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.knet51.ccweb.jpa.entities.ReceiveMsg;

@Repository("receiveMsgDao")
public class ReceiveMsgDaoImpl implements ReceiveMsgDao {
	
	@PersistenceContext
	private EntityManager em;
	@Override
	public void add(ReceiveMsg receiveMsg) {
		em.persist(receiveMsg);
	}
	
	
	@Override
	public void del(Long mId) {
		ReceiveMsg receiveMsg = em.find(ReceiveMsg.class, mId);
		//System.out.println("##############"+receiveMsg.getUser()+"################");
		//System.out.println("###############"+mId+"################");
		receiveMsg.setReaded(3);
		em.merge(receiveMsg);
	}
	
	@Override
	public void destory(Long mId) {
		ReceiveMsg receiveMsg = em.find(ReceiveMsg.class, mId);
		em.remove(receiveMsg);
	}

	@Override
	public ReceiveMsg detail(Long userId) {
		return em.find(ReceiveMsg.class, userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReceiveMsg> list(Long userId) {
		return em.createQuery("from ReceiveMsg where  user_id="+userId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReceiveMsg> unReadList(Long userId) {
		return em.createQuery("from ReceiveMsg where readed=1 and user_id="+userId).getResultList();
	}

	@Override
	public void isRead(Long id) {
		ReceiveMsg receiveMsg = em.find(ReceiveMsg.class, id);
		receiveMsg.setReaded(2);
		em.merge(receiveMsg);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReceiveMsg> isReadList(Long userId) {
		return em.createQuery("from ReceiveMsg where readed=2 and user_id="+userId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReceiveMsg> isDele(Long userId) {
		return em.createQuery("from ReceiveMsg where readed=3 and user_id="+userId).getResultList();
	}

}