package com.hackathon.digitallifevoice.data;

/**
 * Created by SWBRADSH on 2/20/2015.
 */
public class Action {

    private long id;
    private String voiceCommand;
    private String deviceType;
    private String deviceGuid;
    private String label;
    private String operation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVoiceCommand() {
        return voiceCommand;
    }

    public void setVoiceCommand(String voiceCommand) {
        this.voiceCommand = voiceCommand;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceGuid() {
        return deviceGuid;
    }

    public void setDeviceGuid(String deviceGuid) {
        this.deviceGuid = deviceGuid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }



}
