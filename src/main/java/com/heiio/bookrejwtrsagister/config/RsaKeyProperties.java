package com.heiio.bookrejwtrsagister.config;

import com.heiio.bookrejwtrsagister.utils.RsaUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;


@ConfigurationProperties(prefix = "rsa.key")
@Data
public class RsaKeyProperties {
    private String publicKeyFile;
    private String privateKeyFile;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    public void createRsaKey() throws Exception {
        publicKey = RsaUtils.getPublicKey(publicKeyFile);
        privateKey = RsaUtils.getPrivateKey(privateKeyFile);
    }
}
