//package com.vietsoft.boot;
//
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.vietsoft.model.Production;
//import com.vietsoft.repo.ProductionLogRepo;
//import com.vietsoft.service.ProductionLogService;
//
//import lombok.Data;
//@Data
//@Component
//@Scope("prototype")
//public class ProductionLogThread implements Runnable {
//    static final Logger logger = LoggerFactory.getLogger(ProductionLogThread.class);
//
//    @Autowired
//    ProductionLogRepo productionLogRepo;
//    @Autowired
//    private ProductionLogService productionLogService;
//
//    private String type;
//    private String productionId;
//    private Map<String, String> content;
//    private Production production;
//    private String deliveryOrderId;
//    private String orderItemId;
//    public void run() {
//        createLogDB();
//    }
//    private void createLogDB(){
//        productionLogService.createProductionLog(type, productionId, content, production, deliveryOrderId, orderItemId);
//    }
//}
