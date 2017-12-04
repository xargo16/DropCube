package com.dave.dropcube.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "files")
@NamedQuery(query = "SELECT f FROM FileEntity f WHERE f.userEntity = :user", name = "getAllFiles")
public class FileEntity {
	@ManyToOne
	UserEntity userEntity;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int fileId;

	@Column(nullable = false)
	private Date dateOfUpload;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@Basic(fetch = FetchType.LAZY)
	private String contentType;

	@Column(nullable = false, length = 920971520)
	private byte[] content;

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public Date getDateOfUpload() {
		return dateOfUpload;
	}

	public void setDateOfUpload(Date dateOfUpload) {
		this.dateOfUpload = dateOfUpload;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

}
