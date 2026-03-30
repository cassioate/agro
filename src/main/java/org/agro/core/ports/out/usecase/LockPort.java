package org.agro.core.ports.out.usecase;

public interface LockPort {
    boolean tentarLock(String key);
    void liberarLock(String key);
}