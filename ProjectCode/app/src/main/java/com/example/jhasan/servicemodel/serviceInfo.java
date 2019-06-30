package com.example.jhasan.servicemodel;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by jhasan on 22/6/19.
 */

public class serviceInfo {

    private String serviceName;
    private String address;
    private String ServiceDescription;
    private String ServiceProviderContact;
    private ArrayList<String>ServiceImageLink;
    private double ServiceRating;
    private int Image;
    private int ServiceFees;



    private LatLng houseLatlng;
    private String ServiceTitle;
    private String ServieFeePrefer;
    private String ServiceCategory;

    //Constructor
    public serviceInfo(){

    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServiceDescription() {
        return ServiceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        ServiceDescription = serviceDescription;
    }

    public String getServiceProviderContact() {
        return ServiceProviderContact;
    }

    public void setServiceProviderContact(String serviceProviderContact) {
        ServiceProviderContact = serviceProviderContact;
    }

    public ArrayList<String> getServiceImageLink() {
        return ServiceImageLink;
    }

    public void setServiceImageLink(ArrayList<String> serviceImageLink) {
        ServiceImageLink = serviceImageLink;
    }

    public double getServiceRating() {
        return ServiceRating;
    }

    public void setServiceRating(double serviceRating) {
        ServiceRating = serviceRating;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public int getServiceFees() {
        return ServiceFees;
    }

    public void setServiceFees(int serviceFees) {
        ServiceFees = serviceFees;
    }

    public String getServiceTitle() {
        return ServiceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        ServiceTitle = serviceTitle;
    }

    public String getServieFeePrefer() {
        return ServieFeePrefer;
    }

    public void setServieFeePrefer(String servieFeePrefer) {
        ServieFeePrefer = servieFeePrefer;
    }

    public String getServiceCategory() {
        return ServiceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        ServiceCategory = serviceCategory;
    }


    public LatLng getHouseLatlng() {
        return houseLatlng;
    }

    public void setHouseLatlng(LatLng houseLatlng) {
        this.houseLatlng = houseLatlng;
    }



}
