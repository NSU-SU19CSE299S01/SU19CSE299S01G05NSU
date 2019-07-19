package com.project.jhasan.servicemodel;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by jhasan on 22/6/19.
 */

public class serviceInfo {

    private String UID;


    private  String serviceCategory;
    private String serviceName;
    private String address;
    private String ServiceDescription;
    private String ServiceProviderContact;
    private ArrayList<String>ServiceImageLink = new ArrayList();
    private double ServiceRating=0;
    private String Image;
    private String ServiceFees;



    private LatLng serviceLatlng;




    //Constructor
    public serviceInfo(){

    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }


    public String getserviceCategory() {
        return serviceCategory;
    }

    public void setserviceCategory(String serviceCategory) {
        this.serviceCategory=serviceCategory;
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getServiceFees() {
        return ServiceFees;
    }

    public void setServiceFees(String serviceFees) {
        ServiceFees = serviceFees;
    }

//    public String getServiceTitle() {
//        return ServiceTitle;
//    }
//
//    public void setServiceTitle(String serviceTitle) {
//        ServiceTitle = serviceTitle;
//    }


    public LatLng getServiceLatlng() {
        return serviceLatlng;
    }

    public void setServiceLatlng(LatLng houseLatlng) {
        this.serviceLatlng = serviceLatlng;
    }



}
