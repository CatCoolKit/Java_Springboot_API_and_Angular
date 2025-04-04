package com.dailycodework.dream_shops.service.image;

import com.dailycodework.dream_shops.dto.ImageDTO;
import com.dailycodework.dream_shops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDTO> saveImage(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
