package com.donalola.chef.domain;

import com.donalola.ChefID;
import com.donalola.core.Location;
import com.donalola.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Slf4j
@Component
public class ChefServiceDelegator implements ChefManager {

    private final ChefRepository chefRepository;
    private final ChefService chefService;

    private static final int IMG_WIDTH = 150;
    private static final int IMG_HEIGHT = 150;
    private static final long MAX_SIZE = 102400;

    public ChefServiceDelegator(ChefRepository chefRepository, ChefService chefService) {
        this.chefRepository = chefRepository;
        this.chefService = chefService;
    }

    @Override
    public Chef add(Chef chef) {
        //Resizing image size if necessary
        if (StringUtils.isNotEmpty(chef.getImage()) && isReducingNecessary(chef.getImage())) {
            Chef optimizedChef = Chef.builder(chef.getId())
                    .Base64Image(resize(chef.getImage()))
                    .ClosingOn(chef.getClosingSchedule())
                    .OpeningOn(chef.getOpeningSchedule())
                    .Identity(chef.getIdentity().getType(), chef.getIdentity().getValue())
                    .Location(chef.getLocation())
                    .Name(chef.getName())
                    .Phone(chef.getPhone())
                    .AttentionTypes(chef.getAttentionTypes())
                    .build();
            return this.chefRepository.save(optimizedChef);
        }
        return this.chefRepository.save(chef);
    }

    private String resize(String base64Image) {
        File originalImage = ImageUtil.decodeFromBase64(base64Image);
        File resizedImage = ImageUtil.resizeImage(originalImage, IMG_WIDTH, IMG_HEIGHT);
        return ImageUtil.encodeToBase64(resizedImage);
    }

    private boolean isReducingNecessary(String base64Image) {
        File imageFile = ImageUtil.decodeFromBase64(base64Image);
        return NumberUtils.compare(MAX_SIZE, FileUtils.sizeOf(imageFile)) < 0;
    }

    @Override
    public List<Chef> listNearOf(Location location, Integer radius) {
        return this.chefService.findNearbyChefs(location, radius);
    }

    @Override
    public Chef get(ChefID chefID) {
        return this.chefRepository.get(chefID);
    }

    @Override
    public List<Chef> findByName(String name) {
        return this.chefRepository.findByName(name);
    }

}
