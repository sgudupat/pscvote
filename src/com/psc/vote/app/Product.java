package com.psc.vote.app;

public class Product {

    private String anchorName;
    private String clientName;
    private String campaignId;

    public Product(String anchorName, String clientName, String campaignId) {
        super();
        this.anchorName = anchorName;
        this.clientName = clientName;
        this.campaignId = campaignId;
    }

    public String getAnchorName() {
        return anchorName;
    }

    public void setAnchorName(String anchorName) {
        this.anchorName = anchorName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    @Override
    public String toString() {
        return "Product [anchorName=" + anchorName + ", clientName=" + clientName
                + ", campaignId=" + campaignId + "]";
    }
}