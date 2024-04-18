package socialnw.api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class MessageUtil {
	
	@Autowired
	private MessageSource messageSource;
	
	public String getMessage(String key, String... args ) {
		return messageSource.getMessage(key, args, null);
	}
}
