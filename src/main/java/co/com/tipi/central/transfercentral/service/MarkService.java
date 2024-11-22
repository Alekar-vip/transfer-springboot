package co.com.tipi.central.transfercentral.service;

import co.com.tipi.central.transfercentral.models.domains.Mark;

import java.util.List;

public interface MarkService {

    List<Mark> findAllByTransferredAtIsNull();

    void save(Mark mark);
}
