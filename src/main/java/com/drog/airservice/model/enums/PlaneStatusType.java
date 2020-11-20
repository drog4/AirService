package com.drog.airservice.model.enums;

public enum PlaneStatusType {
    AIRPORT, AIR;

    public static PlaneStatusType getStatusType(String statusInType) {
        for (PlaneStatusType type: PlaneStatusType.values()) {
            if (type.name().equals(statusInType)) {
                return type;
            }
        }
        return null;
    }
}
