package com.century21.century21cambodia.controller;

import com.century21.century21cambodia.repository.api_modify_event_status.ModifyEventStatusRepo;
import com.century21.century21cambodia.repository.api_new_project.Project;
import com.century21.century21cambodia.repository.api_new_project.ProjectIntroduction;
import com.century21.century21cambodia.repository.api_new_project.PropertyType;
import com.century21.century21cambodia.repository.api_post_event.PostEventRepo;
import com.century21.century21cambodia.repository.api_project_statistic.ProjectStatisticRepo;
import com.century21.century21cambodia.repository.api_project_userfavorite.ProjectFavoriteRepo;
import com.century21.century21cambodia.repository.api_projects.ProjectRepo;
import com.century21.century21cambodia.repository.api_silder_add.AddSliderRepo;
import com.century21.century21cambodia.repository.api_slider.SliderRepo;
import com.century21.century21cambodia.repository.api_slider_update.SliderUpdateRepo;
import com.century21.century21cambodia.repository.api_upload_project_images.ProjectGalleryRepo;
import com.century21.century21cambodia.repository.api_visible_project.VisibleProjectRepo;
import com.century21.century21cambodia.service.api_new_project.NewProjectService;
import com.century21.century21cambodia.service.api_user_reset_pass.UserResetPassService;
import com.century21.century21cambodia.util.ImageUtil;
import com.century21.century21cambodia.util.JwtUtil;
import com.century21.century21cambodia.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
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
            project.setDescription("Located in the beating heart of the most desirable district of Phnom Penh, The property is your most prestigious urban address. A combination of inspirational architecture, elegant interiors, and signature elevated gardens makes it the ultimate place to be. This is urban living at its finest.");
            project.setDownPayment("50-60");
            if(i%2==0) project.setStatus("rent");
            else project.setStatus("buy");
            if(i%10==0) project.setStatus("rent,buy");
            project.setMinPrice((Math.random()*100000)+10000);
            project.setMaxPrice((Math.random()*1000000)+100000);
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
                    case 0:projectIntroduction.setName("Graphic Feature");break;
                    case 1:projectIntroduction.setName("Buyer Information");break;
                    case 2:projectIntroduction.setName("Commitment");break;
                }
                projectIntroduction.setDescription("Herd Management Areas (HMA) are lands under the supervision of the United States Bureau of Land Management that are managed for the primary but not exclusive benefit of free-roaming \"wild\" horses and burros. While these animals are technically feral equines descended from foundation stock that was originally domesticated, the phrase \"wild horse\" (and wild burro) has a specific meaning in United States law, giving special legal status to the descendants of equines that were \"unmarked and unclaimed\" on public lands at the time the Wild and Free-Roaming Horses and Burros Act of 1971 was passed. There are approximately 270 HMAs across 10 states, comprising 31,600,000 acres (12,800,000 ha). Equine population estimates in each HMA can vary significantly from year to year, depending on habitat condition in a given area, fecundity of the animals, or if a gather has occurred. The original feral horse herds in the Americas were of Spanish horse ancestry. Additional stock brought by eastern settlers moving west, ranging from draft horses to Arabians and Thoroughbreds, added a variety of other horse types.");
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
            sliderUpdateRepo.sliderUpdate(true,(i-5)+".jpg",(i-5));
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
