package com.femcoders.happy_travel.cloudinary;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "happy_travel",
                "api_key", "325723638491756",
                "api_secret", "Ns0ohxoGq2fc5EVr_48qfrt7gX0"
        ));
    }
}
