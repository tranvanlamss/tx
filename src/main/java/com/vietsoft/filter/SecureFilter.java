//package com.vietsoft.filter;
//
//import java.io.IOException;
//import java.util.Optional;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import com.vietsoft.model.ClientID;
//import com.vietsoft.repo.ClientIDRepo;
//
//@Component
//@Order(1)
//public class SecureFilter implements Filter {
//
//  static final Logger logger = LoggerFactory.getLogger(SecureFilter.class);
//  static final String[] SEC_FETCH = { "sec-fetch-site", "same-origin" };
//  static final String[] X_CLIENT_ID = { "x-client-id", "X" };
//  static final String[] X_CLIENT_TOKEN = { "x-client-token", "X" };
//  static boolean enableSec = true;
//
//  @Autowired
//  ClientIDRepo clientIDRepo;
//
//  @Override
//  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//      throws IOException, ServletException {
//
//    HttpServletRequest req = (HttpServletRequest) request;
//    HttpServletResponse res = (HttpServletResponse) response;
//    String reqURI = req.getRequestURI();
//    String reqMethod = req.getMethod();
//    logger.info("Starting a transaction for req : {} {}", reqMethod, reqURI);
//
//    if (enableSec && reqURI.startsWith("/api/v1/") && (!reqURI.startsWith("/api/v1/browser/index.html") && !reqURI.startsWith("/api/v1/pmhooks"))) {
//      logger.info("Calling API that need to be checked before executing anything");
//      String sec = req.getHeader(SEC_FETCH[0]);
//      if (SEC_FETCH[1].equalsIgnoreCase(sec)) {
//        logger.info("This calling from the same origin");
//      } else {
//
//        String clientId = req.getHeader(X_CLIENT_ID[0]);
//        if (StringUtils.isEmpty(clientId)) {
//          logger.info("NOT valid client ID");
//          res.sendError(403);
//          return;
//        }
//
//        String clientToken = req.getHeader(X_CLIENT_TOKEN[0]);
//        if (StringUtils.isEmpty(clientToken)) {
//          logger.info("NOT valid client TK");
//          res.sendError(403);
//          return;
//        }
//
//        Optional<ClientID> userOpt = clientIDRepo.findByClientIdAndToken(clientId, clientToken);
//        if (!userOpt.isPresent()) {
//          logger.info("NOT valid client ID & TK");
//          res.sendError(403);
//          return;
//        }
//
//        ClientID client = userOpt.get();
//        if (client.getStatus() != 1) {
//          logger.info("Client ID has not activated yet");
//          res.sendError(403);
//          return;
//        }
//      }
//    }
//    chain.doFilter(request, response);
//    logger.info("Committing a transaction for req : {}", req.getRequestURI());
//  }
//}