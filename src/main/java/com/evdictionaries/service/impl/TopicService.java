package com.evdictionaries.service.impl;

import com.evdictionaries.payload.request.TopicRequest;
import com.evdictionaries.service.ITopicService;
import org.springframework.stereotype.Service;

@Service
public class TopicService implements ITopicService {
    @Override
    public TopicRequest createOrUpdateTopic(TopicRequest topicRequest) {
        return null;
    }

    @Override
    public void deleteTopicById(Long[] ids) {

    }
}
