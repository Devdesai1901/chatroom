package com.tsv.implementation.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Link
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    private String  hostName;
    private int link;

    private String topic;



    public Link() {
    }

    public Link(String hostName, int link) {
        this.hostName = hostName;
        this.link = link;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getLink() {
        return link;
    }

    public void setLink(int link) {
        this.link = link;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
