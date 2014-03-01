<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript" src="<c:url value="/resources/jquery/emptyCheck-ajax.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/img.js" />"></script>
<style type="text/css">
#preview{}
#showimg {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}

</style>
<script type="text/javascript">
function previewImages(file){
	document.getElementById("logoCover").style.display="none";
	previewImage(file);
}

function checkLogo(obj){
	var flag = false;
	var fileValue = obj.coverFile.value;
	var temp = fileValue.substr(fileValue.indexOf('.'),fileValue.length).toLowerCase();
	if(fileValue==null || fileValue==""){
		alert("请添加项目封面");
		flag=false;
	}else{
		if(".gif"==temp || ".jpg"==temp || ".bmp"==temp || ".png" == temp){
			flag=true;
		}else{
			alert("只支持gif、jpg、bmp、png格式的图片！！");
			flag=false;
		}
	}
	return flag;
}
</script>
<style>
.preview_show{
	margin: -25px 0px 10px 90px;
}
.row-fluid.custom {
	margin-bottom: 20px;
	padding: 0px 0px 10px;
	
}
.round {
	border-radius: 5px;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
}
.row-fluid .custom .row {
	margin: 10px 40px;
	color: #80b029;
	border-bottom: solid #cccccc 1.5px;
	
}
.row-fluid.custom .content{
	margin: 20px 40px;

}
.row-fluid .custom .user-row {
	color: #3d4f67;
}
.span5.custom{
	width: 350px;
}
</style>

<div class="row-fluid custom round">
	<div  class="row <c:if test="${sessionUserInfo.role == 'user'}">user-row</c:if>">
		<h4>我的项目>>添加项目</h4>
	</div>
	<div class="content row-fluid">
		项目信息<hr>
			<span style="margin-left: 14px;">LOGO预览：</span> 
			<div id="preview" class="preview_show">
				<img name="showimg" id="showimg"  style="display: none;" />
			</div>
			<div id="logoCover" class="preview_show">
				<span> <img src="<c:url value='${projects.logoPath}'></c:url>"  style="width:260px; height:190px;" />
				</span>
			</div>
		<form action= '<c:url value="/admin/projects/edit/edit"></c:url>'  method="post" enctype="multipart/form-data"  id="projects_form" name="projects_post" >
		<input type="hidden" value="${projects.id }" name="projects_id" >
			<!--  --><div class="control-group"> 
			<div class="controls">
				<i class="icon-star"></i> 上传LOGO：<input type="file" name="logoPath"  onChange="previewImages(this);"/> <span style="font-size: 13px; color: red;">${errorMsg }</span>
				<br><span style="color: red;  margin-left: 70px;">只支持jpg、gif、bmp、png格式，建议封面宽度260px，高度190px</span></div>
			</div>
			<div class="control-group" id="projectName">
				<div class="controls">
					<i class="icon-star"></i> 项目名称：<input type="text" name="projectName"   placeholder="项目名称" required value="${projects.projectName }"> <span class="help-inline"><form:errors path="projectName" /></span>
				</div>
			</div>
		
			<div class="control-group" id="industry">
				<div class="controls">
					<i class="icon-star"></i> 所属行业：<select name="industry" >		  		
				  		<c:forEach items="${projectsField }" var="projectsField" >
					  		<c:choose>
				  				<c:when test="${projectsField.value == projects.industry }">
				  					<option value="${projectsField.value }" selected>${projectsField.value }</option>
				  				</c:when>
				  				<c:otherwise>
				  					<option value="${projectsField.value}" >${projectsField.value }</option>
				  				</c:otherwise>
				  			</c:choose>
					  </c:forEach>
					</select>
				</div>
			</div>

			<div class="control-group" id="progress">
				<div class="controls">
					<i class="icon-star"></i> 项目进度：<input type="text" name="progress"   placeholder="项目进度"  required value="${projects.progress } "> <span class="help-inline"><form:errors path="progress" /></span>
				</div>
			</div>
			
			<div class="control-group" id="totalMoney">
				<div class="controls">
					<i class="icon-star"></i> 融资金额：<input type="text" name="totalMoney"   placeholder="融资金额"  required value="${projects.totalMoney }" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"> <span class="help-inline"><form:errors path="totalMoney" /></span>
				</div>
			</div>
			<div class="control-group" id="content">
				<div class="controls " >
					<i class="icon-star"></i> 项目简介：<br>
					<textarea  style="width:670px;height:150px;"  name="content"  placeholder="项目简介">${projects.content } </textarea>
					<span class="help-inline"><form:errors path="content" /></span>
				</div>
			</div>
			公司信息<hr>
			<div class="control-group" id="companyName">
				<div class="controls">
					<i class="icon-star"></i> 公司名称：<input type="text" name="companyName"   placeholder="公司名称"  required value="${projects.companyName }"> <span class="help-inline"><form:errors path="companyName" /></span>
				</div>
			</div>
			
			<div class="control-group" id="empNumber">
				<div class="controls">
					<i class="icon-star"></i> 员工人数：<input type="text" name="empNumber"   placeholder="申请人士"  required value="${projects.empNumber }"> <span class="help-inline"><form:errors path="applicant" /></span>
				</div>
			</div>
			
			<div class="control-group" id="location">
				<div class="controls">
					<i class="icon-star"></i> 所在城市：<input type="text" name="location"   placeholder="所在城市"  required value="${projects.location }"> <span class="help-inline"><form:errors path="location" /></span>
				</div>
			</div>
			
			<div class="control-group" id="boss">
				<div class="controls">
					<i class="icon-star"></i> 企业法人：<input type="text" name="boss"   placeholder="企业法人"  required value="${projects.boss }"> <span class="help-inline"><form:errors path="boss" /></span>
				</div>
			</div>
			
			<div class="control-group" id="phone">
				<div class="controls">
					<i class="icon-star"></i> 联系电话：<input type="text" name="phone"   placeholder="联系电话"  required value="${projects.phone }" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"> <span class="help-inline"><form:errors path="phone" /></span>
				</div>
			</div>
			
			<div class="control-group" id="targetUser">
				<div class="controls">
					 目标用户或客户群体定位：<textarea  style="width:670px;height:150px;"  name="targetUser">${bizModul.targetUser } </textarea>
				</div>
			</div>
			
			<div class="span9">
			 	<button type="submit" class="btn btn-success ">保存</button>&nbsp;&nbsp;
				<button type="reset" class="btn">取消</button>
			</div>	

			
		</form>
	</div>
