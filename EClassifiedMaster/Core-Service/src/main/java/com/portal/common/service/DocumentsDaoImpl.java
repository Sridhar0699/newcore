package com.portal.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.basedao.IBaseDao;
import com.portal.common.models.DocumentsModel;
import com.portal.doc.entity.Attachments;

@Service
public class DocumentsDaoImpl implements DocumentsDao {

	private static final Logger logger = LogManager.getLogger(DocumentsDaoImpl.class);
	
	@Autowired
	private IBaseDao baseDao;
	
	@Transactional
	public Map<String, String> saveUploadedDocuments(DocumentsModel documentsModel) {
		logger.info("saveUploadedDocuments started");
		Map<String, String> ids = new HashMap<>();
		try {
			if (documentsModel.isClearTmpData()) {
				Object[] params = new Object[] { documentsModel.getLoggedUser().getUserId() };
				baseDao.updateBySQLQuery("delete from ATTACHMENTS where CREATED_BY=? and obj_ref_id IS NULL",
						params);
			}
			if (!documentsModel.getAttachments().isEmpty())
				baseDao.saveOrUpdateAll(documentsModel.getAttachments());
			for (Attachments smAtt : documentsModel.getAttachments()) {
				ids.put(smAtt.getAttachId(), smAtt.getAttachName());
			}
			return ids;
		} catch (Exception e) {
			logger.info("error while uploading documents :"+ e.getMessage());
		}
		return ids;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public boolean updateUploadedDocTypes(DocumentsModel documentsModel) {
		logger.info("updateUploadedDocTypes started");
		String query = "from Attachments where attachId in (:ids) ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", documentsModel.getAttIds());
		List<Attachments> attachments = (List<Attachments>) baseDao.findByHQLQueryWithNamedParams(query, params);
		if (attachments.isEmpty())
			return false;
		for (Attachments st : attachments) {
			st.setAttachType(documentsModel.getAttType());
			baseDao.update(st);
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<String> getDocumentsForDelete(DocumentsModel documentsModel) {
		List<Attachments> attachments = new ArrayList<>();
		List<String> filePaths = new ArrayList<String>();
		Object[] params = new Object[] { documentsModel.getDocIds().get(0) };
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("attIds", documentsModel.getDocIds());
		if (!documentsModel.getDocIds().isEmpty())
			attachments = (List<Attachments>) baseDao
					.findByHQLQueryWithNamedParams("from Attachments where attachId in (:attIds) ", paramMap);
		else {
			params = new Object[] { documentsModel.getLoggedUser().getUserId(),
					documentsModel.getAttType() };
			attachments = (List<Attachments>) baseDao.findByHQLQueryWithIndexedParams(
					"from Attachments where createdBy = ? and attachType = ? and refObjId is null", params);
		}
		if (!attachments.isEmpty())
			baseDao.deleteAll(attachments);
		return filePaths;
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Attachments> getAttachment(DocumentsModel documentsModel) {
		String query = "from Attachments where attachId = ?1 ";
		Object[] params = new Object[] { documentsModel.getDocIds().get(0) };
		return (List<Attachments>) baseDao.findByHQLQueryWithIndexedParams(query, params);
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Attachments> getMultipleAttachment(DocumentsModel documentsModel) {
		String query = "from Attachments where attachId in (:ids) ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", documentsModel.getDocIds());
		return (List<Attachments>) baseDao.findByHQLQueryWithNamedParams(query, params);
	}
}
