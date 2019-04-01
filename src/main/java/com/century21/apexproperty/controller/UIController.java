package com.century21.apexproperty.controller;

import com.century21.apexproperty.model.ID;
import com.century21.apexproperty.model.request.SignUp;
import com.century21.apexproperty.repository.PropertyRepo;
import com.century21.apexproperty.repository.UserRepo;
import com.century21.apexproperty.repository.api_signup.SignUpRepo;
import com.century21.apexproperty.repository.api_user_upload_image.UserUploadImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;


@ApiIgnore
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

    @GetMapping({"/"})
    public String swagger() {
        return "redirect:/swagger-ui.html";
    }

    @ResponseBody
    @GetMapping("/data")
    public String data(int row){
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
            userRepo.updateRole(i,2);
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

        PropertyRepo.PropertyRequest propertyRequest=new PropertyRepo.PropertyRequest();
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
            propertyRequest.setTitle(i+"Property Available in Cambodia");
            propertyRequest.setDescription("Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, comes from a line in section 1.10.32");
            propertyRequest.setHouseNo("211");
            int ros=(int)(Math.random()*2)+1;
            if(ros==1) propertyRequest.setRentOrSell("Rent");
            else propertyRequest.setRentOrSell("Sell");
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
            for(int j=1;j<=6;j++){
                neighborhood.setPropertyID(i);
                neighborhood.setDistance(3.4);
                neighborhood.setAddress("Phnom Penh International Airport");
                propertyRepo.insertNeighborhood(neighborhood);
            }
        }

        return "SUCCESS";
    }
}
