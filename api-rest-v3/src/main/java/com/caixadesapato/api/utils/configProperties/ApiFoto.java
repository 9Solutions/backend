package com.caixadesapato.api.utils.configProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(
        prefix = "solutions.api"
)
public class ApiFoto {
    private String url;

    public ApiFoto() {
    }

}
