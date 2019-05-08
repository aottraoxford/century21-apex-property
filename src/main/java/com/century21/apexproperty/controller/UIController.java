package com.century21.apexproperty.controller;

import com.century21.apexproperty.model.ID;
import com.century21.apexproperty.model.request.SignUp;
import com.century21.apexproperty.repository.EventRepo;
import com.century21.apexproperty.repository.ProjectRepo;
import com.century21.apexproperty.repository.PropertyRepo;
import com.century21.apexproperty.repository.UserRepo;
import com.century21.apexproperty.repository.api_signup.SignUpRepo;
import com.century21.apexproperty.repository.api_silder_add.AddSliderRepo;
import com.century21.apexproperty.repository.api_social_signin.SocialSignInRepo;
import com.century21.apexproperty.repository.api_user_upload_image.UserUploadImageRepo;

import com.century21.apexproperty.repository.api_visible_project.VisibleProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
//import springfox.documentation.annotations.ApiIgnore;


//@ApiIgnore
@Controller
public class UIController {
    @Autowired
    private SignUpRepo signUpRepo;

    @Autowired
    private UserUploadImageRepo userUploadImageRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PropertyRepo propertyRepo;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private VisibleProjectRepo visibleProjectRepo;

    @Autowired
    private AddSliderRepo addSliderRepo;

    @GetMapping("/test")
    public String test(){
        try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic ODYzOTJlMTYtNGMzNi00MjUzLTg2MWEtMmI0NDU1YzQzYmM3");
            con.setRequestMethod("POST");

            String strJsonBody = "{"
                    +   "\"app_id\": \"6ed2f44c-9a7a-4a25-89b9-73c19b0ae705\","
                    +   "\"filters\": [{\"field\":\"tag\",\"key\":\"name\",\"value\":\"test\"}],"
                    +   "\"data\": {\"foo\": \"bar\"},"
                    +   "\"contents\": {\"en\": \"English Message\"}"
                    + "}";


            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            if (  httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            System.out.println("jsonResponse:\n" + jsonResponse);

        } catch(Throwable t) {
            t.printStackTrace();
        }

        return "index";
    }

    @GetMapping({"/"})
    public String swagger() {
        return "redirect:/swagger-ui.html";
    }

    @ResponseBody
    @GetMapping("/data/property")
    public String dataProperty(){
        PropertyRepo.PropertyRequest propertyRequest=new PropertyRepo.PropertyRequest();
        int row=20;
        for(int i=1;i<=row;i++) {
            propertyRequest.setAirConditioner((int)(Math.random()*8)+1);
            propertyRequest.setBalcony((int)(Math.random()*8)+1);
            propertyRequest.setBathroom((int)(Math.random()*8)+1);
            propertyRequest.setDinningRoom((int)(Math.random()*8)+1);
            propertyRequest.setBedroom((int)(Math.random()*8)+1);
            propertyRequest.setKitchen((int)(Math.random()*8)+1);
            propertyRequest.setLivingRoom((int)(Math.random()*8)+1);
            propertyRequest.setMezzanineFloor((int)(Math.random()*8)+1);
            propertyRequest.setParking((int)(Math.random()*8)+1);
            propertyRequest.setCommonArea(54.32);
            propertyRequest.setHeight(100);
            propertyRequest.setLandLength(35);
            propertyRequest.setLandWidth(10);
            propertyRequest.setFloorNo(2);
            propertyRequest.setLat(11.11);
            propertyRequest.setLng(111.111);
            propertyRequest.setPrivateArea(50.5);
            propertyRequest.setSqmPrice(Double.parseDouble (String.format("%.2f",Math.random()*999)));
            propertyRequest.setTotalArea(594.44);
            propertyRequest.setTotalLandArea(872.44);
            propertyRequest.setUnitPrice(Double.parseDouble (String.format("%.2f",Math.random()*999999)));
            propertyRequest.setWidth(40.21);
            propertyRequest.setCity("Phnom Penh");
            propertyRequest.setCommune("Berng Tom pun");
            propertyRequest.setDistrict("Mean Chey");
            propertyRequest.setVillage("Son Som Ku Sol");
            propertyRequest.setTitle(i+"Property Available in Cambodia Property Available in Cambodia");
            propertyRequest.setDescription("Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, comes from a line in section 1.10.32");
            propertyRequest.setHouseNo("211");
            int ros=(int)(Math.random()*2)+1;
            if(ros==1) propertyRequest.setRentOrSell("Rent");
            else propertyRequest.setRentOrSell("Buy");
            propertyRequest.setStreetNo("2004");
            propertyRequest.setType("Flat");
            propertyRequest.setStatus(true);
            propertyRequest.setShowMap(true);
            propertyRequest.setProjectID(0);
            propertyRepo.insertProperty(new ID(), propertyRequest,(int)(Math.random()*11)+1);
            for(int j=1;j<=8;j++)
                propertyRepo.insertFiles(((int)(Math.random()*8)+1)+".jpg","image",i);
            propertyRepo.insertFiles("1.pdf","doc",i);
            PropertyRepo.Neighborhood neighborhood=new PropertyRepo.Neighborhood();
            for(int j=1;j<=6;j++) {
                neighborhood.setPropertyID(i);
                neighborhood.setDistance(3.4);
                neighborhood.setAddress("Phnom Penh International Airport");
                propertyRepo.insertNeighborhood(neighborhood, i);
            }
        }
        return "OK";
    }

