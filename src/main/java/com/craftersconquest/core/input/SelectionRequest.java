package com.craftersconquest.core.input;

import java.util.function.Consumer;

public class SelectionRequest {

    private final String prompt;
    private final Runnable acceptAction;
    private final Runnable declineAction;

    public SelectionRequest(String prompt, Runnable acceptAction, Runnable declineAction) {
        this.prompt = prompt;
        this.acceptAction = acceptAction;
        this.declineAction = declineAction;
    }

    public String getPrompt() {
        return prompt;
    }

    public Runnable getAcceptAction() {
        return acceptAction;
    }

    public Runnable getDeclineAction() {
        return declineAction;
    }

}
