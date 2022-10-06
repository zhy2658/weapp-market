package com.example.entity;

import lombok.Data;

import java.util.List;

@Data
public class PublishAndPublishImg {

    private Publish publish;
    private List<PublishImg> publishImgs;
}
