package org.agro.adapters.out.persistence.adapter.contratar;

import lombok.RequiredArgsConstructor;
import org.agro.adapters.out.persistence.entities.OperacaoCreditoEntity;
import org.agro.adapters.out.persistence.mapper.OperacaoCreditoEntityMapper;
import org.agro.adapters.out.persistence.repositories.jpa.OperacaoCreditoRepository;
import org.agro.core.domain.OperacaoCreditoDomain;
import org.agro.core.ports.out.usecase.contratar.SalvarOperacaoCreditoPort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SalvarOperacaoCreditoAdapter implements SalvarOperacaoCreditoPort {

    private final OperacaoCreditoRepository repository;
    private final OperacaoCreditoEntityMapper mapper;

    @Override
    public OperacaoCreditoDomain salvar(OperacaoCreditoDomain domain) {
        OperacaoCreditoEntity entity = mapper.toEntity(domain);
        OperacaoCreditoEntity salvo = repository.save(entity);
        return mapper.toDomain(salvo);
    }
}