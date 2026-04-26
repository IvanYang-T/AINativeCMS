package com.ainativecms.ai;

import com.ainativecms.entity.AiConfig;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AiProviderFactory {

    private final Map<AiConfig.AiProviderType, AiProvider> providers;

    public AiProviderFactory(List<AiProvider> providerList) {
        this.providers = providerList.stream()
                .collect(Collectors.toMap(AiProvider::getType, p -> p));
    }

    public AiProvider get(AiConfig.AiProviderType type) {
        AiProvider provider = providers.get(type);
        if (provider == null) {
            throw new IllegalArgumentException("Unsupported AI provider: " + type);
        }
        return provider;
    }
}
