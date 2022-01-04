package com.example.S3.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.S3.Service.AmazonClient;

@RestController
@RequestMapping("/storage")
public class BucketController {

	 @Autowired
	 private AmazonClient amazonClient;

	  
	    BucketController(AmazonClient amazonClient) {
	        this.amazonClient = amazonClient;
	    }

	    @PostMapping("/uploadFile")
	    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
	        return this.amazonClient.uploadFile(file);
	    }

	    @DeleteMapping("/deleteFile")
	    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
	        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
	     
	    }
	    
	   @GetMapping("/download/{fileName}")
	   public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName){
		   byte[] data = amazonClient.downloadFile(fileName);
		   ByteArrayResource resource=new ByteArrayResource(data);
		   return ResponseEntity
				   .ok()
				   .contentLength(data.length)
				   .header("Content-type", "application/octet-stream")
				   .header("Content-disposition", "attachment;filename=\""+fileName+"\"")
				   .body(resource);
		   
		    }
			/*
			 * @RequestMapping(value="/copyFile" ,method = RequestMethod.GET)
			 * public @RequestBody Object CopyBucketRequest(@RequestParam(value="fileName",
			 * required = true)String fileName,
			 * 
			 * @RequestParam(value = "folderName", required = false)String folderName) {
			 * DBObject response = new BasicDBObject(); HttpStatus status = HttpStatus.OK;
			 * 
			 * try {
			 * 
			 * String msg = s3AmazonClient.copyBucketData(fileName, bucketName ,folderName,
			 * "testcopy"); response.put("msg" ,msg); } catch (Exception e) {
			 * response.put("msg",e.getMessage()); } return new
			 * ResponseEntity<DBObject>(response status); }
			 */

}

