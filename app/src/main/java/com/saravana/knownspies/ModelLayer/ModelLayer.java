package com.saravana.knownspies.ModelLayer;

import com.saravana.knownspies.ModelLayer.DTOs.SpyDTO;
import com.saravana.knownspies.ModelLayer.Enums.Source;

import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public interface ModelLayer {
    void loadData(Consumer<List<SpyDTO>> onNewResults, Consumer<Source> notifyDataReceived);

    SpyDTO spyForId(int spyId);

    SpyDTO spyForName(String name);

    void save(List<SpyDTO> dtos, Action finished);
}
