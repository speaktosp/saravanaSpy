package com.saravana.knownspies.ModelLayer.Translation;

import com.saravana.knownspies.ModelLayer.DTOs.SpyDTO;
import com.saravana.knownspies.ModelLayer.Database.Realm.Spy;
import com.saravana.knownspies.ModelLayer.Enums.DTOType;

import java.util.List;

import io.realm.Realm;

public interface TranslationLayer {
    List<SpyDTO> convertJson(String json);

    SpyTranslator translatorFor(DTOType type);

    SpyDTO translate(Spy spy);

    Spy translate(SpyDTO dto, Realm realm);
}
