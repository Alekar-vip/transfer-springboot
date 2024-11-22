package co.com.tipi.central.transfercentral.service;

import co.com.tipi.central.transfercentral.models.domains.Annex;

import java.util.List;

public interface AnnexService {
    List<Annex> findAllByTransferredAtIsNull();

    void save(Annex annex);
}
