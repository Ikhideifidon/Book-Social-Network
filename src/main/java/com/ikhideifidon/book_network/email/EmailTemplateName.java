package com.ikhideifidon.book_network.email;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EmailTemplateName {
    ACTIVATE_ACCOUNT("Activate account");

    private final String name;
}