    @ResponseBody
    @GetMapping("/data/user")
    public String dataUser(){
        SignUp signUp=new SignUp();
        signUp.setEmail("darongvann@gmail.com");
        signUp.setFirstName("darong");
        signUp.setLastName("vann");
        signUp.setGender("male");
        signUp.setPhoneNumber("+855|17552651");
        signUp.setPassword("12345678");
        signUpRepo.signUp(signUp);
        userRepo.updateRole(1,1);
        userUploadImageRepo.saveUserImage(1,"1.jpg");
        for(int i=2;i<7;i++){
            signUp.setEmail("user"+i+"@gmail.com");
            signUp.setFirstName("dara");
            signUp.setLastName("daro");
            signUp.setGender("male");
            signUp.setPhoneNumber("+855|17552651");
            signUp.setPassword("12345678");
            signUpRepo.signUp(signUp);
            userUploadImageRepo.saveUserImage(i,(int)(Math.random()*5)+1+".jpg");
        }
        for(int i=7;i<12;i++){
            signUp.setEmail("agent"+i+"@gmail.com");
            signUp.setFirstName("dara");
            signUp.setLastName("daro");
            signUp.setGender("female");
            signUp.setPhoneNumber("+855|17552651");
            signUp.setPassword("12345678");
            signUpRepo.signUp(signUp);
            userUploadImageRepo.saveUserImage(i,(int)(Math.random()*5)+1+".jpg");
            userRepo.setChild(1,i);
        }
       
        return "OK";
    }

