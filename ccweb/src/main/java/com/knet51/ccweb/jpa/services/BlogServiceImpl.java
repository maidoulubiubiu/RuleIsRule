package com.knet51.ccweb.jpa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knet51.ccweb.jpa.entities.Teacher;
import com.knet51.ccweb.jpa.entities.blog.BlogCategory;
import com.knet51.ccweb.jpa.entities.blog.BlogPost;
import com.knet51.ccweb.jpa.repository.BlogCategoryRepository;
import com.knet51.ccweb.jpa.repository.BlogPostRepository;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogPostRepository blogPostRepository;
	@Autowired
	private BlogCategoryRepository blogCategoryRepository;
	
	@Override
	public BlogPost findOne(Long id) {
		return blogPostRepository.findOne(id);
	}

	@Override
	public List<BlogPost> findAllBlogs() {
		return blogPostRepository.findAll();
	}

	@Override
	public BlogPost createBlogPost(BlogPost blogPost) {
		return blogPostRepository.save(blogPost);
	}

	@Override
	public List<BlogCategory> findBlogCategories(Teacher teacher) {
		return blogCategoryRepository.findAll();
	}

	@Override
	public BlogCategory findBlogCategory(Long category_id) {
		return blogCategoryRepository.findOne(category_id);
	}

	@Override
	public List<BlogPost> findBlogPosts(Long teacher_id) {
		return  blogPostRepository.findAll();
	}

	@Override
	public BlogPost updateBlogPost(BlogPost blogPost) {
		return blogPostRepository.save(blogPost);
	}

	@Override
	public void deleteBlogPost(Long blog_post_id) {
	}

	@Override
	public BlogCategory createBlogCategory(BlogCategory blogCategory) {
		return blogCategoryRepository.save(blogCategory);
	}

	@Override
	public boolean isBlogCategoryExist(String name, Teacher teacher) {
		return (blogCategoryRepository.findByNameAndTeacher(name, teacher) != null );
	}

	@Override
	public void deleteBlogCategory(Long blog_category_id) {
		blogCategoryRepository.delete(blog_category_id);
	}

	@Override
	public BlogCategory renameBlogCategory(BlogCategory blogCategory) {
		return blogCategoryRepository.save(blogCategory);
	}

}
