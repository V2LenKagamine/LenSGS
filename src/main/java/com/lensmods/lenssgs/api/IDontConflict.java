package com.lensmods.lenssgs.api;

import net.neoforged.neoforge.client.settings.IKeyConflictContext;

public enum IDontConflict implements IKeyConflictContext {
    IDONT {
        @Override
        public boolean isActive() {
            return true;
        }

        @Override
        public boolean conflicts(IKeyConflictContext iKeyConflictContext) {
            return false;
        }
    }
}
