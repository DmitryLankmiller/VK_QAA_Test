package com.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountersDTO {
    @JsonProperty("ads_topics")
    private String adsTopics;
    @JsonProperty("black_list")
    private String blackList;
    private String catalogs;
    @JsonProperty("delayed_topics")
    private String delayedTopics;
    private String friends;
    @JsonProperty("join_requests")
    private String joinRequests;
    private String links;
    private String maybe;
    private String members;
    private String moderators;
    @JsonProperty("music_tracks")
    private String musicTracks;
    @JsonProperty("new_paid_topics")
    private String newPaidTopics;
    @JsonProperty("own_products")
    private String ownProducts;
    @JsonProperty("paid_members")
    private String paidMembers;
    @JsonProperty("paid_topics")
    private String paidTopics;
    @JsonProperty("photo_albums")
    private String photoAlbums;
    private String photos;
    @JsonProperty("pinned_topics")
    private String pinnedTopics;
    private String presents;
    private String products;
    @JsonProperty("promo_on_moderation")
    private String promoOnModeration;
    @JsonProperty("suggested_products")
    private String suggestedProducts;
    @JsonProperty("suggested_topics")
    private String suggestedTopics;
    private String themes;
    @JsonProperty("unpublished_topics")
    private String unpublishedTopics;
    private String videos;

    public String getAdsTopics() {
        return adsTopics;
    }

    public CountersDTO setAdsTopics(String adsTopics) {
        this.adsTopics = adsTopics;
        return this;
    }

    public String getBlackList() {
        return blackList;
    }

    public CountersDTO setBlackList(String blackList) {
        this.blackList = blackList;
        return this;
    }

    public String getCatalogs() {
        return catalogs;
    }

    public CountersDTO setCatalogs(String catalogs) {
        this.catalogs = catalogs;
        return this;
    }

    public String getDelayedTopics() {
        return delayedTopics;
    }

    public CountersDTO setDelayedTopics(String delayedTopics) {
        this.delayedTopics = delayedTopics;
        return this;
    }

    public String getFriends() {
        return friends;
    }

    public CountersDTO setFriends(String friends) {
        this.friends = friends;
        return this;
    }

    public String getJoinRequests() {
        return joinRequests;
    }

    public CountersDTO setJoinRequests(String joinRequests) {
        this.joinRequests = joinRequests;
        return this;
    }

    public String getLinks() {
        return links;
    }

    public CountersDTO setLinks(String links) {
        this.links = links;
        return this;
    }

    public String getMaybe() {
        return maybe;
    }

    public CountersDTO setMaybe(String maybe) {
        this.maybe = maybe;
        return this;
    }

    public String getMembers() {
        return members;
    }

    public CountersDTO setMembers(String members) {
        this.members = members;
        return this;
    }

    public String getModerators() {
        return moderators;
    }

    public CountersDTO setModerators(String moderators) {
        this.moderators = moderators;
        return this;
    }

    public String getMusicTracks() {
        return musicTracks;
    }

    public CountersDTO setMusicTracks(String musicTracks) {
        this.musicTracks = musicTracks;
        return this;
    }

    public String getNewPaidTopics() {
        return newPaidTopics;
    }

    public CountersDTO setNewPaidTopics(String newPaidTopics) {
        this.newPaidTopics = newPaidTopics;
        return this;
    }

    public String getOwnProducts() {
        return ownProducts;
    }

    public CountersDTO setOwnProducts(String ownProducts) {
        this.ownProducts = ownProducts;
        return this;
    }

    public String getPaidMembers() {
        return paidMembers;
    }

    public CountersDTO setPaidMembers(String paidMembers) {
        this.paidMembers = paidMembers;
        return this;
    }

    public String getPaidTopics() {
        return paidTopics;
    }

    public CountersDTO setPaidTopics(String paidTopics) {
        this.paidTopics = paidTopics;
        return this;
    }

    public String getPhotoAlbums() {
        return photoAlbums;
    }

    public CountersDTO setPhotoAlbums(String photoAlbums) {
        this.photoAlbums = photoAlbums;
        return this;
    }

    public String getPhotos() {
        return photos;
    }

    public CountersDTO setPhotos(String photos) {
        this.photos = photos;
        return this;
    }

    public String getPinnedTopics() {
        return pinnedTopics;
    }

    public CountersDTO setPinnedTopics(String pinnedTopics) {
        this.pinnedTopics = pinnedTopics;
        return this;
    }

    public String getPresents() {
        return presents;
    }

    public CountersDTO setPresents(String presents) {
        this.presents = presents;
        return this;
    }

    public String getProducts() {
        return products;
    }

    public CountersDTO setProducts(String products) {
        this.products = products;
        return this;
    }

    public String getPromoOnModeration() {
        return promoOnModeration;
    }

    public CountersDTO setPromoOnModeration(String promoOnModeration) {
        this.promoOnModeration = promoOnModeration;
        return this;
    }

    public String getSuggestedProducts() {
        return suggestedProducts;
    }

    public CountersDTO setSuggestedProducts(String suggestedProducts) {
        this.suggestedProducts = suggestedProducts;
        return this;
    }

    public String getSuggestedTopics() {
        return suggestedTopics;
    }

    public CountersDTO setSuggestedTopics(String suggestedTopics) {
        this.suggestedTopics = suggestedTopics;
        return this;
    }

    public String getThemes() {
        return themes;
    }

    public CountersDTO setThemes(String themes) {
        this.themes = themes;
        return this;
    }

    public String getUnpublishedTopics() {
        return unpublishedTopics;
    }

    public CountersDTO setUnpublishedTopics(String unpublishedTopics) {
        this.unpublishedTopics = unpublishedTopics;
        return this;
    }

    public String getVideos() {
        return videos;
    }

    public CountersDTO setVideos(String videos) {
        this.videos = videos;
        return this;
    }
}
