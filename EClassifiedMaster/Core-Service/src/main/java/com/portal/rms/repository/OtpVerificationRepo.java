package com.portal.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.rms.entity.OtpVerification;

public interface OtpVerificationRepo extends CrudRepository<OtpVerification,String>{

	@Query("from OtpVerification ov where ov.mobileNo = ?1 and ov.otpNum = ?2 and ov.orderId = ?3 ORDER BY ov.otpValidityTime DESC ")
	List<OtpVerification> getOtpVerificationDetails(String mobileNo, String otp, String orderId); 
}
