package org.agro.adapters.out.persistence.adapter.contratar;

import lombok.RequiredArgsConstructor;
import org.agro.adapters.out.persistence.entities.OperacaoCreditoEntity;
import org.agro.adapters.out.persistence.entities.SocioBeneficiarioEntity;
import org.agro.adapters.out.persistence.mapper.SocioBeneficiarioEntityMapper;
import org.agro.adapters.out.persistence.repositories.jpa.OperacaoCreditoRepository;
import org.agro.adapters.out.persistence.repositories.jpa.SocioBeneficiarioRepository;
import org.agro.core.domain.exceptions.RecursoNaoEncontradoException;
import org.agro.core.ports.out.usecase.contratar.SalvarSocioBeneficiarioPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SalvarSocioBeneficiarioAdapter implements SalvarSocioBeneficiarioPort {

    private final SocioBeneficiarioRepository socioBeneficiarioRepository;
    private final OperacaoCreditoRepository operacaoCreditoRepository;
    private final SocioBeneficiarioEntityMapper mapper;

    @Override
    public void salvar(UUID idOperacaoCredito, Long idAssociado) {
        OperacaoCreditoEntity operacaoEntity = operacaoCreditoRepository.findById(idOperacaoCredito)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Operação não encontrada para o id: " + idOperacaoCredito));

        SocioBeneficiarioEntity entity = mapper.toEntity(operacaoEntity, idAssociado);
        socioBeneficiarioRepository.save(entity);
    }
}