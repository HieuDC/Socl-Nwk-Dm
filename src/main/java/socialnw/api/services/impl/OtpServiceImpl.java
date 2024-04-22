package socialnw.api.services.impl;

import javax.print.attribute.standard.DateTimeAtCompleted;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import socialnw.api.dao.AccountDao;
import socialnw.api.services.OtpService;

public class OtpServiceImpl implements OtpService {

	@Autowired
	private AccountDao accountDao;
	
	@Override
	public String generateOtp(String email) {
		String otp = RandomStringUtils.randomNumeric(8, 9);
		accountDao.updateOtpByAccountId(0L, otp);
		return otp;
	}

	@Override
	public boolean validateOtp() {
		// TODO Auto-generated method stub
		return false;
	}

}
