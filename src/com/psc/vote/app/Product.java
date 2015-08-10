package com.psc.vote.app;

import android.util.Log;

import java.util.Date;

public class Product {

    private String anchorName;
    private String clientName;
    private String campaignId;
    private String campaignStatusDescription;
    private Date campaignEndDate;

    public Product(String anchorName, String clientName, String campaignId, Date endDate) {
        super();
        this.anchorName = anchorName;
        this.clientName = clientName;
        this.campaignId = campaignId;
        this.campaignEndDate = endDate;
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

    public String getCampaignStatusDescription() {
        if (new Date().after(campaignEndDate)) {
            campaignStatusDescription = "Campaign expired";
        } else {
            campaignStatusDescription = "Campaign expires in " + calculateTime();
        }
        return campaignStatusDescription;
    }

    public void setCampaignStatusDescription(String campaignStatusDescription) {
        this.campaignStatusDescription = campaignStatusDescription;
    }

    public Date getCampaignEndDate() {
        return campaignEndDate;
    }

    public void setCampaignEndDate(Date campaignEndDate) {
        this.campaignEndDate = campaignEndDate;
    }

    public String isCampaignExpired() {
        Date currentDate = new Date();
        String expired = (currentDate.after(campaignEndDate) ? "Y" : "N");
        Log.i("expired::", expired);
        return expired;
    }

    private String calculateTime() {
        long endDate = campaignEndDate.getTime();
        long sysDate = new Date().getTime();
        long diffMilliSeconds = endDate - sysDate;
        long minutes = diffMilliSeconds / (1000 * 60);
        long hours = diffMilliSeconds / (1000 * 60 * 60);
        long days = diffMilliSeconds / (1000 * 60 * 60 * 24);
        if (days > 0) {
            return days + " day(s)";
        } else if (hours > 0) {
            return hours + " hour(s)";
        } else if (minutes > 0) {
            return minutes + " minute(s)";
        } else {
            return "soon";
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "anchorName='" + anchorName + '\'' +
                ", clientName='" + clientName + '\'' +
                ", campaignId='" + campaignId + '\'' +
                ", campaignStatusDescription='" + campaignStatusDescription + '\'' +
                '}';
    }
}