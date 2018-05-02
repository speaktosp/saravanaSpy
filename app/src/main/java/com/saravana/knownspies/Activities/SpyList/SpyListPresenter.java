package com.saravana.knownspies.Activities.SpyList;

import com.saravana.knownspies.ModelLayer.DTOs.SpyDTO;
import com.saravana.knownspies.ModelLayer.Enums.Source;

import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

public interface SpyListPresenter {
    void loadData(Consumer<Source> notifyDataReceived);

    void addNewSpy();

    BehaviorSubject<List<SpyDTO>> spies();
}
