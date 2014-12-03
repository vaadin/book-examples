// Define the namespace
var mylibrary = mylibrary || {};

mylibrary.MyComponent = function (element) {
	element.innerHTML =
		"<div class='caption'>Hello, world!</div>" +
		"<div class='textinput'>Enter a value: " +
		"<input type='text' name='value'/>" +
		"<input type='button' value='Click'/>" +
		"</div>";

	// Style it
	element.style.border = "thin solid red";
	element.style.display = "inline-block";

	// Getter and setter for the value property
	this.getValue = function () {
		return element.
		    getElementsByTagName("input")[0].value;
	};
	this.setValue = function (value) {
		element.getElementsByTagName("input")[0].value =
		    value;
	};

	// Default implementation of the click handler
	this.click = function () {
		alert("Error: Must implement click() method");
	};

	// Set up button click
	var button = element.getElementsByTagName("input")[1];
	var self = this; // Can't use this inside the function
	button.onclick = function () {
		self.click();
	};
};
