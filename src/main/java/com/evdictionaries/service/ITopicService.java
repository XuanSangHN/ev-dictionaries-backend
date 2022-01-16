package com.evdictionaries.service;

import com.evdictionaries.payload.request.TopicRequest;

public interface ITopicService {
    TopicRequest createOrUpdateTopic(TopicRequest topicRequest);

    void deleteTopicById(Long[] ids);
}
