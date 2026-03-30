package org.agro.adapters.configs.beans.impl;

import org.agro.core.ports.in.BuscarOperacaoCreditoUseCase;
import org.agro.core.ports.in.ContratarCreditoUseCase;
import org.agro.core.ports.out.logging.LoggerPort;
import org.agro.core.ports.out.usecase.LockPort;
import org.agro.core.ports.out.usecase.buscar.BuscarOperacaoCreditoPort;
import org.agro.core.ports.out.usecase.contratar.SalvarOperacaoCreditoComSocioPort;
import org.agro.core.ports.out.usecase.contratar.ValidarProdutoPort;
import org.agro.core.usecase.credito.BuscarOperacaoCreditoUseCaseImpl;
import org.agro.core.usecase.credito.ContratarCreditoUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImplBeanConfiguration {

    @Bean
    ContratarCreditoUseCase contratarCreditoUseCaseImpl(ValidarProdutoPort validarProdutoPort,
                                                        SalvarOperacaoCreditoComSocioPort salvarOperacaoCreditoComSocioPort,
                                                        LockPort lockPort,
                                                        LoggerPort logger) {
        return new ContratarCreditoUseCaseImpl(validarProdutoPort, salvarOperacaoCreditoComSocioPort, lockPort, logger);
    }

    @Bean
    BuscarOperacaoCreditoUseCase buscarOperacaoCreditoUseCaseImpl(BuscarOperacaoCreditoPort buscarOperacaoCreditoPort, LoggerPort logger) {
        return new BuscarOperacaoCreditoUseCaseImpl(buscarOperacaoCreditoPort, logger);
    }

}