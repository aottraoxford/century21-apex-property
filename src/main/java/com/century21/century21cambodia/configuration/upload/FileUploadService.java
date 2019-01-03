package com.century21.century21cambodia.configuration.upload;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileUploadService {

    public String storeImage(MultipartFile file,String fileProperty){
        Path path = Paths.get(fileProperty).toAbsolutePath().normalize();
        File directory = new File(path.toString());
        if(!directory.exists()){
            directory.mkdirs();
        }
        String fileName = UUID.randomUUID()+file.getOriginalFilename();
        try {
            Files.copy(file.getInputStream(),path.resolve(fileName),StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new CustomRuntimeException(500,e.getMessage());
        }
        return fileName;
    }
    public List<String> storeImages(MultipartFile[] file,String fileProperty){
        Path path = Paths.get(fileProperty) .toAbsolutePath().normalize();
        File directory = new File(path.toString());
        List<String> fileName=new ArrayList<>();
        if(!directory.exists()){
            directory.mkdirs();
        }
        try {
            for (int i = 0; i < file.length; i++) {
                String fn = UUID.randomUUID()+file[i].getOriginalFilename();
                fileName.add(fn);
                Files.copy(file[i].getInputStream(), path.resolve(fn), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new CustomRuntimeException(500, e.getMessage());
        }
        return fileName;
    }
    public boolean removeImage(String fileName,String fileProperty){
        Path path = Paths.get(fileProperty) .toAbsolutePath().normalize();
        Path filePath = path.resolve(fileName).normalize();
        File file = new File(filePath.toString());
        if (file.exists())
            return file.delete();
        else
            return false;
    }
    public Resource loadFile(String fileName,String fileProperty){
        try {
            Path path = Paths.get(fileProperty).toAbsolutePath().normalize();
            Path filePath = path.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            resource.contentLength();

            return resource;
        } catch (MalformedURLException e) {
            throw new CustomRuntimeException(500,e.getMessage());
        }catch (IOException e) {
            throw new CustomRuntimeException(404,"File not found.");
        }
    }
}
