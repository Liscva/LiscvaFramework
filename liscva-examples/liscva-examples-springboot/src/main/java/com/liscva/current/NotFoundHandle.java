package com.liscva.current;

import com.liscva.framework.core.connect.FailPublicConnect;
import com.liscva.framework.core.connect.FinalConnect;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理 404  
 *  
 */
@RestController
public class NotFoundHandle implements ErrorController {

	@Override
	public String getErrorPath() {
		return "/error";
	}

	@RequestMapping("/error")
    public FinalConnect error(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(200);
        return FailPublicConnect.error(404, "not found", null);
    }
	
}
