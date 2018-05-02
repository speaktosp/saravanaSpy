package com.saravana.knownspies.ModelLayer.Translation;

import com.saravana.knownspies.ModelLayer.DTOs.SpyDTO;
import com.saravana.knownspies.ModelLayer.Database.Realm.Spy;

import io.realm.Realm;

public interface SpyTranslator {
    SpyDTO translate(Spy from);
    Spy translate(SpyDTO dto, Realm realm);
}
