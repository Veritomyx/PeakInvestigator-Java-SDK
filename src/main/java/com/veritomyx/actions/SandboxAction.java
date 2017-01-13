package com.veritomyx.actions;

public class SandboxAction<T extends Action> implements Action {

	private final T action;
	private String sandboxOption = null;

	public SandboxAction(T action) {
		this.action = action;
	}

	public SandboxAction(T action, String sandboxOption) {
		this.action = action;
		this.sandboxOption = sandboxOption;
	}

	public SandboxAction(T action, int errorCode) {
		this.action = action;
		this.sandboxOption = Integer.toString(errorCode);
	}

	@Override
	public String buildQuery() {
		String query = action.buildQuery();
		if (sandboxOption != null) {
			query += "&Sandbox=" + sandboxOption;
		} else {
			query += "&Sandbox=0";
		}

		return query;
	}

	@Override
	public void processResponse(String response) throws ResponseFormatException {
		action.processResponse(response);
	}

	@Override
	public void reset() {
		action.reset();
	}

	public T getWrappedAction() {
		return action;
	}
}
