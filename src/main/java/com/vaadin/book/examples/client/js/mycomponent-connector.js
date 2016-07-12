window.com_vaadin_book_examples_client_js_MyComponent =
	function() {
		// Create the component
		var mycomponent =
			new mylibrary.MyComponent(this.getElement());
	
        // Handle changes from the server-side
		this.onStateChange = function() {
			mycomponent.setValue(this.getState().value);
		};
	
        // Pass user interaction to the server-side
		var connector = this;
		mycomponent.click = function() {
			connector.onClick(mycomponent.getValue());
		};
	};