</div>
<c:url var="uploadJson" value="/file_upload/${sessionUserInfo.id}"></c:url>
<c:url var="fileManagerJson" value="/file_manager/${sessionUserInfo.id}"></c:url>
<link rel="stylesheet" href="<c:url value="/resources/kindeditor-4.1.3/themes/default/default.css"/>" />
<link rel="stylesheet" href="<c:url value="/resources/kindeditor-4.1.3/plugins/code/prettify.css"/>" />
<script type="text/javascript" charset="utf-8" src="<c:url value="/resources/kindeditor-4.1.3/plugins/code/prettify.js"/>"></script>
<script type="text/javascript">
		$(document).ready(function() {
			var editor = KindEditor.create('textarea[name="content"]',{
				cssPath : '<c:url value="/resources/kindeditor-4.1.3/plugins/code/prettify.css"/>',
				uploadJson : '${uploadJson}',
				fileManagerJson : '${fileManagerJson}',
				allowFileManager : true,
				afterCreate : function() {
					var self = this;
					KindEditor.ctrl(document, 13, function() {
						self.sync();
						document.forms['projects_post'].submit();
					});
					KindEditor.ctrl(self.edit.doc, 13, function() {
						self.sync();
						document.forms['projects_post'].submit();
					});
				}
			});
			$("#projects_form").submit(function(){
				editor.sync();
				return checkEmptyAjax("projects_form","edit/projectsInfoAJAX");
			});
			prettyPrint();
	    });
</script>