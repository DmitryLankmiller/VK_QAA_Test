package com.example;


public class GroupsGetCountersDTO {
    private CountersDTO counters;

    public CountersDTO getCounters() {
        return counters;
    }

    public GroupsGetCountersDTO setCounters(CountersDTO counters) {
        this.counters = counters;
        return this;
    }
}
