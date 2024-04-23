package socialnw.api.services.impl;

import java.time.Instant;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import socialnw.api.dao.AccountDao;
import socialnw.api.entities.Account;
import socialnw.api.services.OtpService;

@Service
@Transactional
public class OtpServiceImpl implements OtpService {

	@Autowired
	private AccountDao accountDao;
	
	@Override
	public String generateOtp(String email) {
		String otp = RandomStringUtils.randomNumeric(8, 9);
		// OTP expires after 60 seconds
		accountDao.updateOtpByEmail(email, Instant.now().plusSeconds(60), otp);
		return otp;
	}

	@Override
	public boolean validateOtp(String email, String otp) {
		if (Strings.isBlank(otp)) {
			return false;
		}
		Account account = accountDao.findByEmail(email);
		if (otp.equals(account.getOtp()) && Instant.now().isBefore(account.getOtpExpTime())) {
			// Clear OTP
			accountDao.updateOtpByEmail(email, null, null);
			return true;
		}
		return false;
	}

}
