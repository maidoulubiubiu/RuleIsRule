<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div class="">
	<form method="post">
	  <legend>编辑博文</legend>
	  <div class="control-group" >
		  <label class="control-label" style="display:inline-block;width: 40px;">标题:</label>
		  <input type="text" name="title" placeholder="" value="${blogPost.title}">
		  <input type="hidden" name="id" value="${blogPost.id}">
	  </div>
      <div class="control-group" >
		  <label class="control-label" style="display:inline-block; width: 40px;">分类:</label>
		  <select name="blogcategory_id">
		  	<c:forEach var="blogCategory" items="${blogCategories}">
		  		<option value="${blogCategory.id}" 
		  			${ (blogCategory.id==blogPost.blogCategory.id) ?"selected='selected'":"" }
		  		>${blogCategory.name}</option>
		  	</c:forEach>

		  </select>
	  </div>
	  <div class="clearfix"></div>
	  <div class="control-group">
	  	<textarea rows="20" cols="30" id="textarea" name="content" style="width: 98%;">
	  	${blogPost.content}
	  	</textarea>
	  </div>
	  <button type="submit" class="btn">Submit</button>
	</form>
</div>

<script type="text/javascript">
		$(document).ready(function() {
			var editor = KindEditor.create('textarea[name="content"]');
	    });
</script>
	