    @ResponseBody
    @GetMapping("/data/project")
    public String dataProject() {
        int row = 20;
        ProjectRepo.ProjectRequest projectRequest = new ProjectRepo.ProjectRequest();
        for (int i = 1; i <= row; i++) {
            projectRequest.setName(i+"Project for sell - free 1 charger and t-shirt and popcorn for free");
            projectRequest.setBuiltDate(new Date());
            projectRequest.setCompletedDate(new Date());
            projectRequest.setProjectTypeID((int) (Math.random() * 4) + 1);
            projectRequest.setCountryID((int) (Math.random() * 3) + 1);
            projectRequest.setAddressOne("#50, Corner of St. 516 and St. 335, Sangkat Boeung Kak 1, Khan Tuol Kouk, Phnom Penh, Cambodia.");
            projectRequest.setGrr(0.5);
            projectRequest.setDownPayment("20%");
            projectRequest.setDescription("项目名称：Skyline\n" +
                    "物业类型：永久产权公寓\n" +
                    "户型选择：1房（77㎡）、2房（105㎡）\n" +
                    "项目位置：金边玛卡拉区首相府旁\n" +
                    "楼高：39层（两个塔楼组成）\n" +
                    "交房日期：2018年底（已全面封顶，楼体装修阶段中）\n" +
                    "房屋产权：永久产权\n" +
                    "包租：3年18%租赁回报\n" +
                    "项目配套：大型停车场、中型超市、泳池、阅览室、健身房、空中酒吧、餐厅、烧烤区、空中花园等。");
            projectRequest.setPrice((int) (Math.random() * 100000) + 1 * 1.6);
            projectRequest.setSqmPrice((int) (Math.random() * 100) + 1 * 1.6);
            projectRequest.setAvgRentFrom(0.5);
            projectRequest.setAvgRentTo(0.9);
            projectRequest.setCity("Phnom Penh");
            switch ((int) (Math.random() * 2) + 1) {
                case 1:
                    projectRequest.setRentOrBuy("Rent");
                    break;
                case 2:
                    projectRequest.setRentOrBuy("Buy");
                    break;
            }
            projectRepo.insertProject(new ID(), (int) (Math.random() * 5) + 7, projectRequest);
            visibleProjectRepo.visibleProject(true,i);
            projectRepo.updateThumbnail((int) (Math.random() * 8) + 1 + ".jpg",i);
            for(int j=1;j<8;j++){
                projectRepo.insertGallery((int) (Math.random() * 8) + 1 + ".jpg",i);
            }

            ProjectRepo.ProjectIntroduction projectIntroduction = new ProjectRepo.ProjectIntroduction();
            for(int j=1;j<=3;j++){
                if(j==1) {
                    projectIntroduction.setDescription("<div id=\"xmjs\" class=\"lease_intro_con\" style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; color: rgb(83, 83, 83); font-family: &quot;Microsoft YaHei&quot;;\"><div class=\"intro_con_item\" style=\"margin: 0px; padding: 0px 0px 20px; border: 0px; vertical-align: baseline; line-height: 25px;\"><div id=\"xmjs\" class=\"lease_intro_con\" style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline;\"><div class=\"intro_con_item\" style=\"margin: 0px; padding: 0px 0px 20px; border: 0px; vertical-align: baseline; line-height: 25px;\"><ul style=\"margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none; letter-spacing: 1px; line-height: 20px;\"><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><p style=\"margin-bottom: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; color: rgb(83, 83, 83); line-height: 20px;\"><span style=\"font-size: 14px;\">金边市12个行政区内面积最小的一个，却是整个金边心脏地区，首相府坐落其中，生活配套最为完善！地区外资机构云集，外国工作人员较多，深受外国投资客青睐！出租公寓紧缺。</span></p><p style=\"margin-bottom: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; color: rgb(83, 83, 83); line-height: 20px;\">&nbsp;</p><ul style=\"margin-right: 0px; margin-left: 0px; padding: 0px; border: 0px; font-size: 14px; vertical-align: baseline; list-style: none; color: rgb(83, 83, 83); letter-spacing: normal;\"><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none; color: rgb(83, 83, 83); letter-spacing: 1px; line-height: 20px; text-align: center;\"><img src=\"http://www.shitonghk.com/d/file/2017/09/10/646da3c9d924e0ed273bd923020e17d6.jpg\" alt=\"2.jpg\" width=\"790\" height=\"380\" style=\"margin: 0px; padding: 0px; vertical-align: baseline;\"></li></ul><p style=\"margin-bottom: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; color: rgb(83, 83, 83); line-height: 20px;\">&nbsp;</p><p style=\"margin-bottom: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; color: rgb(83, 83, 83); line-height: 20px;\"><span style=\"font-size: 14px;\">玛卡拉区域内大型购物商场、传统市场并存，购物消费等生活配套完整，区内还有国际级的大型运动公园、高级外语学校（包含新加坡国际学校及中文学校等）及甲级的崭新办公大楼及住宅大楼，在治安部分，区内分别有古巴及德国大使馆等外使馆，治安十分严谨。</span></p><p style=\"margin-bottom: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; color: rgb(83, 83, 83); line-height: 20px;\"><span style=\"font-size: 14px;\"><br></span></p><p style=\"margin-bottom: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; color: rgb(83, 83, 83); line-height: 20px;\"><span style=\"font-size: 14px;\"><br></span></p><ul style=\"margin-right: 0px; margin-left: 0px; padding: 0px; border: 0px; vertical-align: baseline; list-style: none; color: rgb(83, 83, 83); line-height: 20px;\"><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><span style=\"font-size: 14px;\">总理府坐落其中</span></li><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><span style=\"font-size: 14px;\">银行云集，柬埔寨的金融区</span></li><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><span style=\"font-size: 14px;\">云集多国大使馆</span></li><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><span style=\"font-size: 14px;\">旅游局</span></li><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><span style=\"font-size: 14px;\">拥有大型购物商场、传统市场</span></li><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><span style=\"font-size: 14px;\">国际级的大型运动公园</span></li><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><span style=\"font-size: 14px;\">多所国际级学校</span></li><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><span style=\"font-size: 14px;\">火车站以及金边国际机场</span></li><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><span style=\"font-size: 14px;\">A级崭新写字楼及公寓</span></li></ul></li></ul></div></div></div></div>");
                    projectIntroduction.setName("金融核心：玛卡拉区");
                    projectRepo.insertProjectIntro(projectIntroduction, i);
                }else if(j==2){
                    projectIntroduction.setDescription("<div id=\"xmjs\" class=\"lease_intro_con\" style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; color: rgb(83, 83, 83); font-family: &quot;Microsoft YaHei&quot;;\"><div class=\"intro_con_item\" style=\"margin: 0px; padding: 0px 0px 20px; border: 0px; vertical-align: baseline; line-height: 25px;\"><div id=\"xmjs\" class=\"lease_intro_con\" style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline;\"><div class=\"intro_con_item\" style=\"margin: 0px; padding: 0px 0px 20px; border: 0px; vertical-align: baseline; line-height: 25px;\"><ul style=\"margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none; letter-spacing: 1px; line-height: 20px;\"><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><p style=\"margin-bottom: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; color: rgb(83, 83, 83); line-height: 20px;\"><span style=\"font-size: 14px;\">北面是干隆街，南面是戴高乐大道，东面是莫尼旺大道，西面是捷克大道。</span></p><p style=\"margin-bottom: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; color: rgb(83, 83, 83); line-height: 20px;\">&nbsp;</p><ul style=\"margin-right: 0px; margin-left: 0px; padding: 0px; border: 0px; font-size: 14px; vertical-align: baseline; list-style: none; color: rgb(83, 83, 83); letter-spacing: normal;\"><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none; color: rgb(83, 83, 83); letter-spacing: 1px; line-height: 20px; text-align: center;\"><img src=\"http://www.shitonghk.com/d/file/2017/09/10/5cc61b3a832be670f12ad08cd163367b.jpg\" alt=\"3.jpg\" width=\"790\" height=\"450\" style=\"margin: 0px; padding: 0px; vertical-align: baseline;\"></li></ul><p style=\"margin-bottom: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; color: rgb(83, 83, 83); line-height: 20px;\">&nbsp;</p><p style=\"margin-bottom: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; color: rgb(83, 83, 83); line-height: 20px;\"><span style=\"font-size: 14px; color: rgb(229, 51, 51);\"><strong>距离</strong></span></p><ul style=\"margin-right: 0px; margin-left: 0px; padding: 0px; border: 0px; vertical-align: baseline; list-style: none; color: rgb(83, 83, 83); line-height: 20px;\"><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><span style=\"font-size: 14px;\">和平宫：2分钟</span></li><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><span style=\"font-size: 14px;\">中央市场：3分钟</span></li><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><span style=\"font-size: 14px;\">加华大厦：3分钟</span></li><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><span style=\"font-size: 14px;\">金边大厦：7分钟</span></li><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><span style=\"font-size: 14px;\">塔仔山：8分钟</span></li><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><span style=\"font-size: 14px;\">独立纪念碑：10分钟</span></li><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><span style=\"font-size: 14px;\">柬埔寨火车站：9分钟</span></li><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><span style=\"font-size: 14px;\">奥林匹克体育场：4分钟</span></li><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><span style=\"font-size: 14px;\">金边水上乐园：14分钟</span></li><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><span style=\"font-size: 14px;\">金边国际机场：30分钟</span></li></ul><p style=\"margin-bottom: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; color: rgb(83, 83, 83); line-height: 20px;\">&nbsp;</p><ul style=\"margin-right: 0px; margin-left: 0px; padding: 0px; border: 0px; font-size: 14px; vertical-align: baseline; list-style: none; color: rgb(83, 83, 83); letter-spacing: normal;\"><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none; color: rgb(83, 83, 83); letter-spacing: 1px; line-height: 20px; text-align: center;\"><img src=\"http://www.shitonghk.com/d/file/2017/09/10/4363381dac2147ff182c17e2409b43d3.jpg\" alt=\"4.jpg\" width=\"790\" height=\"573\" style=\"margin: 0px; padding: 0px; vertical-align: baseline;\"></li></ul><p style=\"margin-bottom: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; color: rgb(83, 83, 83); line-height: 20px;\">&nbsp;</p></li></ul></div></div></div></div>");
                    projectIntroduction.setName("便捷交通，四通八达");
                    projectRepo.insertProjectIntro(projectIntroduction,i);
                }else {
                    projectIntroduction.setDescription("<div id=\"xmjs\" class=\"lease_intro_con\" style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; color: rgb(83, 83, 83); font-family: &quot;Microsoft YaHei&quot;;\"><div class=\"intro_con_item\" style=\"margin: 0px; padding: 0px 0px 20px; border: 0px; vertical-align: baseline; line-height: 25px;\"><div id=\"xmjs\" class=\"lease_intro_con\" style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline;\"><div class=\"intro_con_item\" style=\"margin: 0px; padding: 0px 0px 20px; border: 0px; vertical-align: baseline; line-height: 25px;\"><ul style=\"margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none; letter-spacing: 1px; line-height: 20px;\"><li style=\"margin: 0px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; list-style: none;\"><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: center;\"><span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">首相府旁唯一39层绝版地标公寓</span></p><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: center;\">金边<span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">玛卡拉区</span>，尊贵无二</p><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: center;\">毗邻<span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">总理府</span>、<span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">金边皇宫</span>、<span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">国家博物馆</span></p><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: center;\">3-5年包租<span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">18%-30%</span>总租赁回报</p><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: center;\"><span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">永久产权</span>拎包入住</p><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: center;\">现楼发售，<span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">100万/套起</span></p><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: justify;\"><span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">【永久产权 包租5年】</span></p><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: justify;\">该项目土地属于永久性，可以包租<span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">5</span>年，每年回报率<span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">6%</span>，可以在购买时一次性支付<span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">5</span>年租金，每套房低至<span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">10</span>万美金起，付款灵活，稳定收益，最佳投资选择。</p><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: justify;\"><img id=\"\" src=\"http://si1.go2yd.com/get-image/0JHunZ3U4ie\" alt=\"\" style=\"margin: 20px 0px; padding: 0px; vertical-align: baseline; max-width: 100%; display: block;\"></p><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: justify;\"><span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">【豪华配套设施】</span></p><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: justify;\">地面层和第1、2层为<span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">集中型商业</span>，3至8层为<span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">停车</span><span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">场</span>（汽车停车位有509个，摩托车位有297个）第9层为架空层，有<span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">游泳池、阅览室、健身房、烧烤区、浅水休闲区</span>，而且屋顶也有<span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">聚餐、酒吧</span>。第10至38层为公寓，第38层还有个<span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">空中花园。</span></p><p style=\"margin-bottom: 0px; padding: 0px; border: 0px; font-size: 14px; vertical-align: baseline; color: rgb(83, 83, 83); letter-spacing: normal;\">&nbsp;</p><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: justify;\"><span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">出租优势：</span></p><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: justify;\">加华银行附近2公里内，属于柬埔寨金融公司银行的聚集地，周边地价高（都是旺铺），目前没有高端公寓，周边商务人士都只能退而求其次租相对较远的公寓。<span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">skyline的出现将极大刺激了周边的商务人士的租房需求。</span></p><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: justify;\">&nbsp;</p><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: justify;\"><span style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; font-weight: 700;\">&nbsp; 租住人群 ：</span></p><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: justify;\">--写字楼企业的员工住宿（A/B级写字楼）</p><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: justify;\">--商人的住宿（乌鸦西及周边做生意的人）</p><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: justify;\">--玛卡拉区的商务人士（周边政府部门，银行职员）</p><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: justify;\">--玛卡拉区的家庭</p><p style=\"margin-bottom: 17px; padding: 0px; border: 0px; font-size: 16px; vertical-align: baseline; letter-spacing: normal; line-height: 28px; color: rgb(50, 50, 50); font-family: tahoma, arial, &quot;Hiragino Sans GB&quot;, &quot;\\5 b8b\\4 f53&quot;; text-align: justify;\">--想居住在市中心的有游泳池和健身房的配套完善的公寓楼的人群。</p><p style=\"margin-bottom: 0px; padding: 0px; border: 0px; font-size: 14px; vertical-align: baseline; color: rgb(83, 83, 83); letter-spacing: normal;\">&nbsp;</p><p style=\"margin-bottom: 0px; padding: 0px; border: 0px; font-size: 17.1429px; vertical-align: baseline; max-width: 100%; clear: both; min-height: 1em; color: rgb(51, 51, 51); font-family: -apple-system-font, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei UI&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; text-align: justify; letter-spacing: 0.582857px; overflow-wrap: break-word !important;\"><span style=\"margin: 0px; padding: 0px; max-width: 100%; color: rgb(63, 63, 63); letter-spacing: 0.582857px; font-size: 14px; overflow-wrap: break-word !important;\"><span style=\"margin: 0px; padding: 0px; max-width: 100%; color: rgb(0, 82, 255); overflow-wrap: break-word !important;\"><strong style=\"margin: 0px; padding: 0px; max-width: 100%; overflow-wrap: break-word !important;\">世通君（微信ID：shitonghk002）</strong></span>——海外投资领域骨灰级老兵，专注海外移民、海外金融和海外房产投资超10年，一路从欧美加澳新走到东南亚各国，现专注柬埔寨房地产投资。</span></p><p style=\"margin-bottom: 0px; padding: 0px; border: 0px; font-size: 17.1429px; vertical-align: baseline; max-width: 100%; clear: both; min-height: 1em; color: rgb(51, 51, 51); font-family: -apple-system-font, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei UI&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; text-align: justify; letter-spacing: 0.582857px; overflow-wrap: break-word !important;\">&nbsp;</p><p style=\"margin-bottom: 0px; padding: 0px; border: 0px; font-size: 17.1429px; vertical-align: baseline; max-width: 100%; clear: both; min-height: 1em; color: rgb(51, 51, 51); font-family: -apple-system-font, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei UI&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; text-align: justify; letter-spacing: 0.582857px; overflow-wrap: break-word !important;\"><span style=\"margin: 0px; padding: 0px; max-width: 100%; color: rgb(63, 63, 63); font-size: 14px; overflow-wrap: break-word !important;\">过去3年，世通君每年超过200天在柬埔寨，双脚深入考察过金边和西港99%的楼盘，带领团队为超过2000组投资者提供过咨询，清楚各家开发商的虚实，了解各投资者踩过的坑。</span></p><p style=\"margin-bottom: 0px; padding: 0px; border: 0px; font-size: 17.1429px; vertical-align: baseline; max-width: 100%; clear: both; min-height: 1em; color: rgb(51, 51, 51); font-family: -apple-system-font, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei UI&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; text-align: justify; letter-spacing: 0.582857px; overflow-wrap: break-word !important;\"><span style=\"margin: 0px; padding: 0px; max-width: 100%; color: rgb(63, 63, 63); font-size: 14px; overflow-wrap: break-word !important;\"><br style=\"margin: 0px; padding: 0px; max-width: 100%; overflow-wrap: break-word !important;\">柬埔寨房产投资不踩坑，就找世通君，可先添加微信（136&nbsp;9223&nbsp;5060&nbsp;电话和微信同号），世通君有空的时候会和你们聊聊柬埔寨房产投资那些事！</span></p></li></ul></div></div></div></div>");
                    projectIntroduction.setName("投资分析");
                    projectRepo.insertProjectIntro(projectIntroduction,i);
                }
            }
            for(int j=1;j<=3;j++) {
                projectRepo.insertTowerType("Tower "+j, i);
                ProjectRepo.PropertyType propertyType=new ProjectRepo.PropertyType();
                propertyType.setBathroom((int) (Math.random() * 8) + 1);
                propertyType.setBedroom((int) (Math.random() * 8) + 1);
                propertyType.setFloor((int) (Math.random() * 8) + 1);
                propertyType.setHeight(555.3);
                propertyType.setWidth(324.98);
                propertyType.setParking((int) (Math.random() * 8) + 1);
                propertyType.setType("Property Type "+j);
                projectRepo.insertPropertyType(propertyType,i);
            }
        }
        return "OK";
    }

    @ResponseBody
    @GetMapping("/data/event-slide")
    public String dataEvent(){
        for(int i=1;i<=5;i++){
            EventRepo.EventRequest eventRequest=new EventRepo.EventRequest();
            eventRequest.setDescription("The 2020 One21 Experience® will be a CENTURY 21 exclusive event, elevated by learning from top-tier speakers and personalities, an amazing expo featuring the products and services of our world-class partners, and first-class entertainment to keep our days well-rounded.");
            eventRequest.setTitle("HINDSIGHT IS 2020");
            eventRequest.setMessage("The 2020 One21 Experience® will be a CENTURY 21 exclusive event, elevated by learning from top-tier speakers and personalities, an amazing expo featuring the products and services of our world-class partners, and first-class entertainment to keep our days well-rounded.");
            eventRepo.insertEvent(new ID(),eventRequest,i+".jpg",new Timestamp(new Date().getTime()));
        }

        for(int i=1;i<=4;i++){
            addSliderRepo.addSlider("slider"+i,i+".jpg");
        }

        return "OK";
    }
}
