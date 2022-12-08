package com.vietsoft.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class HttpClientInterceptor implements ClientHttpRequestInterceptor {
	private static Logger logger = LoggerFactory.getLogger(HttpClientInterceptor.class);

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		traceRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		traceResponse(response);

		return response;
	}

	private void traceRequest(HttpRequest request, byte[] body) throws IOException {
//		logger.info("===========================HttpClient Request Rest begin================================================");
//		logger.info("URI         : {}", request.getURI());
//		logger.info("Method      : {}", request.getMethod());
//		logger.info("Headers     : {}", request.getHeaders());
//		logger.info("Content-Length: {}", body.length);
//		logger.info("Content: {}", new String(body));
//		logger.info("Request body: {}", new String(body, "UTF-8"));
//		logger.info("==========================H ttpClientRequest Rest end================================================");
	}

	private void traceResponse(ClientHttpResponse response) throws IOException {
//		logger.info("============================HttpClient Response Rest begin==========================================");
//		logger.info("Status code  : {}", response.getStatusCode());
//		logger.info("Status text  : {}", response.getStatusText());
//		logger.info("Headers      : {}", response.getHeaders());
//		logger.info("=======================HttpClient Response Rest end=================================================");
	}
}
