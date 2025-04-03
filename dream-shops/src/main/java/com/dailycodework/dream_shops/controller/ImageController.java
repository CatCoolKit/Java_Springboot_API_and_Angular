package com.dailycodework.dream_shops.controller;

import com.dailycodework.dream_shops.dto.ImageDTO;
import com.dailycodework.dream_shops.model.Image;
import com.dailycodework.dream_shops.response.ApiResponse;
import com.dailycodework.dream_shops.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {
    private final IImageService iImageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(
            @RequestParam List<MultipartFile> files,
            @RequestParam Long productId
    ){
        try {
            List<ImageDTO> imageDTOS = iImageService.saveImage(files, productId);

            return ResponseEntity.ok(ApiResponse.builder()
                            .message("Upload success")
                            .data(imageDTOS)
                    .build());
        } catch (Exception  e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                            .message("Upload failed!")
                            .data(e.getMessage())
                    .build());
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(
            @PathVariable Long imageId
    ) throws SQLException {
        Image image = iImageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(
            @PathVariable Long imageId, @RequestBody MultipartFile file
    ){
        try {
            Image image = iImageService.getImageById(imageId);
            if(image != null){
                iImageService.updateImage(file, imageId);
                return ResponseEntity.ok(ApiResponse.builder()
                                .message("Update success!")
                                .data(null)
                        .build());
            }
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                            .message(e.getMessage())
                            .data(null)
                    .build());
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                .message("Update failed!")
                .data(INTERNAL_SERVER_ERROR)
                .build());
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(
            @PathVariable Long imageId
    ){
        try {
            Image image = iImageService.getImageById(imageId);
            if(image != null){
                iImageService.deleteImageById(imageId);
                return ResponseEntity.ok(ApiResponse.builder()
                        .message("Delete success!")
                        .data(null)
                        .build());
            }
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .data(null)
                    .build());
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                .message("Delete failed!")
                .data(INTERNAL_SERVER_ERROR)
                .build());
    }
}
