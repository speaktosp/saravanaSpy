package com.saravana.knownspies.ModelLayer.Translation;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saravana.knownspies.ModelLayer.DTOs.SpyDTO;
import com.saravana.knownspies.ModelLayer.Database.Realm.Spy;
import com.saravana.knownspies.ModelLayer.Enums.DTOType;

import java.util.List;

import io.realm.Realm;

public class TranslationLayerImpl implements TranslationLayer {
    private static final String TAG = "TranslationLayer";

    private Gson gson;

    private SpyTranslator spyTranslator;

    public TranslationLayerImpl(Gson gson, SpyTranslator spyTranslator) {
        this.gson = gson;
        this.spyTranslator = spyTranslator;
    }

    @Override
    public List<SpyDTO> convertJson(String json) {
        Log.d(TAG, "converting json to dtos");

        TypeToken<List<SpyDTO>> token = new TypeToken<List<SpyDTO>>(){};

        return gson.fromJson(json, token.getType());
    }

    @Override
    public SpyTranslator translatorFor(DTOType type){
        switch (type) {
            case spy:
                return spyTranslator;
            default:
                return spyTranslator;
        }
    }

    @Override
    public SpyDTO translate(Spy spy) {
        SpyDTO dto = spyTranslator.translate(spy);
        return dto;
    }

    @Override
    public Spy translate(SpyDTO dto, Realm realm) {
        Spy spy = spyTranslator.translate(dto, realm);
        return spy;
    }
}
