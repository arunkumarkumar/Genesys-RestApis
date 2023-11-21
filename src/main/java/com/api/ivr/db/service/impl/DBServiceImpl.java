package com.api.ivr.db.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.api.ivr.db.entity.AMRIvrIntraction;
import com.api.ivr.global.constants.GlobalConstants;
import com.api.ivr.model.BinMaster_Req;
import com.api.ivr.model.preferredlanguage.PreferredLanguage_Req;
import com.api.ivr.model.preferredlanguage.PreferredLanguage_Res;
import com.api.ivr.model.setpreferredlanguage.SetPreferredLangauage_Req;

@Component
public class DBServiceImpl {
	@PersistenceContext
	private EntityManager em;

	@Transactional(value = "transactionManager")

	public PreferredLanguage_Res getPreferredLanguageBasedOnCLI(PreferredLanguage_Req req) throws Exception {
		PreferredLanguage_Res res = new PreferredLanguage_Res();
		String langCode = "";
		try {

			StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("GET_PREFFERED_LANG_CODE_SP")
					.setParameter("p_cli_no", req.getCli().trim());

			langCode = (String) procedureQuery.getOutputParameterValue("p_lang_code");
			String p_check = (String) procedureQuery.getOutputParameterValue("p_check");

			if (p_check.equalsIgnoreCase("Data Not Exist")) {
				res.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
				res.setErrormessage("Record Not Found");
				return res;
			}

			res.setLangCode(langCode);
			res.setErrorcode(GlobalConstants.SUCCESSCODE);
			res.setErrormessage(GlobalConstants.SUCCESS);

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return res;
	}

	@Transactional(value = "transactionManager")
	public String setPreferredLanguage(SetPreferredLangauage_Req req) throws Exception {

		String status = GlobalConstants.FAILURE;
		String cli = req.getCli();
		String relId = req.getRelid();
		String langCode = req.getLangcode();

		try {
			StoredProcedureQuery procedureQuery = em.createStoredProcedureQuery("INSERT_CUSTOMER_DTLS_SP")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN).setParameter(1, cli)
					.setParameter(2, langCode).setParameter(3, relId);

			procedureQuery.execute();

			status = GlobalConstants.SUCCESS;

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}

		return status;
	}
	@Transactional(value = "transactionManager")
	public AMRIvrIntraction getAMIvrDetails(AMRIvrIntraction amivr) {
		//Logger sessionLogger = CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));
		AMRIvrIntraction amivr1 = new AMRIvrIntraction();
		try {
			StoredProcedureQuery getDetails = em.createNamedStoredProcedureQuery("GET_CUST_TVAL_SP")
					.setParameter("I_RELID", amivr.getRelID());
			getDetails.execute();
			String check = null;
			if (getDetails.getOutputParameterValue("O_CHECK").toString() != null)
				check = getDetails.getOutputParameterValue("O_CHECK").toString();
			System.out.println(check +"yy");
			if (check.equalsIgnoreCase("DATAFOUND")) {
				
				@SuppressWarnings("unchecked")
				List<Object[]> listObj = getDetails.getResultList();
				if(listObj.size() > 0) {
					Object[] obj = (Object[]) listObj.get(0);
					
					amivr1.setRelID((String)obj[0]);
					amivr1.setHashedVal((String)obj[1]);
					amivr1.setStatus((String)obj[2]);
					amivr1.setBlockedDate((String)obj[3]);
					amivr1.setCreatechangeDate((String)obj[4]);
					amivr1.setTries((String)obj[5]);
					amivr1.setUnblockedDate((String)obj[6]);
					amivr1.setCheck(getDetails.getOutputParameterValue("O_CHECK").toString());
					
					
					
				}else {
					System.out.println(check +"hh");
					amivr1.setCheck(check);
				}

			} else {
				amivr1.setCheck(check);
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return amivr1;
	}
	
	@Transactional(value = "transactionManager")
	public com.api.ivr.db.entity.BinMaster_Res getCardDetailsBasedOnBin(BinMaster_Req req) {

		String binNumber = req.getBinNumber().trim();

		com.api.ivr.db.entity.BinMaster_Res binMaster_Res = new com.api.ivr.db.entity.BinMaster_Res();

		try {
			StoredProcedureQuery procedureQuery = em.createStoredProcedureQuery("GET_BIN_MASTER_DTLS_SP")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.setParameter(1, binNumber);

			@SuppressWarnings("unchecked")
			List<Object[]> resultList = procedureQuery.getResultList();

			if (resultList != null && resultList.size() > 0 && resultList.get(0) != null) {

				for (Object[] rs : resultList) {
					
					if (rs != null && rs.length >= 12) {
						binMaster_Res.setBin(new Long(rs[0].toString()));
						binMaster_Res.setCardType(rs[1].toString());
						binMaster_Res.setCardName(rs[2].toString());
						binMaster_Res.setHost(rs[3] != null ? rs[3].toString() : "");

						binMaster_Res.setCard_name_desc(rs[4] != null ? rs[4].toString() : "");
						binMaster_Res.setCard_group(rs[5] != null ? rs[5].toString() : "");
						binMaster_Res.setCard_subgroup(rs[6] != null ? rs[6].toString() : "");
						binMaster_Res.setCompany_code(rs[7] != null ? rs[7].toString() : "");

						binMaster_Res.setCurrency_code(rs[8] != null ? rs[8].toString() : "");
						binMaster_Res.setCredit_limit_flag(rs[9] != null ? rs[9].toString() : "");
						binMaster_Res.setAvail_limit_flag(rs[10] != null ? rs[10].toString() : "");
						binMaster_Res.setUbpayment_flag(rs[11] != null ? rs[11].toString() : "");
						binMaster_Res.setCardProductType(rs[12] != null ? rs[12].toString() : "");

						binMaster_Res.setErrorcode(GlobalConstants.SUCCESSCODE);
						binMaster_Res.setErrormessage(GlobalConstants.SUCCESS);

						break;
					} else {
						binMaster_Res.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
						binMaster_Res.setErrormessage("NO DATA FOUND");
					}
				}
			} else {
				binMaster_Res.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
				binMaster_Res.setErrormessage("NO DATA FOUND");
			}

		} catch (Exception e) {
			binMaster_Res.setErrorcode(GlobalConstants.FAILURECODE);
			binMaster_Res.setErrormessage(GlobalConstants.FAILURE);
		}
		return binMaster_Res;
	}
	
	@Transactional(value = "transactionManager")
	public String insertCallLog(com.api.ivr.db.entity.CallLog_Req callLog_Req) throws Exception {


		String status = "";

		try {
			StoredProcedureQuery insertDetails = em.createNamedStoredProcedureQuery("INSERT_CALL_LOG_DTLS_SP");
			insertDetails.setParameter("I_CLI", callLog_Req.getCli());
			insertDetails.setParameter("I_DNIS", callLog_Req.getDnis());
			insertDetails.setParameter("I_STARTTIME", callLog_Req.getStarttime());
			insertDetails.setParameter("I_ENDTIME", callLog_Req.getEndtime());
			insertDetails.setParameter("I_UCID", callLog_Req.getUcid());
			insertDetails.setParameter("I_RMN", callLog_Req.get_rmn());
			insertDetails.setParameter("I_IS_RMN", callLog_Req.getis_rmn());
			insertDetails.setParameter("I_BANK_CARD_LOAN", callLog_Req.get_bank_card_loan());
			insertDetails.setParameter("I_CUSTOMER_SEGMENT", callLog_Req.getCustomer_segment());
			insertDetails.setParameter("I_TRANSFER_OR_DISC", callLog_Req.getTransfer_or_disc());
			insertDetails.setParameter("I_ACC_CARD_ID", callLog_Req.getAcc_card_id());
			insertDetails.setParameter("I_SEG_CODE", callLog_Req.getSeg_code());
			insertDetails.setParameter("I_REL_ID", callLog_Req.getRel_id());
			insertDetails.setParameter("I_BLOCK_CODE", callLog_Req.getBlock_code());
			insertDetails.setParameter("I_LANGUAGE", callLog_Req.getLanguage());
			insertDetails.setParameter("I_CONTEXT_ID", callLog_Req.getContext_id());
			insertDetails.setParameter("I_LASTMENU", callLog_Req.getLastmenu());
			insertDetails.setParameter("I_TRANSFER_ATTRIBUTES", callLog_Req.getTransfer_attributes());
			insertDetails.setParameter("I_COUNTRY", callLog_Req.getConutry());
			insertDetails.setParameter("I_ONE_FA", callLog_Req.getOne_fa());
			insertDetails.setParameter("I_TWO_FA", callLog_Req.getTwo_fa());
			insertDetails.setParameter("I_VERIFIED_BY", callLog_Req.getVerified_by());
			insertDetails.setParameter("I_IDENTIFIED_BY", callLog_Req.getIdentified_by());
			insertDetails.setParameter("I_MENU_TRAVERSE", callLog_Req.getMenu_traverse());
			insertDetails.setParameter("I_CHANNEL", callLog_Req.getChannel());
			insertDetails.setParameter("I_INVOLUNTARY_REASON", callLog_Req.getInvoluntry_Reason());
			insertDetails.setParameter("I_AGENT_ID", callLog_Req.getAgent_id());
			insertDetails.setParameter("I_SESSION_ID", callLog_Req.getSession_id());
			insertDetails.setParameter("I_OTP_DEST", callLog_Req.getOtp_dest());
			insertDetails.setParameter("DISCONNECT_REASON", callLog_Req.getDisconnect_reason());

			insertDetails.execute();


			status = GlobalConstants.SUCCESS;
		} catch (Exception e) {
			
			System.out.println(e.getLocalizedMessage());
		}
		return status;
	}
	
	public com.api.ivr.db.entity.BusinessHrsCheck_Res checkBusinessHours(com.api.ivr.db.entity.BusinessHrsCheck_Req reqObj) {
	
		com.api.ivr.db.entity.BusinessHrsCheck_Res res = new com.api.ivr.db.entity.BusinessHrsCheck_Res();

		try {
			StoredProcedureQuery procedureQuery = em.createStoredProcedureQuery("BUSINESS_HOURS_CHECK_SP")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(8, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(9, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(10, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(11, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(12, String.class, ParameterMode.OUT)
					.setParameter(1, reqObj.getSegment())
					.setParameter(2, reqObj.getLanguage())
					.setParameter(3, reqObj.getProduct());


			procedureQuery.execute();
			
			res.setSegment(reqObj.getSegment());
			res.setLanguage(reqObj.getLanguage());
			res.setProduct(reqObj.getProduct());
			
			if("Y".equalsIgnoreCase(procedureQuery.getOutputParameterValue(4).toString())) {
				res.setHoliday(true);
			}else {
				res.setHoliday(false);
			}
			
			if("Y".equalsIgnoreCase(procedureQuery.getOutputParameterValue(5).toString())) {
				res.setServiceHrsCheckAvailable(true);
			}else {
				res.setServiceHrsCheckAvailable(false);
			}
			
			res.setServiceHrsStartTime(procedureQuery.getOutputParameterValue(6).toString());
			res.setServiceHrsEndTime(procedureQuery.getOutputParameterValue(7).toString());
			if("Y".equalsIgnoreCase(procedureQuery.getOutputParameterValue(8).toString())) {
				res.setServiceHrs(true);
			}else {
				res.setServiceHrs(false);
			}
			
			if("Y".equalsIgnoreCase(procedureQuery.getOutputParameterValue(9).toString())) {
				res.setVDNAvailable(true);
			}else {
				res.setVDNAvailable(false);
			}
			res.setVdnRoutingData(procedureQuery.getOutputParameterValue(10).toString());
			
			res.setGenPrompt(procedureQuery.getOutputParameterValue(11).toString());
			
			res.setSplPrompt(procedureQuery.getOutputParameterValue(12).toString());

			

			res.setErrorcode(GlobalConstants.SUCCESSCODE);
			res.setErrormessage(GlobalConstants.SUCCESS);
			

		} catch (Exception e) {
			res.setErrorcode(GlobalConstants.FAILURECODE);
			res.setErrormessage(GlobalConstants.FAILURE);

		}

		return res;

	}

}
