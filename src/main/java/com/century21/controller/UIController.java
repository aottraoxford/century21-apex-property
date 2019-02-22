package com.century21.controller;

import com.century21.repository.api_modify_event_status.ModifyEventStatusRepo;
import com.century21.repository.api_new_project.Project;
import com.century21.repository.api_new_project.ProjectIntroduction;
import com.century21.repository.api_new_project.PropertyType;
import com.century21.repository.api_post_event.PostEventRepo;
import com.century21.repository.api_silder_add.AddSliderRepo;
import com.century21.repository.api_slider_update.SliderUpdateRepo;
import com.century21.repository.api_upload_project_images.ProjectGalleryRepo;
import com.century21.repository.api_visible_project.VisibleProjectRepo;
import com.century21.service.api_new_project.NewProjectService;
import com.century21.service.api_user_reset_pass.UserResetPassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class UIController {
    @GetMapping({"/"})
    public String swagger() {
        return "redirect:/swagger-ui.html";
    }

    @Autowired
    private ProjectGalleryRepo projectGalleryRepo;
    @Autowired
    private VisibleProjectRepo visibleProjectRepo;
    @Autowired
    private NewProjectService newProjectService;
    @ResponseBody
    @GetMapping("/data/projects")
    public String dataProjects(@RequestParam int num){
        for(int i=1;i<=num;i++){
            Project project=new Project();
            project.setAddressOne(i+"Songkat beong tompun,Khan mean chey");
            project.setAddressTwo(i+"#1234,street 271");
            project.setName(i+"Crystal Condominium ");
            project.setBuiltDate(new Date());
            project.setCompletedDate(new Date());
            project.setAvgRentFrom(0.3);
            project.setAvgRentTo(0.7);
            project.setGrr(0.6);
            project.setDescription("<p>It is adjacent to the Wangu Lake Super City, the mature valley of the Valley, the Prime Minister's Office, the Phnom Penh Municipal Government, embassies, railway stations, large shopping malls, hospitals, banks, and Central Park.</p><p>Developer:&nbsp;J&amp;L Property Development Co., Ltd.</p><p>Property Type:&nbsp;Apartment</p><p>Date of delivery:&nbsp;2018.12</p><p>Can you pay for the goods:&nbsp;Yes</p><p>Years of property rights:</p><p>Planning area:&nbsp;32-140 square meters</p><p>Number of households:</p>");
            project.setDownPayment("50-60");
            if(i%2==0) project.setStatus("rent");
            else project.setStatus("buy");
            if(i%10==0) project.setStatus("rent,buy");
            project.setMinPrice((int)(Math.random()*100000)+10000);
            project.setMaxPrice((int)(Math.random()*1000000)+100000);
            int cid=((int)(Math.random()*3)+1);
            project.setCountryID(cid);
            switch (cid){
                case 1 : project.setCity("Phnom Penh"); break;
                case 2 : project.setCity("Dubai") ;break;
                case 3 : project.setCity("Kaula Lumpur"); break;
            }
            int pid=((int)(Math.random()*4)+1);
            project.setProjectTypeID(pid);
            if(pid!=2){
                List<PropertyType> propertyTypes=new ArrayList<>();
                for(int k=0;k<5;k++) {
                    PropertyType propertyType = new PropertyType();
                    propertyType.setBathroom(((int) Math.random() * 2) + 1);
                    propertyType.setBedroom(((int) Math.random() * 5) + 1);
                    propertyType.setFloor(((int) Math.random() * 4) + 1);
                    propertyType.setParking(((int) Math.random() * 1) + 1);
                    propertyType.setType("E4");
                    propertyType.setWidth(Math.random() * 50);
                    propertyType.setHeight(Math.random() * 200);
                    propertyTypes.add(propertyType);
                }
                project.setPropertyTypes(propertyTypes);

            }
            List<ProjectIntroduction> projectIntroductions=new ArrayList<>();
            for(int j=0;j<3;j++){
                ProjectIntroduction projectIntroduction=new ProjectIntroduction();
                switch (j){
                    case 0:
                        projectIntroduction.setName("Graphic Feature");
                        projectIntroduction.setDescription("<p><strong>Project introduction: Phnom Penh Hotel Apartments, commercial and residential</strong></p><p><strong>Project Location: Phnom Penh Prime Minister's Office, next to the subway</strong></p><p><img src='http://si1.go2yd.com/get-image/0JI1dwfShWa'></p><p><strong>Value highlights:</strong></p><p>1.&nbsp;&nbsp;<strong>Sending fine decoration and home appliance bag</strong>&nbsp;;</p><p>2.&nbsp;&nbsp;<strong>Five-star management property value-added;</strong></p><p>3.&nbsp;&nbsp;<strong>Sky tree price advantage: the lowest price of the second ring of Phnom Penh is&nbsp;1700-2000&nbsp;US dollars&nbsp;/&nbsp;square meter;</strong></p><p>4.&nbsp;<strong>Sky Tree is located beside Phnom Penh International Financial Center, 10 kilometers from the airport to the sky tree, 20 minutes by car.&nbsp;</strong>Look at the Prime Minister's Office, the Phnom Penh City Government, embassies, railway stations, large shopping malls, hospitals, banks, and Central Park.</p><p>5.&nbsp;&nbsp;<strong>30% down payment, pay the balance at the time of delivery or apply for a mortgage loan, accept RMB payment.</strong></p><p><img src='http://si1.go2yd.com/get-image/0JI1hXy13Am'><img src='http://si1.go2yd.com/get-image/0JI1hWzOR5E'><img src='http://si1.go2yd.com/get-image/0JI1hXCJ1HM'><img src='http://si1.go2yd.com/get-image/0JI1hfRGvgW'><img src='http://si1.go2yd.com/get-image/0JI1hfzrg0G'></p><p>Investment Analysis:</p><p><strong>Investment in Cambodia Sky Tree Advantage:</strong></p><p>1. The lowest price in the whole of Phnom Penh (the second ring of the capital)&nbsp;</p><p>2. Planning for the S3 subway exit near the Belt and Road Trans-Asian Railway&nbsp;</p><p>3. Influx of foreigners, high occupancy rate&nbsp;</p><p>4. Soft card transaction No real estate transaction tax&nbsp;</p><p>5. Five-star management, property value-added, rent-on-sale, consignment, escrow, repair&nbsp;</p><p>6. Landscape landmarks overlooking Phnom Penh&nbsp;</p><p><img src='http://si1.go2yd.com/get-image/0JI224awNNY'><img src='http://si1.go2yd.com/get-image/0JI22VZ0MxE'><img src='http://si1.go2yd.com/get-image/0JI22FRLvg8'><img src='http://si1.go2yd.com/get-image/0JI22FmxwcS'><img src='http://si1.go2yd.com/get-image/0JI22R0jM0m'><img src='http://si1.go2yd.com/get-image/0JI22SbeAqm'></p>");
                        break;
                    case 1:
                        projectIntroduction.setName("Buyer Information");
                        projectIntroduction.setDescription("<h2>The hole purchase</h2><p>1. Select the house model and room number&nbsp;</p><p>2. After confirming the house, pay the deposit of 2,000 USD,&nbsp;</p><p>3, 14 working days to sign the purchase contract and pay the down payment of 30%.&nbsp;</p><p>4.&nbsp;Pay&nbsp;later according to the progress of the project.</p><p><br></p><h2>Purchase goods, The purchase process</h2><p>1, choose the house model and room number&nbsp;</p><p>2, determine the house and pay a deposit of 2000 dollars</p>");

                        break;
                    case 2:
                        projectIntroduction.setName("Commitment Latter");
                        projectIntroduction.setDescription("<p><img src='http://www.shitonghk.com/public/m/images/zhiye.jpg'></p><p><strong>WorldCom Overseas:</strong></p><p>Selected listed real estate developers in Southeast Asia's quality projects&nbsp;<strong>NO.1</strong></p><p>Perfect one-stop Southeast Asian home purchase server system&nbsp;<strong>NO.1</strong></p><p>VIP car viewing experience&nbsp;<strong>NO.1</strong></p><p><strong>Commitment 1: Optimize quality properties</strong></p><p>1. Directly connect with the developer and build a new house.</p><p>2. Preferred listed company real estate</p><p>3. Non-listed companies are mainly based on existing and quasi-existing houses.</p><p>4, do the due diligence in the field to eliminate the risk of bad tail</p><p><strong>Commitment 2: One-stop service</strong></p><p>1, overseas viewing VIP exclusive transfer service</p><p>2. Contract details and Q&amp;A services</p><p>3, the house acceptance agent</p><p>4, rental housing rental resale agency recommended</p><p><strong>Commitment 3: double compensation for premium</strong></p><p>1, the price is transparent, no additional fees</p><p>2, double compensation difference</p>");
                        break;
                }
                projectIntroductions.add(projectIntroduction);
            }
            project.setProjectIntroductions(projectIntroductions);
            newProjectService.createNewProject(project);
        }
        return "GOOD";
    }

    @ResponseBody
    @GetMapping("/data/image")
    public String dataThumbnail(@RequestParam int num){
        for(int i=1;i<=num;i++){
            int th=((int)(Math.random()*8)+1);
            projectGalleryRepo.saveThumbnail(th+".jpg",i);
            for(int j=1;j<=6;j++){
                int gr=((int)(Math.random()*8)+1);
                projectGalleryRepo.saveGallery(gr+".jpg",i);
            }
        }
        return "GOOD";
    }
    @ResponseBody
    @GetMapping("/data/enable")
    public String dataEnableProject(@RequestParam int num){
        for(int i=1;i<=num;i++)
            visibleProjectRepo.visibleProject(true,i);
        return "GOOD";
    }

    @Autowired
    private PostEventRepo postEventRepo;
    @Autowired
    private ModifyEventStatusRepo modifyEventStatusRepo;
    @Autowired
    private AddSliderRepo addSliderRepo;
    @Autowired
    private SliderUpdateRepo sliderUpdateRepo;
    @ResponseBody
    @GetMapping("/data/events-slider")
    public String dataEventsSlider(){
        for(int i=1;i<=5;i++){
            String title="21st Century Cambodia Housing Group";
            String description = "After listening to the briefing session, is it also the site visit?\n" +
                    "\n" +
                    "In order to make our customers' safer investment, we regularly hold the \"21st Century Cambodian Housing Group\", which will be used by special passengers to inspect the development projects in the Phnom Penh area. Each time we will arrange 5 properties to provide detailed investment information and analysis of real estate market.\n" +
                    "\n" +
                    "This month's viewing group arrangement:\n" +
                    "Saturday, September 29th, from 1:00 pm to 6:00 pm\n" +
                    "Saturday, October 13th, from 1:00 pm to 6:00 pm\n" +
                    "Saturday, October 27th, from 1:00 pm to 6:00 pm\n" +
                    "\n" +
                    "Meeting point: 21st Century Cambodia Real Estate Headquarters Downstairs Mao Zedong Avenue Opposite Chinese Embassy Park Cafe";
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = dateFormat.parse("2019-03-20");
                Timestamp timestamp = new Timestamp(parsedDate.getTime());
                postEventRepo.postEvent(title,description,timestamp,i+".jpg");
                modifyEventStatusRepo.updateStatus(i,true);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
        for(int i=6;i<=9;i++){
            addSliderRepo.addSlider("Slide "+(i-5),(i-5)+".jpg");
            sliderUpdateRepo.sliderUpdate(true,(i-5)+".jpg",i);
        }
        return "GOOD";
    }


    @Autowired
    private UserResetPassService userResetPassService;
    @ApiIgnore
    @GetMapping("api/user/reset/pass/{base64}")
    public String resetPass(@PathVariable("base64")String base64){
        if(userResetPassService.updateUserPassword(base64)) return "redirect:https://www.google.com/";
        else return "redirect:https://www.youtube.com/";

    }
}
