package com.portal.org.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "UM_CATEGORY_USERS")
public class UmCategoryUsers extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UmCategoryUsers_generator")
	@SequenceGenerator(name = "UmCategoryUsers_generator", sequenceName = "UM_CATEGORY_USERS_SEQ", allocationSize = 1)
	@Column(name = "USR_MAP_ID")
	private Integer usrMapId;

	@Column(name = "USER_ID")
	private Integer userId;
	
	@Column(name = "CATEGORY_ID")
	private Integer categoryId;

	
	public Integer getUsrMapId() {
		return usrMapId;
	}

	public void setUsrMapId(Integer usrMapId) {
		this.usrMapId = usrMapId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
}
