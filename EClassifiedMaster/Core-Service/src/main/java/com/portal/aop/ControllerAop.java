package com.portal.aop;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.portal.apilog.entities.PortalApiLog;
import com.portal.common.models.GenericApiResponse;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.datasecurity.DataSecurityService;
import com.portal.datasecurity.model.DataSecurity;
import com.portal.repository.PortalApiLogRepository;
import com.portal.security.util.LoggedUserContext;

import net.minidev.json.JSONObject;

@Configuration
@Aspect
public class ControllerAop {

	private static final Logger log = Logger.getLogger(ControllerAop.class);
	
	@Autowired
	private DataSecurityService dataSecurityService;
	
	@Autowired
	private LoggedUserContext userContext;
	
	@Autowired
	private PortalApiLogRepository portalApiLogRepository;
	
	
	@Around("execution(* com.portal.gd.controller.*.*(..)) || execution(* com.portal.clf.controller.*.*(..))"
			+ "|| execution(* com.portal.nm.controller.*.*(..)) || execution(* com.portal.user.controller.*.*(..)) || execution(* com.portal.reports.controller.*.*(..)) || execution(* com.portal.hm.*.*(..))")
	public Object requestMappingAround(ProceedingJoinPoint pjp) throws Throwable {
		GenericRequestHeaders requestHeaders = this.getReqHeaders();
		GenericApiResponse apiResp = dataSecurityService.dataSecurityVerification(new DataSecurity
				(requestHeaders.getOrgId(), requestHeaders.getOrgOpuId(), requestHeaders.getAccessObjId()));
		if ("1".equals(apiResp.getStatus())) {
			return new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.BAD_REQUEST);
		}
		return	pjp.proceed();
		
	}
	@Before(value = "execution(* com.portal.*.controller.*.*(..))")
	public void requestMapping(JoinPoint pjp) {
		log.debug("Entering in Method :  " + pjp.getSignature().getName());
        log.debug("Class Name :  " + pjp.getSignature().getDeclaringTypeName());
        log.debug("Arguments :  " + Arrays.toString(pjp.getArgs()));
        log.debug("Target class : " + pjp.getTarget().getClass().getName());
        log.info("Entering in Method :  " + pjp.getSignature().getName());
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes()).getRequest();
        if(request.getAttribute("start_time") == null)
			request.setAttribute("start_time", System.currentTimeMillis());
       /* if (null != request) {
            log.debug("Start Header Section of request ");
            log.debug("Method Type : " + request.getMethod());
            Enumeration headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = (String)headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                log.debug("Header Name: " + headerName + " Header Value : " + headerValue);
            }
            log.debug("Request Path info :" + request.getServletPath());
            log.debug("End Header Section of request ");
        }*/
	}
	
	//After -> All method within resource annotated with @Controller annotation 
    //and return a  value
   @AfterReturning(pointcut = "execution(* com.portal.*.controller.*.*(..)) || "
   		+ "@annotation(org.springframework.web.bind.annotation.GetMapping) || "
   		+ "@annotation(org.springframework.web.bind.annotation.PostMapping) || "
   		+ "@annotation(org.springframework.web.bind.annotation.RequestMapping)", returning = "result")
	public void logAfter(JoinPoint joinPoint, Object result) {
        this.apiLogInfo(joinPoint, result);
        log.info("Exit from the method: " + joinPoint.getSignature().getName());
    }
    
    @SuppressWarnings("unchecked")
	public void apiLogInfo(JoinPoint joinPoint, Object result) {
		try {
			if (result != null) {
				ResponseEntity<GenericApiResponse> response = (ResponseEntity<GenericApiResponse>) result;	
				final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
						.currentRequestAttributes()).getRequest();
				GenericRequestHeaders requestHeaders = this.getReqHeaders();
				PortalApiLog apiLog = new PortalApiLog();
				apiLog.setRefObjId(requestHeaders.getOrgOpuId());
				apiLog.setApiStatus(response.getStatusCode().value()+"");
				apiLog.setApiStatus(response.getStatusCode().name());
				apiLog.setApiUrl(request.getRequestURL().toString());
				apiLog.setApiReqMethod(request.getMethod());
				apiLog.setApiReqHeader((new JSONObject(requestHeaders.getReqHeadersMap())).toJSONString());
				if(!"GET".equalsIgnoreCase(request.getMethod())) {
					/*apiLog.setApiReqBody((joinPoint.getArgs() != null && joinPoint.getArgs().length > 0)
							? joinPoint.getArgs()[0] : joinPoint.getArgs());*/
					try {
						apiLog.setApiReqBody(new ObjectMapper().writeValueAsString(joinPoint.getArgs()));
					} catch(Exception ee) {}
				}
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				String json = "";
				try {
					json = ow.writeValueAsString(response.getBody().getData());
				} catch (Exception e) {
					log.info("Exception while converting response body to generic response object");
				}
				apiLog.setApiResBody(json);
				if(request.getAttribute("start_time") instanceof Long)
					apiLog.setApiResTs(TimeUnit.MILLISECONDS
							.toSeconds(System.currentTimeMillis() - ((Long) request.getAttribute("start_time"))));
				apiLog.setCreatedTs(new Date());
				apiLog.setUserId(userContext.getLoggedUser().getUserId());
				apiLog.setCreatedBy(apiLog.getUserId());
				//portalApiLogRepository.save(apiLog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	public GenericRequestHeaders getReqHeaders() {
		GenericRequestHeaders requestHeaders = new GenericRequestHeaders();
		try {
			final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes()).getRequest();
			String orgId = null;
			String orgOpuId = null;
			String accessObjId = null;
			for (Enumeration<?> e = request.getHeaderNames(); e.hasMoreElements();) {
				String nextHeaderName = (String) e.nextElement();
				if ("org_id".equalsIgnoreCase(nextHeaderName)) {
					orgId = request.getHeader(nextHeaderName);
				}
				if ("opu_id".equalsIgnoreCase(nextHeaderName)) {
					orgOpuId = request.getHeader(nextHeaderName);
				}
				if ("access_obj_id".equalsIgnoreCase(nextHeaderName)) {
					accessObjId = request.getHeader(nextHeaderName);
				}
			}
			for (Map.Entry<String, String[]> reqParam : request.getParameterMap().entrySet()) {
				if (orgId == null && "org_id".equalsIgnoreCase(reqParam.getKey())) {
					orgId = reqParam.getValue()[0];
				}
				if (orgOpuId == null && "opu_id".equalsIgnoreCase(reqParam.getKey())) {
					orgOpuId = reqParam.getValue()[0];
				}
				if (accessObjId == null && "access_obj_id".equalsIgnoreCase(reqParam.getKey())) {
					accessObjId = reqParam.getValue()[0];
				}
			}
			requestHeaders.setReqHeadersMap(Collections.list(request.getHeaderNames()).stream()
					.collect(Collectors.toMap(h -> h, request::getHeader)));			
			requestHeaders.setOrgId(orgId);
			requestHeaders.setOrgOpuId(orgOpuId);
			requestHeaders.setAccessObjId(accessObjId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestHeaders;
	}
}
