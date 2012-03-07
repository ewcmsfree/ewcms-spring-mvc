/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Index;

/**
 * 文章信息
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>title:标题</li>
 * <li>shortTitle:短标题</li>
 * <li>subTitle:副标题</li>
 * <li>author:作者</li>
 * <li>origin:来源</li>
 * <li>keyword:关键字</li>
 * <li>tag:标签</li>
 * <li>summary:摘要</li>
 * <li>contents:内容集合对象</li>
 * <li>image:文章图片</li>
 * <li>comment:允许评论</li>
 * <li>type:文章类型</li>
 * <li>owner:创建者</li>
 * <li>published:发布时间</li>
 * <li>modified:修改时间</li>
 * <li>status:状态</li>
 * <li>url:链接地址</li>
 * <li>delete:删除标志</li>
 * <li>relations:相关文章</li>
 * <li>createTime:创建时间</li>
 * <li>categories:文章分类属性集合</li>
 * <li>contentTotal:内容总页数<li>
 * <li>inside:使用内部标题</li>
 * <li>reviewProcess:审核流程对象</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "content_article")
@SequenceGenerator(name = "seq_content_article", sequenceName = "seq_content_article_id", allocationSize = 1)
public class Article implements Serializable {

	private static final long serialVersionUID = -5809802652492615658L;

	/**
	 * 文章类型枚举
	 * @author wuzhijun
	 */
	public enum Type {
		GENERAL("普通新闻"),TITLE("标题新闻");
		
		private String description;
		
		private Type(String description){
			this.description = description;
		}
		
		public String getDescription(){
			return description;
		}

	}
	
	/**
	 * 文章状态枚举
	 * @author wuzhijun
	 */
	public enum Status {
		DRAFT("初稿"),REEDIT("重新编辑"),REVIEW("审核中"),REVIEWBREAK("审核中断"),PRERELEASE("发布版"),RELEASE("已发布");
		
		private String description;
		
		private Status(String description){
			this.description = description;
		}
		
		public String getDescription(){
			return description;
		}
	}
	
	@Id
    @GeneratedValue(generator = "seq_content_article",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "title", nullable = false, length = 100)
	private String title;
	@Column(name = "short_title", length = 50)
	private String shortTitle;
	@Column(name = "sub_title", length = 100)
	private String subTitle;
	@Column(name = "author")
	private String author;
	@Column(name = "origin")
	private String origin;
	@Column(name = "key_word", columnDefinition = "text")
	private String keyword;
	@Column(name = "tag")
	private String tag;
	@Column(name = "summary", columnDefinition = "text")
	private String summary;
	@OneToMany(cascade = CascadeType.ALL, targetEntity = Content.class,fetch=FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "article_id")
	@OrderBy(value = "page asc")
	private List<Content> contents = new ArrayList<Content>();
	@Column(name = "image")
	private String image;
	@Column(name = "comment")
	private Boolean comment;
	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	private Type type;
	@Column(name = "owner")
	private String owner;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "published")
	@Index(name="idx_article_published")
	private Date published;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified", nullable = false)
	private Date modified;
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	@Index(name="idx_article_status")
	private Status status;
	@Column(name = "url", columnDefinition = "text")
	private String url;
	@Column(name = "delete")
	private Boolean delete;
	@OneToMany(cascade = CascadeType.ALL, targetEntity = Relation.class)
	@JoinColumn(name = "article_id")
	@OrderBy(value = "sort")
	private List<Relation> relations = new ArrayList<Relation>();
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime", nullable = false)
	private Date createTime;
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = Category.class)
	@JoinTable(name = "content_article_category", joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
	@OrderBy(value = "id")
	private List<Category> categories = new ArrayList<Category>();
	@Column(name = "total")
	private Integer contentTotal;
	@Column(name = "inside")
	private Boolean inside;
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = ReviewProcess.class)
	@JoinColumn(name="reviewprocess_id")
	private ReviewProcess reviewProcess;
	
	public Article() {
		comment = false;
		type = Type.GENERAL;
		status = Status.DRAFT;
		createTime = new Date(Calendar.getInstance().getTime().getTime());
		delete = false;
		inside = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@JsonIgnore
	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getComment() {
		return comment;
	}

	public void setComment(Boolean comment) {
		this.comment = comment;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getTypeDescription(){
		if (type != null){
			return type.getDescription();
		}else{
			return Type.GENERAL.getDescription();
		}
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getStatusDescription(){
		return status.getDescription();
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Boolean getDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	@JsonIgnore
	public List<Relation> getRelations() {
		return relations;
	}

	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Integer getContentTotal() {
		return contentTotal;
	}

	public void setContentTotal(Integer contentTotal) {
		this.contentTotal = contentTotal;
	}

	public Boolean getInside() {
		return inside;
	}

	public void setInside(Boolean inside) {
		this.inside = inside;
	}

	public ReviewProcess getReviewProcess() {
		return reviewProcess;
	}

	public void setReviewProcess(ReviewProcess reviewProcess) {
		this.reviewProcess = reviewProcess;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
