package com.gospell.aas.log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gospell.aas.common.web.Servlets;

//import com.infosoul.mserver.entity.log.Userloginlist;

public class LogClient {
    private static Logger logger = LoggerFactory.getLogger(LogClient.class);

    public static void main(String[] args) {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget resourceBase = client.target("http://localhost:8080/mserver/rs/log");
            WebTarget getAll = resourceBase.path("getAll");
            WebTarget getById = resourceBase.path("getById");
            WebTarget add = resourceBase.path("add");
            WebTarget update = resourceBase.path("update");
            WebTarget delete = resourceBase.path("delete");
            WebTarget getAllObject = resourceBase.path("getAllObject");

            String authentication = Servlets.encodeHttpBasic("superadmin", "admin");

            logger.info("===== REST start... =====");

            logger.info("===== getLogList =====");
            getAllObject(getAllObject, authentication);

            logger.info("===== get on log =====");
            // getById(getById, 2, authentication);

            logger.info("===== add log =====");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dd = format.format(new Date());
            Date nmsTime = null;
            try {
                nmsTime = format.parse(dd);
                logger.info("dd:" + dd);
                logger.info("nmsTime:" + format.format(nmsTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // Userloginlist addLog = new Userloginlist(nmsTime, "actec9", "3", "192.168.0.1", "基站999", "4", "基站操作999");

            // logger.info("time=" + addLog.getTime());

            getAllObject(getAllObject, authentication);

            // String addLogJson = new
            // ObjectMapper().writeValueAsString(addLog);
            // new ObjectMapper().writeValue(System.out, addLog);
            for (int i = 0; i < 100; i++) {
                // addLogObj(add, addLog, authentication);
            }
            // addLogObj(add, addLog, authentication);
            // TODO: 还可以以Json格式的对象字符串为参数 增加一条日志

            logger.info("===== Update one log =====");

        }

        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new WebApplicationException();
        }
    }

    // get all
    public static void getAllObject(WebTarget target, String authentication) throws Exception {
        Response response1 = target.request(MediaType.APPLICATION_JSON).get();
        System.out.println(response1.getStatus());

        // 1, get response as plain text
        Object jsonRes = target.request(MediaType.APPLICATION_JSON)
                .header(Servlets.AUTHENTICATION_HEADER, authentication).get(Object.class);
        logger.info(jsonRes.toString());

        String xmlRes = target.request(MediaType.APPLICATION_XML)
                .header(Servlets.AUTHENTICATION_HEADER, authentication).get(String.class);
        logger.info(xmlRes);

        // 2, get response and headers etc, wrapped in Response
        Response response = target.request().header(Servlets.AUTHENTICATION_HEADER, authentication).get();
        logger.info("response.getStatus()=" + response.getStatus());
        logger.info("Content-Type=" + response.getHeaders().get("Content-Type"));
        String entity = response.readEntity(String.class);
        logger.info("entity=" + entity);

        // 3, get JAXB response
        // GenericType<List<Userloginlist>> genericType = new GenericType<List<Userloginlist>>() {
        // };
        // List<Userloginlist> logs = target.request(MediaType.APPLICATION_XML)
        // .header(Servlets.AUTHENTICATION_HEADER, authentication).get(genericType);
        // String str = new ObjectMapper().writeValueAsString(logs);
        // logger.info("###JAXB response=" + str);
    }

    // get one
    public static void getById(WebTarget target, Integer id, String authentication) throws Exception {
        // GenericType<JAXBElement<Userloginlist>> generic = new GenericType<JAXBElement<Userloginlist>>() {
        // };
        // JAXBElement<Userloginlist> jaxbContact = target.queryParam("id", id).request(MediaType.APPLICATION_XML)
        // .header(Servlets.AUTHENTICATION_HEADER, authentication).get(generic);
        // Userloginlist log = jaxbContact.getValue();
        // String str = new ObjectMapper().writeValueAsString(log);
        // logger.info(str);
    }

    // add
    public static void addLogObj(WebTarget target, Object log, String authentication) throws Exception {
        Response response = target.request(MediaType.APPLICATION_JSON)
                .header(Servlets.AUTHENTICATION_HEADER, authentication)
                .post(Entity.entity(log, MediaType.APPLICATION_JSON));
        logger.info("addLogObj response code=" + response.getStatus());
    }

    // update
    public static void updateLog(WebTarget target, Object log, String authentication) throws Exception {
        Response response = target.request(MediaType.APPLICATION_JSON)
                .header(Servlets.AUTHENTICATION_HEADER, authentication)
                .put(Entity.entity(log, MediaType.APPLICATION_JSON));

        logger.info("updateLog response code=" + response.getStatus());
    }

    // delete //TODO
    // public static void deleteLog(WebTarget target, Class<Userloginlist> responseType, String authentication)
    // throws Exception {
    // Userloginlist delLog = target.request(MediaType.APPLICATION_JSON)
    // .header(Servlets.AUTHENTICATION_HEADER, authentication).delete(responseType);
    // logger.info("deleteLog id=" + delLog.getId());
    // }

}
