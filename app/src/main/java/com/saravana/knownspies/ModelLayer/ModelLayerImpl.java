package com.saravana.knownspies.ModelLayer;

import com.saravana.knownspies.Helpers.Threading;
import com.saravana.knownspies.ModelLayer.DTOs.SpyDTO;
import com.saravana.knownspies.ModelLayer.Database.DataLayer;
import com.saravana.knownspies.ModelLayer.Database.Realm.Spy;
import com.saravana.knownspies.ModelLayer.Enums.Source;
import com.saravana.knownspies.ModelLayer.Network.NetworkLayer;
import com.saravana.knownspies.ModelLayer.Translation.SpyTranslator;
import com.saravana.knownspies.ModelLayer.Translation.TranslationLayer;

import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class ModelLayerImpl implements ModelLayer {
    private NetworkLayer networkLayer;
    private DataLayer dataLayer;
    private TranslationLayer translationLayer;

    public ModelLayerImpl(NetworkLayer networkLayer, DataLayer dataLayer, TranslationLayer translationLayer) {
        this.networkLayer = networkLayer;
        this.dataLayer = dataLayer;
        this.translationLayer = translationLayer;
    }

    @Override
    public void loadData(Consumer<List<SpyDTO>> onNewResults, Consumer<Source> notifyDataReceived) {
        SpyTranslator translator = translationLayer.translatorFor(SpyDTO.dtoType);

        try {
            dataLayer.loadSpiesFromLocal(translator::translate, onNewResults);
            notifyDataReceived.accept(Source.local);
        } catch (Exception e) {
            e.printStackTrace();
        }

        networkLayer.loadJson(json -> {
            notifyDataReceived.accept(Source.network);
            persistJson(json, ()->dataLayer.loadSpiesFromLocal(translator::translate, onNewResults));
        });


    }

    @Override
    public SpyDTO spyForId(int spyId) {
        Spy spy = dataLayer.spyForId(spyId);
        SpyDTO spyDTO = translationLayer.translate(spy);
        return spyDTO;
    }

    @Override
    public SpyDTO spyForName(String name) {
        Spy spy = dataLayer.spyForName(name);
        SpyDTO spyDTO = translationLayer.translate(spy);
        return spyDTO;
    }

    @Override
    public void save(List<SpyDTO> dtos, Action finished) {
        Threading.async(() -> {
            persistDTOs(dtos, finished);
            return true;
        });
    }

    private void persistDTOs(List<SpyDTO> dtos, Action finished) {
        SpyTranslator translator = translationLayer.translatorFor(SpyDTO.dtoType);
        dataLayer.persistDTOs(dtos, translator::translate);

        Threading.dispatchMain(() -> finished.run());
    }


    private void persistJson(String json, Action finished) {
        List<SpyDTO> dtos = translationLayer.convertJson(json);

        Threading.async(()->{
           dataLayer.clearSpies(() -> {
               dtos.forEach(dto -> dto.initialize());

               persistDTOs(dtos, finished);
           });

            return true;
        });

    }

}
