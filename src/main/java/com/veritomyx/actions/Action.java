package com.veritomyx.actions;

public interface Action {
	public String buildQuery();

	public void processResponse(String response) throws ResponseFormatException;

	public void reset();

	public class ResponseFormatException extends Exception {
		private static final long serialVersionUID = 1L;

		private Action action = null;

		public ResponseFormatException(String message, Action action) {
			super(message);
			this.action = action;
		}

		public ResponseFormatException(String message, Action action, Throwable cause) {
			super(message, cause);
			this.action = action;
		}

		public Action getAction() {
			return action;
		}
	}
}
