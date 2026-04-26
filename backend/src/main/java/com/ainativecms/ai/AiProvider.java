package com.ainativecms.ai;

import com.ainativecms.entity.AiConfig;

public interface AiProvider {

    AiConfig.AiProviderType getType();

    AiResponse chat(AiConfig config, AiRequest request) throws Exception;
}
