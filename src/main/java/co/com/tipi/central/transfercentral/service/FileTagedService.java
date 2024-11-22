package co.com.tipi.central.transfercentral.service;

import co.com.tipi.central.transfercentral.models.domains.FileTaged;

import java.util.List;

public interface FileTagedService {

    List<FileTaged> findAllByTransferredAtIsNull();

    void save(FileTaged fileTaged);
}
