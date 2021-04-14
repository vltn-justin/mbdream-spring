package com.chamalo.mbdream.responses;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Abstract class to define method for response JSON
 *
 * @param <M> Model for response
 */
public abstract class Response<M> {

    public Map<String, Object> buildResponse(final ResponseType type, final M model) {
        Map<String, Object> map = new LinkedHashMap<>();

        switch (type) {
            case BASIC:
                this.basicResponse(map, model);
                break;
            case LIGHT:
                this.lightResponse(map, model);
                break;
        }

        return map;
    }

    protected abstract void basicResponse(final Map<String, Object> map, final M model);

    protected abstract void lightResponse(final Map<String, Object> map, final M model);
}
