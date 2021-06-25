package com.example.demo.service.transaction;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dao.NewsDao;
import com.example.demo.dto.GeneralResult;
import com.example.demo.enumModel.GeneralResultStatus;
import com.example.demo.model.News;
import com.fasterxml.jackson.annotation.JsonIgnore;


 


@Service 
public class NewsTransactionService{
 	 
	@Autowired
	private NewsDao newsDao;	

	@JsonIgnore Logger logger = LogManager.getLogger();

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public GeneralResult upsertNewsList(List<News> upsertNewsModelList) {
		GeneralResult gr = new GeneralResult();
		try {
			newsDao.saveAll(upsertNewsModelList);

			gr.setResultCode(GeneralResultStatus.OK.getStatus());
			gr.setNote("success save or update data size:" + upsertNewsModelList.size());
		} catch (Exception e) {
						
			gr.setResultCode(GeneralResultStatus.SystemProgramError.getStatus());
			gr.setErrorInfo(Thread.currentThread(), e);
			gr.setNote(e.toString());
						
		}
		return gr;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public GeneralResult deleteNewsList(List<News> deleteNewsModelList) {
		GeneralResult gr = new GeneralResult();
		try {
			newsDao.deleteAll(deleteNewsModelList);

			gr.setResultCode(GeneralResultStatus.OK.getStatus());
			gr.setNote("success delete data size:" + deleteNewsModelList.size());
		} catch (Exception e) {
						
			gr.setResultCode(GeneralResultStatus.SystemProgramError.getStatus());
			gr.setErrorInfo(Thread.currentThread(), e);
			gr.setNote(e.toString());
						
		}
		return gr;
	}


}
